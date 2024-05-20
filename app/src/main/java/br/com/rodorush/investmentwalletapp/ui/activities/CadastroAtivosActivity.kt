package br.com.rodorush.investmentwalletapp.ui.activities

import AtivosViewModel
import CadastroAtivo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.rodorush.investmentwalletapp.BuildConfig
import br.com.rodorush.investmentwalletapp.data.entity.AtivoEntity
import br.com.rodorush.investmentwalletapp.data.repository.AtivoRepository
import br.com.rodorush.investmentwalletapp.ui.theme.InvestmentWalletAppTheme
import br.com.rodorush.investmentwalletapp.viewmodel.StockViewModel

@OptIn(ExperimentalMaterial3Api::class)
class CadastroAtivosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InvestmentWalletAppTheme {
                val ativosRepository = AtivoRepository(applicationContext)
                val viewModel = viewModel { AtivosViewModel(ativosRepository) }
                val ativos by viewModel.ativos.collectAsState(initial = emptyList())

                var showCadastroAtivo by remember { mutableStateOf(false) }
                var ativoToEdit by remember { mutableStateOf<AtivoEntity?>(null) }

                val stockViewModel: StockViewModel = viewModel()
                val stockData by stockViewModel.stockData.observeAsState()

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Cadastro de Ativos") },
                            actions = {
                                IconButton(onClick = { showCadastroAtivo = true }) {
                                    Icon(Icons.Filled.Add, contentDescription = "Adicionar Ativo")
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        Column {
                            if (showCadastroAtivo || ativoToEdit != null) {
                                CadastroAtivo(
                                    viewModel = viewModel,
                                    ativo = ativoToEdit,
                                    onDismiss = {
                                        showCadastroAtivo = false
                                        ativoToEdit = null
                                    },
                                    modifier = Modifier.padding(innerPadding)
                                )
                            } else {
                                ListaAtivos(
                                    ativos = ativos,
                                    onEditAtivo = { ativoToEdit = it },
                                    onDeleteAtivo = { viewModel.deleteAtivo(it) },
                                    modifier = Modifier.padding(innerPadding)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = { stockViewModel.fetchStockData("PETR4.SAO", BuildConfig.ALPHA_VANTAGE_API_KEY) },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Fetch Stock Data")
                            }

                            stockData?.let { data ->
                                Text("Symbol: ${data.symbol}")
                                Text("Price: ${data.price}")
                                Text("Volume: ${data.volume}")
                            }
                        }
                    }
                }
            }
        }
    }
}
