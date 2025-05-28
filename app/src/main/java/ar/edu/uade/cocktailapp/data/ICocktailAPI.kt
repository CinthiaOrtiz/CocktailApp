package ar.edu.uade.cocktailapp.data

import android.icu.text.StringSearch
import androidx.privacysandbox.ads.adservices.appsetid.AppSetId
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//endpoinds que vamos a consumir de la API

interface ICocktailAPI {

    /*@GET("filter.php")
suspend fun getCocktailSearch(
    @Query("c") search: String
): CocktailResult */

    @GET("search.php")
    suspend fun getCocktailSearch(
        @Query("s") search: String
    ): CocktailResult



    @GET("cocktail/{cocktailId")
    suspend fun getCocktail (
        @Path("cocktailId") cocktailId: Int
    ) : CocktailDetailResult



}