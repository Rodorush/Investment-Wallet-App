package br.com.rodorush.investmentwalletapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.rodorush.investmentwalletapp.data.entities.AtivoEntity

@Dao
interface AtivoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAtivo(ativo: AtivoEntity)

    @Query("SELECT * FROM atv_ativos")
    suspend fun getAllAtivos(): List<AtivoEntity>
}
