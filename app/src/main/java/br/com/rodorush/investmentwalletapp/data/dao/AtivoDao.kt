package br.com.rodorush.investmentwalletapp.data.dao

import androidx.room.*
import br.com.rodorush.investmentwalletapp.data.entity.AtivoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AtivoDao : GenericDao<AtivoEntity> {

    @Query("SELECT * FROM atv_ativos")
    fun getAllAtivos(): Flow<List<AtivoEntity>>

    @Query("SELECT * FROM atv_ativos WHERE atv_ticker = :ticker")
    suspend fun getAtivosByTicker(ticker: String): List<AtivoEntity>

    @Update
    suspend fun updateAtivo(ativo: AtivoEntity)
}
