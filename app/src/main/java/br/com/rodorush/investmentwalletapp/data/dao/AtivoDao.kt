package br.com.rodorush.investmentwalletapp.data.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.rodorush.investmentwalletapp.data.entity.AtivoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AtivoDao : GenericDao<AtivoEntity> {

    @Query("SELECT * FROM atv_ativos")
    fun getAllAtivos(): Flow<List<AtivoEntity>>
}
