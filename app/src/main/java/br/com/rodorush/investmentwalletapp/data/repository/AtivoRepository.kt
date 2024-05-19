package br.com.rodorush.investmentwalletapp.data.repository

import android.content.Context
import br.com.rodorush.investmentwalletapp.data.dao.AtivoDao
import br.com.rodorush.investmentwalletapp.data.entity.AtivoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.Flow

class AtivoRepository(context: Context) : GenericRepository(context) {

    private val ativoDao: AtivoDao = database.ativoDao()

    fun getAllAtivos(): Flow<List<AtivoEntity>> {
        return ativoDao.getAllAtivos()
    }

    suspend fun addAtivo(ativo: AtivoEntity) {
        withContext(Dispatchers.IO) {
            ativoDao.insert(ativo)
        }
    }

    suspend fun updateAtivo(ativo: AtivoEntity) {
        withContext(Dispatchers.IO) {
            ativoDao.update(ativo)
        }
    }

    suspend fun deleteAtivo(ativo: AtivoEntity) {
        withContext(Dispatchers.IO) {
            ativoDao.delete(ativo)
        }
    }
}
