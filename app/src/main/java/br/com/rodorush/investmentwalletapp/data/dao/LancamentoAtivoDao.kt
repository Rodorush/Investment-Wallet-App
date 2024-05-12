package br.com.rodorush.investmentwalletapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.rodorush.investmentwalletapp.data.entities.LancamentoAtivoEntity

@Dao
interface LancamentoAtivoDao : GenericDao<LancamentoAtivoEntity> {

    @Query("SELECT * FROM lat_lancamentos_ativos")
    fun getAllLancamentos(): List<LancamentoAtivoEntity>
}
