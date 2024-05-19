package br.com.rodorush.investmentwalletapp

import AtivosViewModel
import CadastroAtivo
import CadastroCarteira
import CarteirasViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.rodorush.investmentwalletapp.data.database.AppDatabase
import br.com.rodorush.investmentwalletapp.data.entity.AtivoEntity
import br.com.rodorush.investmentwalletapp.data.entity.CarteiraEntity
import br.com.rodorush.investmentwalletapp.data.repository.AtivoRepository
import br.com.rodorush.investmentwalletapp.data.repository.CarteiraRepository
import br.com.rodorush.investmentwalletapp.ui.theme.InvestmentWalletAppTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(applicationContext)

        setContent {
            InvestmentWalletAppTheme {
                val carteiraRepository = CarteiraRepository(applicationContext)
                val ativosRepository = AtivoRepository(applicationContext)
                val viewModelCarteiras = CarteirasViewModel(carteiraRepository)
                val viewModelAtivos = AtivosViewModel(ativosRepository)
                val carteiras by viewModelCarteiras.carteiras.collectAsState(initial = emptyList())
                val ativos by viewModelAtivos.ativos.collectAsState(initial = emptyList())

                var showCadastroCarteira by remember { mutableStateOf(false) }
                var showCadastroAtivo by remember { mutableStateOf(false) }
                var carteiraToEdit by remember { mutableStateOf<CarteiraEntity?>(null) }
                var ativoToEdit by remember { mutableStateOf<AtivoEntity?>(null) }
                var carteiraToDelete by remember { mutableStateOf<CarteiraEntity?>(null) }
                var ativoToDelete by remember { mutableStateOf<AtivoEntity?>(null) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(stringResource(R.string.app_name)) },
                            actions = {
                                IconButton(onClick = { showCadastroCarteira = true }) {
                                    Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.add_carteira))
                                }
                                IconButton(onClick = { showCadastroAtivo = true }) {
                                    Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.add_ativo))
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    if (showCadastroCarteira || carteiraToEdit != null) {
                        CadastroCarteira(
                            viewModel = viewModelCarteiras,
                            carteira = carteiraToEdit,
                            onDismiss = {
                                showCadastroCarteira = false
                                carteiraToEdit = null
                            },
                            modifier = Modifier.padding(innerPadding)
                        )
                    } else if (showCadastroAtivo || ativoToEdit != null) {
                        CadastroAtivo(
                            viewModel = viewModelAtivos,
                            ativo = ativoToEdit,
                            onDismiss = {
                                showCadastroAtivo = false
                                ativoToEdit = null
                            },
                            modifier = Modifier.padding(innerPadding)
                        )
                    } else {
                        Column {
                            ListaCarteiras(
                                carteiras = carteiras,
                                onEditCarteira = { carteiraToEdit = it },
                                onDeleteCarteira = { carteiraToDelete = it },
                                modifier = Modifier.padding(innerPadding)
                            )
                            ListaAtivos(
                                ativos = ativos,
                                onEditAtivo = { ativoToEdit = it },
                                onDeleteAtivo = { ativoToDelete = it },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }

                        carteiraToDelete?.let { carteira ->
                            AlertDialog(
                                onDismissRequest = { carteiraToDelete = null },
                                confirmButton = {
                                    TextButton(onClick = {
                                        viewModelCarteiras.deleteCarteira(carteira)
                                        carteiraToDelete = null
                                    }) {
                                        Text(stringResource(R.string.confirm))
                                    }
                                },
                                dismissButton = {
                                    TextButton(onClick = { carteiraToDelete = null }) {
                                        Text(stringResource(R.string.cancel))
                                    }
                                },
                                title = { Text(stringResource(R.string.delete_carteira)) },
                                text = { Text(stringResource(R.string.confirm_delete_carteira, carteira.nome)) }
                            )
                        }

                        ativoToDelete?.let { ativo ->
                            AlertDialog(
                                onDismissRequest = { ativoToDelete = null },
                                confirmButton = {
                                    TextButton(onClick = {
                                        viewModelAtivos.deleteAtivo(ativo)
                                        ativoToDelete = null
                                    }) {
                                        Text(stringResource(R.string.confirm))
                                    }
                                },
                                dismissButton = {
                                    TextButton(onClick = { ativoToDelete = null }) {
                                        Text(stringResource(R.string.cancel))
                                    }
                                },
                                title = { Text(stringResource(R.string.delete_ativo)) },
                                text = { Text(stringResource(R.string.confirm_delete_ativo, ativo.nome)) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ListaCarteiras(
    modifier: Modifier = Modifier,
    carteiras: List<CarteiraEntity>,
    onEditCarteira: (CarteiraEntity) -> Unit = {},
    onDeleteCarteira: (CarteiraEntity) -> Unit = {}
) {
    LazyColumn(modifier = modifier) {
        items(carteiras) { carteira ->
            CarteiraItem(
                carteira = carteira,
                onEditClick = { onEditCarteira(carteira) },
                onDeleteClick = { onDeleteCarteira(carteira) }
            )
        }
    }
}

@Composable
fun CarteiraItem(
    carteira: CarteiraEntity,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = carteira.nome, modifier = Modifier.weight(1f))
                IconButton(onClick = onEditClick) {
                    Icon(Icons.Filled.Edit, contentDescription = stringResource(R.string.edit_carteira))
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(Icons.Filled.Delete, contentDescription = stringResource(R.string.delete_carteira))
                }
            }
        }
    )
}

@Composable
fun ListaAtivos(
    modifier: Modifier = Modifier,
    ativos: List<AtivoEntity>,
    onEditAtivo: (AtivoEntity) -> Unit = {},
    onDeleteAtivo: (AtivoEntity) -> Unit = {}
) {
    LazyColumn(modifier = modifier) {
        items(ativos) { ativo ->
            AtivoItem(
                ativo = ativo,
                onEditClick = { onEditAtivo(ativo) },
                onDeleteClick = { onDeleteAtivo(ativo) }
            )
        }
    }
}

@Composable
fun AtivoItem(
    ativo: AtivoEntity,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = ativo.nome, modifier = Modifier.weight(1f))
                IconButton(onClick = onEditClick) {
                    Icon(Icons.Filled.Edit, contentDescription = stringResource(R.string.edit_ativo))
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(Icons.Filled.Delete, contentDescription = stringResource(R.string.delete_ativo))
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InvestmentWalletAppTheme {
        Column {
            ListaCarteiras(
                carteiras = listOf(CarteiraEntity(1, "Carteira 1"), CarteiraEntity(2, "Carteira 2")),
                onEditCarteira = {},
                onDeleteCarteira = {}
            )
            ListaAtivos(
                ativos = listOf(AtivoEntity(1, "Ativo 1", "ATV1"), AtivoEntity(2, "Ativo 2", "ATV2")),
                onEditAtivo = {},
                onDeleteAtivo = {}
            )
        }
    }
}