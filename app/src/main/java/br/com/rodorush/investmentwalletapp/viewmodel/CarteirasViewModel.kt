import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.rodorush.investmentwalletapp.data.entity.CarteiraEntity
import br.com.rodorush.investmentwalletapp.data.repository.CarteiraRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CarteirasViewModel(private val carteiraRepository: CarteiraRepository) : ViewModel() {

    val carteiras: StateFlow<List<CarteiraEntity>> = carteiraRepository.getAllCarteiras()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addCarteira(carteira: CarteiraEntity) {
        viewModelScope.launch {
            carteiraRepository.addCarteira(carteira)
        }
    }

    fun updateCarteira(carteira: CarteiraEntity) {
        viewModelScope.launch {
            carteiraRepository.updateCarteira(carteira)
        }
    }

    fun deleteCarteira(carteira: CarteiraEntity) {
        viewModelScope.launch {
            carteiraRepository.deleteCarteira(carteira)
        }
    }
}
