package br.com.rodorush.investmentwalletapp.network

import br.com.rodorush.investmentwalletapp.domain.model.StockData
import com.google.gson.annotations.SerializedName

data class StockApiResponse(
    @SerializedName("Time Series (Daily)") val timeSeries: Map<String, StockTimeSeries>?
) {
    data class StockTimeSeries(
        @SerializedName("1. open") val open: String,
        @SerializedName("2. high") val high: String,
        @SerializedName("3. low") val low: String,
        @SerializedName("4. close") val close: String,
        @SerializedName("5. volume") val volume: String
    )
}

fun StockApiResponse.toStockData(ticker: String): StockData? {
    val latestEntry = timeSeries?.entries?.firstOrNull() ?: return null
    val latestDate = latestEntry.key
    val latestData = latestEntry.value
    return StockData(
        symbol = ticker,
        date = latestDate,
        price = latestData.close.toDoubleOrNull() ?: 0.0,
        volume = latestData.volume.toLongOrNull() ?: 0L
    )
}
