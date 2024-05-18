package br.com.rodorush.investmentwalletapp.data.repository

import android.content.Context
import br.com.rodorush.investmentwalletapp.data.dao.CarteiraDao
import br.com.rodorush.investmentwalletapp.data.entity.CarteiraEntity

class CarteiraRepository(context: Context) : GenericRepository(context) {

    private val carteiraDao: CarteiraDao = database.carteiraDao()

    suspend fun getAllCarteiras(): List<CarteiraEntity> {
        return carteiraDao.getAllCarteiras()
    }

    suspend fun insertCarteira(carteira: CarteiraEntity) {
        carteiraDao.insert(carteira)
    }

    suspend fun updateCarteira(carteira: CarteiraEntity) {
        carteiraDao.update(carteira)
    }

    suspend fun deleteCarteira(carteira: CarteiraEntity) {
        carteiraDao.delete(carteira)
    }
}