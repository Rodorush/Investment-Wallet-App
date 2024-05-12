package br.com.rodorush.investmentwalletapp.data.repository

import android.content.Context
import br.com.rodorush.investmentwalletapp.data.database.AppDatabase

open class GenericRepository(private val context: Context) {

    protected val database: AppDatabase = AppDatabase.getDatabase(context)
}
