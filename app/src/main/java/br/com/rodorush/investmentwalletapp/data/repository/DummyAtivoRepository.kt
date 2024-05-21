package br.com.rodorush.investmentwalletapp.ui.activities

import br.com.rodorush.investmentwalletapp.data.entity.AtivoEntity
import br.com.rodorush.investmentwalletapp.data.repository.IAtivoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class DummyAtivoRepository : IAtivoRepository {
    override fun getAllAtivos(): Flow<List<AtivoEntity>> = flowOf(
        listOf(
            AtivoEntity(1, "Ativo 1", "ATV1"),
            AtivoEntity(2, "Ativo 2", "ATV2")
        )
    )

    override suspend fun addAtivo(ativo: AtivoEntity) {}
    override suspend fun updateAtivo(ativo: AtivoEntity) {}
    override suspend fun deleteAtivo(ativo: AtivoEntity) {}
    override suspend fun getAtivosByTicker(ticker: String): List<AtivoEntity> = listOf()
}
