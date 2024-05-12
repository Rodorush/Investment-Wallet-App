package br.com.rodorush.investmentwalletapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.rodorush.investmentwalletapp.data.entities.CarteiraEntity

@Dao
interface CarteiraDao : GenericDao<CarteiraEntity> {

    @Query("SELECT * FROM car_carteiras")
    fun getAllCarteiras(): List<CarteiraEntity>
}
