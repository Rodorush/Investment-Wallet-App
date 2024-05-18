import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.rodorush.investmentwalletapp.data.entity.CarteiraEntity
import br.com.rodorush.investmentwalletapp.viewmodel.CarteirasViewModel

@Composable
fun CadastroCarteira(
    viewModel: CarteirasViewModel,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var carteiraNome by remember { mutableStateOf("") }

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
                label = { Text(text = "Nome da Carteira") },
                onValueChange = { carteiraNome = it }
            )
            Button(onClick = {
                viewModel.addCarteira(CarteiraEntity(nome = carteiraNome))
                onDismiss()
            }) {
                Text("Salvar")
            }
        }
    }
}
