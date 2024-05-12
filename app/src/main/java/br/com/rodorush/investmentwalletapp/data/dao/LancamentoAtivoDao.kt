package br.com.rodorush.investmentwalletapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.rodorush.investmentwalletapp.data.entities.LancamentoAtivoEntity

@Dao
interface LancamentoAtivoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLancamento(lancamento: LancamentoAtivoEntity)

    @Query("SELECT * FROM lat_lancamentos_ativos")
    suspend fun getAllLancamentos(): List<LancamentoAtivoEntity>
}
