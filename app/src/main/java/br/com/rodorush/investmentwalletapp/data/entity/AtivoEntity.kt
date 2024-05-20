package br.com.rodorush.investmentwalletapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "atv_ativos")
data class AtivoEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "atv_id") val id: Int = 0,
    @ColumnInfo(name = "atv_nome") val nome: String,
    @ColumnInfo(name = "atv_ticker") val ticker: String,
    @ColumnInfo(name = "atv_ultimoPreco") val ultimoPreco: Double? = null,
    @ColumnInfo(name = "atv_dataUltimoPreco") val dataUltimoPreco: String? = null
)