package ar.edu.uade.cocktailapp.data

import android.icu.text.StringSearch
import ar.edu.uade.cocktailapp.domain.ICocktailRepository

class CocktailRepository  (
    val cocktailDataSource : ICocktailDataSource = CocktailApiDataSource()
): ICocktailRepository
{
    override suspend fun fetchCocktails(search: String) : List<Cocktail> {
        return cocktailDataSource.getCocktailList(search)
    }

    override suspend fun fetchCocktail(cocktailId: Int): Cocktail {
        return cocktailDataSource.getCocktailById(cocktailId)
    }
}


