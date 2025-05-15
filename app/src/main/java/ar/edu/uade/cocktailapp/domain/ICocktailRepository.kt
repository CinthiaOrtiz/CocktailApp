package ar.edu.uade.cocktailapp.domain

import ar.edu.uade.cocktailapp.data.Cocktail

interface ICocktailRepository {
    suspend fun fetchCocktail() : List<Cocktail>

}