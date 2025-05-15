package ar.edu.uade.cocktailapp.data

interface ICocktailDataSource {
    suspend fun getCocktailList(): List<Cocktail>

}