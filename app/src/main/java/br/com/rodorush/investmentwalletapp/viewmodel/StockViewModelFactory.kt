package br.com.rodorush.investmentwalletapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.rodorush.investmentwalletapp.data.repository.AtivoRepository

class StockViewModelFactory(private val ativoRepository: AtivoRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StockViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StockViewModel(ativoRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
