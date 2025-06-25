package ar.edu.uade.cocktailapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Database(entities = [CocktailLocal::class], version = 1)

abstract class CocktailDatabase : RoomDatabase() {

    abstract fun cocktailDao(): ICocktailDao

    companion object {

        @Volatile
        private var _instance: CocktailDatabase? = null

        fun getInstance(context: Context) : CocktailDatabase = _instance ?: synchronized(this){
            _instance  ?: buildDatabase(context)
        }


        private fun buildDatabase(context: Context): CocktailDatabase =
            Room.databaseBuilder(context, CocktailDatabase::class.java, "cocktail_database")
                .fallbackToDestructiveMigration()
                .build()

        suspend fun clean(context: Context) = coroutineScope {
            launch(Dispatchers.IO) {
                getInstance(context).clearAllTables()
            }
        }

    }




}