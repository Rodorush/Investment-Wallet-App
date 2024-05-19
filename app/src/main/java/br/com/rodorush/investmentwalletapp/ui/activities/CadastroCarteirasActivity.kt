package br.com.rodorush.investmentwalletapp.ui.activities

import CadastroCarteira
import CarteirasViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.rodorush.investmentwalletapp.data.entity.CarteiraEntity
import br.com.rodorush.investmentwalletapp.data.repository.CarteiraRepository
import br.com.rodorush.investmentwalletapp.ui.theme.InvestmentWalletAppTheme

@OptIn(ExperimentalMaterial3Api::class)
class CadastroCarteirasActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InvestmentWalletAppTheme {
                val carteiraRepository = CarteiraRepository(applicationContext)
                val viewModel = CarteirasViewModel(carteiraRepository)
                val carteiras by viewModel.carteiras.collectAsState(initial = emptyList())

                var showCadastroCarteira by remember { mutableStateOf(false) }
                var carteiraToEdit by remember { mutableStateOf<CarteiraEntity?>(null) }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Cadastro de Carteiras") },
                            actions = {
                                IconButton(onClick = { showCadastroCarteira = true }) {
                                    Icon(Icons.Filled.Add, contentDescription = "Adicionar Carteira")
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
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
                                onDeleteCarteira = { viewModel.deleteCarteira(it) },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}
