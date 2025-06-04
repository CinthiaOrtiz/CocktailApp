package ar.edu.uade.cocktailapp.data



interface ICocktailDataSource {
    suspend fun getCocktailList(search: String): List<Cocktail>?
    suspend fun getCocktailById(coktailId: Int) : Cocktail

}