import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.rodorush.investmentwalletapp.data.entity.CarteiraEntity
import br.com.rodorush.investmentwalletapp.data.repository.CarteiraRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CarteirasViewModel(private val repository: CarteiraRepository) : ViewModel() {
    private val _carteiras = MutableStateFlow<List<CarteiraEntity>>(emptyList())
    val carteiras: StateFlow<List<CarteiraEntity>> get() = _carteiras

    init {
        loadCarteiras()
    }

    private fun loadCarteiras() {
        viewModelScope.launch {
            _carteiras.value = repository.getAllCarteiras()
        }
    }

    fun addCarteira(carteira: CarteiraEntity) {
        viewModelScope.launch {
            repository.addCarteira(carteira)
            loadCarteiras()
        }
    }
}
