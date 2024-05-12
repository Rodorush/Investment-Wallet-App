package br.com.rodorush.investmentwalletapp

import Carteira
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
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import br.com.rodorush.investmentwalletapp.data.entity.CarteiraEntity
import br.com.rodorush.investmentwalletapp.data.repository.CarteiraRepository
import br.com.rodorush.investmentwalletapp.ui.theme.InvestmentWalletAppTheme
import br.com.rodorush.investmentwalletapp.viewmodel.CarteirasViewModel

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

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(stringResource(R.string.app_name)) },
                            actions = {
                                IconButton(onClick = { /* Navigate to add carteira screen */ }) {
                                    Icon(Icons.Filled.Add, contentDescription = "Add Carteira")
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    ListaCarteiras(carteiras, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ListaCarteiras(carteiras: List<CarteiraEntity>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(carteiras) { carteira ->
            CarteiraItem(carteira = carteira) {
                // Handle click on carteira item
            }
        }
    }
}

@Composable
fun CarteiraItem(carteira: CarteiraEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { onClick() },
        content = {
            Text(text = carteira.nome, modifier = Modifier.padding(16.dp))
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InvestmentWalletAppTheme {
        ListaCarteiras(listOf(CarteiraEntity(1, "Carteira 1"), CarteiraEntity(2, "Carteira 2")))
    }
}
