package ar.edu.uade.cocktailapp.data

import android.icu.text.StringSearch

interface ICocktailDataSource {
    suspend fun getCocktailList(search: String): List<Cocktail>?
    suspend fun getCocktailById(coktailId: Int) : Cocktail

}