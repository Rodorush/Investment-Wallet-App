package br.com.rodorush.investmentwalletapp.data.dao

import androidx.room.*

@Dao
interface GenericDao<E> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: E)

    @Update
    suspend fun update(entity: E)

    @Delete
    suspend fun delete(entity: E)
}