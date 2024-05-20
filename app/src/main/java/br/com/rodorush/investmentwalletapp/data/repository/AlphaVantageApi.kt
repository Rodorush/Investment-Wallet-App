package br.com.rodorush.investmentwalletapp.data.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AlphaVantageApi {
    @GET("query")
    fun getStockData(
        @Query("function") function: String = "TIME_SERIES_DAILY",
        @Query("symbol") symbol: String,
//        @Query("interval") interval: String = "5min",
        @Query("apikey") apiKey: String
    ): Call<StockResponse>
}

data class StockResponse(
    val metaData: MetaData,
    val timeSeries: Map<String, StockInfo>
)

data class MetaData(
    val information: String,
    val symbol: String,
    val lastRefreshed: String,
    val interval: String,
    val outputSize: String,
    val timeZone: String
)

data class StockInfo(
    val open: String,
    val high: String,
    val low: String,
    val close: String,
    val volume: String
)
