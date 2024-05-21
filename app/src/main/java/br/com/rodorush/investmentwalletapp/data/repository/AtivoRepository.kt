package br.com.rodorush.investmentwalletapp.data.repository

import android.content.Context
import br.com.rodorush.investmentwalletapp.data.dao.AtivoDao
import br.com.rodorush.investmentwalletapp.data.database.AppDatabase
import br.com.rodorush.investmentwalletapp.data.entity.AtivoEntity
import kotlinx.coroutines.flow.Flow

class AtivoRepository(context: Context) : IAtivoRepository {

    private val ativoDao: AtivoDao = AppDatabase.getDatabase(context).ativoDao()

    override fun getAllAtivos(): Flow<List<AtivoEntity>> {
        return ativoDao.getAllAtivos()
    }

    override suspend fun addAtivo(ativo: AtivoEntity) {
        ativoDao.insert(ativo)
    }

    override suspend fun updateAtivo(ativo: AtivoEntity) {
        ativoDao.update(ativo)
    }

    override suspend fun deleteAtivo(ativo: AtivoEntity) {
        ativoDao.delete(ativo)
    }

    override suspend fun getAtivosByTicker(ticker: String): List<AtivoEntity> {
        return ativoDao.getAtivosByTicker(ticker)
    }
}
