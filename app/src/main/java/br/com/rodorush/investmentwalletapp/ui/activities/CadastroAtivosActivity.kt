package br.com.rodorush.investmentwalletapp.ui.activities

import AtivosViewModel
import CadastroAtivo
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
import br.com.rodorush.investmentwalletapp.data.entity.AtivoEntity
import br.com.rodorush.investmentwalletapp.data.repository.AtivoRepository
import br.com.rodorush.investmentwalletapp.ui.theme.InvestmentWalletAppTheme

@OptIn(ExperimentalMaterial3Api::class)
class CadastroAtivosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InvestmentWalletAppTheme {
                val ativosRepository = AtivoRepository(applicationContext)
                val viewModel = AtivosViewModel(ativosRepository)
                val ativos by viewModel.ativos.collectAsState(initial = emptyList())

                var showCadastroAtivo by remember { mutableStateOf(false) }
                var ativoToEdit by remember { mutableStateOf<AtivoEntity?>(null) }

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
                    }
                }
            }
        }
    }
}