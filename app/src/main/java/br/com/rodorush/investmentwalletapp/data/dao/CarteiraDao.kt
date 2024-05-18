package br.com.rodorush.investmentwalletapp.data.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.rodorush.investmentwalletapp.data.entity.CarteiraEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CarteiraDao : GenericDao<CarteiraEntity> {

    @Query("SELECT * FROM car_carteiras")
    fun getAllCarteiras(): Flow<List<CarteiraEntity>>
}
