package br.com.rodorush.investmentwalletapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.rodorush.investmentwalletapp.data.entity.CarteiraEntity
import br.com.rodorush.investmentwalletapp.data.repository.CarteiraRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CarteirasViewModel(private val carteiraRepository: CarteiraRepository) : ViewModel() {

    // Usando MutableStateFlow para armazenar o estado das carteiras
    private val _carteiras = MutableStateFlow<List<CarteiraEntity>>(emptyList())
    val carteiras: StateFlow<List<CarteiraEntity>> = _carteiras

    init {
        viewModelScope.launch {
            // Atualize o estado das carteiras quando o ViewModel for criado
            _carteiras.value = carteiraRepository.getAllCarteiras()
        }
    }

    suspend fun addCarteira(carteira: CarteiraEntity) {
        carteiraRepository.insertCarteira(carteira)
    }

    suspend fun updateCarteira(carteira: CarteiraEntity) {
        carteiraRepository.updateCarteira(carteira)
    }

    suspend fun deleteCarteira(carteira: CarteiraEntity) {
        carteiraRepository.deleteCarteira(carteira)
    }
}
