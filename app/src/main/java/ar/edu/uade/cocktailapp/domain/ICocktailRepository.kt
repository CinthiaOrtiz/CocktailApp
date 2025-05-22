package ar.edu.uade.cocktailapp.domain

import android.icu.text.StringSearch
import ar.edu.uade.cocktailapp.data.Cocktail

interface ICocktailRepository {
    suspend fun fetchCocktail(search: String) : List<Cocktail>

}