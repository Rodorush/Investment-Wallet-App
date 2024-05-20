package br.com.rodorush.investmentwalletapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.rodorush.investmentwalletapp.data.entity.AtivoEntity
import br.com.rodorush.investmentwalletapp.data.repository.AtivoRepository
import br.com.rodorush.investmentwalletapp.domain.model.StockData
import br.com.rodorush.investmentwalletapp.network.AlphaVantageApi
import br.com.rodorush.investmentwalletapp.network.toStockData
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StockViewModel(private val ativoRepository: AtivoRepository) : ViewModel() {

    private val _stockData = MutableLiveData<StockData>()
    val stockData: LiveData<StockData> get() = _stockData

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.alphavantage.co/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val alphaVantageApi = retrofit.create(AlphaVantageApi::class.java)

    fun fetchStockData(ticker: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = alphaVantageApi.getStockData("TIME_SERIES_DAILY", ticker, apiKey)
                if (response.isSuccessful) {
                    val stockData = response.body()?.toStockData(ticker)
                    stockData?.let {
                        _stockData.value = it
                        Log.d("StockViewModel", "Fetched stock data: $it")
                        updateAtivoWithStockData(ticker, it)
                    }
                } else {
                    // Handle the error
                    Log.e("StockViewModel", "Error fetching stock data: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                // Handle the exception
                Log.e("StockViewModel", "Exception fetching stock data: ${e.message}")
            }
        }
    }

    private suspend fun updateAtivoWithStockData(ticker: String, stockData: StockData) {
        val ativos = ativoRepository.getAtivosByTicker(ticker)
        ativos.forEach { ativo ->
            val updatedAtivo = ativo.copy(
                ultimoPreco = stockData.price,
                dataUltimoPreco = stockData.date
            )
            ativoRepository.updateAtivo(updatedAtivo)
            Log.d("StockViewModel", "Updated ativo with stock data: $updatedAtivo")
        }
    }
}
