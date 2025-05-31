package ar.edu.uade.cocktailapp.domain

import android.adservices.adid.AdId
import ar.edu.uade.cocktailapp.data.Cocktail

interface ICocktailRepository {
    suspend fun fetchCocktails(search: String) : List<Cocktail>?
    suspend fun fetchCocktail(cocktailId: Int) : Cocktail

}