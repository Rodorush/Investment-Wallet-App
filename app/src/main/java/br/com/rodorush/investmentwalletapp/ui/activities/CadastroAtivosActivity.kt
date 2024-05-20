package br.com.rodorush.investmentwalletapp.ui.activities

import CadastroAtivo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.rodorush.investmentwalletapp.BuildConfig
import br.com.rodorush.investmentwalletapp.data.entity.AtivoEntity
import br.com.rodorush.investmentwalletapp.data.repository.AtivoRepository
import br.com.rodorush.investmentwalletapp.ui.theme.InvestmentWalletAppTheme
import br.com.rodorush.investmentwalletapp.viewmodel.AtivosViewModel
import br.com.rodorush.investmentwalletapp.viewmodel.StockViewModel
import br.com.rodorush.investmentwalletapp.viewmodel.StockViewModelFactory

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

                val stockViewModel: StockViewModel = viewModel(factory = StockViewModelFactory(ativosRepository))
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
                        }
                    }
                }
            }
        }
    }
}
