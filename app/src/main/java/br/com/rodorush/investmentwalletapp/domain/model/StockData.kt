package br.com.rodorush.investmentwalletapp.domain.model

data class StockData(
    val symbol: String,
    val date: String,
    val price: Double,
    val volume: Long
)
