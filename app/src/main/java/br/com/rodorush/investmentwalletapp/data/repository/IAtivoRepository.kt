package br.com.rodorush.investmentwalletapp.data.repository

import br.com.rodorush.investmentwalletapp.data.entity.AtivoEntity
import kotlinx.coroutines.flow.Flow

interface IAtivoRepository {
    fun getAllAtivos(): Flow<List<AtivoEntity>>
    suspend fun addAtivo(ativo: AtivoEntity)
    suspend fun updateAtivo(ativo: AtivoEntity)
    suspend fun deleteAtivo(ativo: AtivoEntity)
    suspend fun getAtivosByTicker(ticker: String): List<AtivoEntity>
}
