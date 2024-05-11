package br.com.rodorush.investmentwalletapp.dao.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "atv_ativos")
data class AtivoEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "atv_id") val id: Long = 0,
    @ColumnInfo(name = "atv_sigla") val sigla: String,
    @ColumnInfo(name = "atv_nome") val nome: String,
    @ColumnInfo(name = "atv_mercado") val mercado: String
)