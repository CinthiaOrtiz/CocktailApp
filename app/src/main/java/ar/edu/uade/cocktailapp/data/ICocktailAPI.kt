package ar.edu.uade.cocktailapp.data

import android.icu.text.StringSearch
import retrofit2.http.GET
import retrofit2.http.Query

//endpoinds que vamos a consumir de la API

interface ICocktailAPI {

    @GET("search.php")
    suspend fun getCocktailSearch(
        @Query("s") search: String
    ): CocktailResult

    /*@GET("filter.php")
    suspend fun getCocktailSearch(
        @Query("c") search: String
    ): CocktailResult */



}