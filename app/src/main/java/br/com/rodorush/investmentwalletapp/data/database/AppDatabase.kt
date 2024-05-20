package br.com.rodorush.investmentwalletapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.rodorush.investmentwalletapp.data.dao.AtivoDao
import br.com.rodorush.investmentwalletapp.data.dao.CarteiraDao
import br.com.rodorush.investmentwalletapp.data.dao.LancamentoAtivoDao
import br.com.rodorush.investmentwalletapp.data.entity.AtivoEntity
import br.com.rodorush.investmentwalletapp.data.entity.CarteiraEntity
import br.com.rodorush.investmentwalletapp.data.entity.LancamentoAtivoEntity

@Database(
    entities = [AtivoEntity::class, CarteiraEntity::class, LancamentoAtivoEntity::class],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun ativoDao(): AtivoDao
    abstract fun carteiraDao(): CarteiraDao
    abstract fun lancamentoAtivoDao(): LancamentoAtivoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "iwdb"
                )
                    .fallbackToDestructiveMigration() // Adicione esta linha
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
