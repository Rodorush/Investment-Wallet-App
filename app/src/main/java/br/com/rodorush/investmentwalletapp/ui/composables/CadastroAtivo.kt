import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.rodorush.investmentwalletapp.R
import br.com.rodorush.investmentwalletapp.data.entity.AtivoEntity
import kotlinx.coroutines.launch

@Composable
fun CadastroAtivo(
    viewModel: AtivosViewModel,
    ativo: AtivoEntity? = null,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var ativoNome by remember { mutableStateOf(ativo?.nome ?: "") }
    var ativoTicker by remember { mutableStateOf(ativo?.ticker ?: "") }
    val coroutineScope = rememberCoroutineScope()

    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = ativoNome,
                label = { Text(text = stringResource(R.string.nome_do_ativo)) },
                onValueChange = { ativoNome = it }
            )
            OutlinedTextField(
                value = ativoTicker,
                label = { Text(text = stringResource(R.string.ticker_do_ativo)) },
                onValueChange = { ativoTicker = it }
            )
            Button(onClick = {
                coroutineScope.launch {
                    if (ativo == null) {
                        viewModel.addAtivo(AtivoEntity(nome = ativoNome, ticker = ativoTicker))
                    } else {
                        viewModel.updateAtivo(ativo.copy(nome = ativoNome, ticker = ativoTicker))
                    }
                    onDismiss()
                }
            }) {
                Text(stringResource(R.string.salvar))
            }
        }
    }
}