package br.com.rodorush.investmentwalletapp.data.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.rodorush.investmentwalletapp.data.entities.AtivoEntity

@Dao
interface AtivoDao : GenericDao<AtivoEntity> {

    @Query("SELECT * FROM atv_ativos")
    fun getAllAtivos(): List<AtivoEntity>
}
