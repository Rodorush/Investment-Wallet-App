package br.com.rodorush.investmentwalletapp.dao.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car_carteiras")
data class CarteiraEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "car_id") val id: Long = 0,
    @ColumnInfo(name = "car_nome") val nome: String
)