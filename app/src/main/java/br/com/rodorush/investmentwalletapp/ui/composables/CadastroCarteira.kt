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
import br.com.rodorush.investmentwalletapp.data.entity.CarteiraEntity
import kotlinx.coroutines.launch

@Composable
fun CadastroCarteira(
    viewModel: CarteirasViewModel,
    carteira: CarteiraEntity? = null,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var carteiraNome by remember { mutableStateOf(carteira?.nome ?: "") }
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
                value = carteiraNome,
                label = { Text(text = stringResource(R.string.nome_da_carteira)) },
                onValueChange = { carteiraNome = it }
            )
            Button(onClick = {
                coroutineScope.launch {
                    if (carteira == null) {
                        viewModel.addCarteira(CarteiraEntity(nome = carteiraNome))
                    } else {
                        viewModel.updateCarteira(carteira.copy(nome = carteiraNome))
                    }
                    onDismiss()
                }
            }) {
                Text(stringResource(R.string.salvar))
            }
        }
    }
}
