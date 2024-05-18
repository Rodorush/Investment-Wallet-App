import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.rodorush.investmentwalletapp.R
import br.com.rodorush.investmentwalletapp.data.entity.CarteiraEntity
import kotlinx.coroutines.launch

@Composable
fun CadastroCarteira(
    viewModel: CarteirasViewModel,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var carteiraNome by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), // Add padding here
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = carteiraNome,
                label = { Text(text = stringResource(R.string.nome_da_carteira)) },
                onValueChange = { carteiraNome = it }
            )
            Button(onClick = {
                coroutineScope.launch {
                    viewModel.addCarteira(CarteiraEntity(nome = carteiraNome))
                    onDismiss()
                }
            }) {
                Text(stringResource(R.string.salvar))
            }
        }
    }
}
