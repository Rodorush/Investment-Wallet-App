package br.com.rodorush.investmentwalletapp.data.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.rodorush.investmentwalletapp.data.entity.CarteiraEntity

@Dao
interface CarteiraDao : GenericDao<CarteiraEntity> {

    @Query("SELECT * FROM car_carteiras")
    suspend fun getAllCarteiras(): List<CarteiraEntity>
}
