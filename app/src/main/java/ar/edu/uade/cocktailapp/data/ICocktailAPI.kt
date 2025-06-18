package ar.edu.uade.cocktailapp.data


import retrofit2.http.GET
import retrofit2.http.Query

//endpoinds que vamos a consumir de la API

interface ICocktailAPI {


    @GET("search.php")
    suspend fun getCocktailSearch(
        @Query("s") search: String
    ): CocktailResult



    @GET("lookup.php")
    suspend fun getCocktail (
        @Query("i") cocktailId: Int
    ) : CocktailResult

    @GET("random.php")
    suspend fun getRandomCocktail(): CocktailResponse


}