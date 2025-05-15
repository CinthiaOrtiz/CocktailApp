package ar.edu.uade.cocktailapp.data

import ar.edu.uade.cocktailapp.domain.ICocktailRepository

class CocktailRepository  (
    val cocktailDataSource : ICocktailDataSource = CocktailTestDataSource()
): ICocktailRepository
{
    override suspend fun fetchCocktail() : List<Cocktail> {
        return cocktailDataSource.getCocktailList()
    }
}