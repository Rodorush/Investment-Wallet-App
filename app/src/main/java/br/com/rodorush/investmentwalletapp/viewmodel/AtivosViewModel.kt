import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.rodorush.investmentwalletapp.data.entity.AtivoEntity
import br.com.rodorush.investmentwalletapp.data.repository.AtivoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AtivosViewModel(private val ativoRepository: AtivoRepository) : ViewModel() {

    val ativos: StateFlow<List<AtivoEntity>> = ativoRepository.getAllAtivos()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addAtivo(ativo: AtivoEntity) {
        viewModelScope.launch {
            ativoRepository.addAtivo(ativo)
        }
    }

    fun updateAtivo(ativo: AtivoEntity) {
        viewModelScope.launch {
            ativoRepository.updateAtivo(ativo)
        }
    }

    fun deleteAtivo(ativo: AtivoEntity) {
        viewModelScope.launch {
            ativoRepository.deleteAtivo(ativo)
        }
    }
}
