package br.com.rodorush.investmentwalletapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.rodorush.investmentwalletapp.data.repository.RetrofitClient
import br.com.rodorush.investmentwalletapp.domain.model.StockData
import kotlinx.coroutines.launch
import retrofit2.await

class StockViewModel : ViewModel() {

    private val _stockData = MutableLiveData<StockData>()
    val stockData: LiveData<StockData> get() = _stockData

    fun fetchStockData(symbol: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getStockData(symbol = symbol, apiKey = apiKey).await()
                val stockInfo = response.timeSeries.values.first()
                _stockData.value = StockData(
                    symbol = symbol,
                    price = stockInfo.close.toDouble(),
                    volume = stockInfo.volume.toInt()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
