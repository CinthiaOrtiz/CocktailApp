package ar.edu.uade.cocktailapp.data.local

import android.content.Context


object CocktailDataBaseProvider {
    lateinit var dbLocal : CocktailDatabase
        private set


    fun createDataBase(context: Context) {
        dbLocal = CocktailDatabase.getInstance(context)
    }
}