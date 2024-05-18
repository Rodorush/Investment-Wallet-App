package br.com.rodorush.investmentwalletapp

import CadastroCarteira
import CarteirasViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.rodorush.investmentwalletapp.data.entity.CarteiraEntity
import br.com.rodorush.investmentwalletapp.data.repository.CarteiraRepository
import br.com.rodorush.investmentwalletapp.ui.theme.InvestmentWalletAppTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InvestmentWalletAppTheme {
                val carteiraRepository = CarteiraRepository(applicationContext)
                val viewModel = CarteirasViewModel(carteiraRepository)
                val carteiras by viewModel.carteiras.collectAsState(initial = emptyList())

                var showCadastroCarteira by remember { mutableStateOf(false) }
                var carteiraToEdit by remember { mutableStateOf<CarteiraEntity?>(null) }
                var carteiraToDelete by remember { mutableStateOf<CarteiraEntity?>(null) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(stringResource(R.string.app_name)) },
                            actions = {
                                IconButton(onClick = { showCadastroCarteira = true }) {
                                    Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.add_carteira))
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    if (showCadastroCarteira || carteiraToEdit != null) {
                        CadastroCarteira(
                            viewModel = viewModel,
                            carteira = carteiraToEdit,
                            onDismiss = {
                                showCadastroCarteira = false
                                carteiraToEdit = null
                            },
                            modifier = Modifier.padding(innerPadding)
                        )
                    } else {
                        ListaCarteiras(
                            carteiras = carteiras,
                            onEditCarteira = { carteiraToEdit = it },
                            onDeleteCarteira = { carteiraToDelete = it },
                            modifier = Modifier.padding(innerPadding)
                        )

                        carteiraToDelete?.let { carteira ->
                            AlertDialog(
                                onDismissRequest = { carteiraToDelete = null },
                                confirmButton = {
                                    TextButton(onClick = {
                                        viewModel.deleteCarteira(carteira)
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
                    }
                }
            }
        }
    }
}

@Composable
fun ListaCarteiras(
    carteiras: List<CarteiraEntity>,
    onEditCarteira: (CarteiraEntity) -> Unit = {},
    onDeleteCarteira: (CarteiraEntity) -> Unit = {},
    modifier: Modifier = Modifier
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InvestmentWalletAppTheme {
        ListaCarteiras(
            carteiras = listOf(CarteiraEntity(1, "Carteira 1"), CarteiraEntity(2, "Carteira 2")),
            onEditCarteira = {},
            onDeleteCarteira = {}
        )
    }
}
