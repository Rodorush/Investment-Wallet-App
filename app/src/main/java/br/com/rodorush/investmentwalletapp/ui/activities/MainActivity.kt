package br.com.rodorush.investmentwalletapp.ui.activities

import MenuPrincipal
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.rodorush.investmentwalletapp.R
import br.com.rodorush.investmentwalletapp.data.entity.AtivoEntity
import br.com.rodorush.investmentwalletapp.data.entity.CarteiraEntity
import br.com.rodorush.investmentwalletapp.data.repository.AtivoRepository
import br.com.rodorush.investmentwalletapp.data.repository.IAtivoRepository
import br.com.rodorush.investmentwalletapp.ui.theme.InvestmentWalletAppTheme
import br.com.rodorush.investmentwalletapp.viewmodel.StockViewModel
import br.com.rodorush.investmentwalletapp.viewmodel.StockViewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InvestmentWalletAppTheme {
                val ativosRepository = AtivoRepository(applicationContext)
                val stockViewModel: StockViewModel = viewModel(factory = StockViewModelFactory(ativosRepository))

                Scaffold(
                    topBar = {
                        TopAppBar(title = { Text("Menu Principal") })
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        MenuPrincipal(
                            onNavigateToAtivos = {
                                val intent = Intent(this@MainActivity, CadastroAtivosActivity::class.java)
                                startActivity(intent)
                            },
                            onNavigateToCarteiras = {
                                val intent = Intent(this@MainActivity, CadastroCarteirasActivity::class.java)
                                startActivity(intent)
                            }
                        )
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val dummyAtivoRepository = DummyAtivoRepository()
    val stockViewModel = StockViewModel(dummyAtivoRepository)

    InvestmentWalletAppTheme {
        Column {
            ListaCarteiras(
                carteiras = listOf(CarteiraEntity(1, "Carteira 1"), CarteiraEntity(2, "Carteira 2")),
                onEditCarteira = {},
                onDeleteCarteira = {}
            )
            ListaAtivos(
                ativos = listOf(AtivoEntity(1, "Ativo 1", "ATV1"), AtivoEntity(2, "Ativo 2", "ATV2")),
                stockViewModel = stockViewModel,
                onEditAtivo = {},
                onDeleteAtivo = {}
            )
        }
    }
}
