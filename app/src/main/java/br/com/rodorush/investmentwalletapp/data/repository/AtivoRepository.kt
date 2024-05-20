package br.com.rodorush.investmentwalletapp.data.repository

import android.content.Context
import br.com.rodorush.investmentwalletapp.data.dao.AtivoDao
import br.com.rodorush.investmentwalletapp.data.entity.AtivoEntity
import kotlinx.coroutines.flow.Flow

class AtivoRepository(context: Context) : GenericRepository(context) {

    private val ativoDao: AtivoDao = database.ativoDao()

    fun getAllAtivos(): Flow<List<AtivoEntity>> {
        return ativoDao.getAllAtivos()
    }

    suspend fun getAtivosByTicker(ticker: String): List<AtivoEntity> {
        return ativoDao.getAtivosByTicker(ticker)
    }

    suspend fun addAtivo(ativo: AtivoEntity) {
        ativoDao.insert(ativo)
    }

    suspend fun updateAtivo(ativo: AtivoEntity) {
        ativoDao.updateAtivo(ativo)
    }

    suspend fun deleteAtivo(ativo: AtivoEntity) {
        ativoDao.delete(ativo)
    }
}
