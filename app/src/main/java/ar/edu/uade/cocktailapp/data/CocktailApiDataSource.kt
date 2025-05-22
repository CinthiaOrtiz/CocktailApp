package ar.edu.uade.cocktailapp.data

import android.icu.text.StringSearch
import android.util.Log
import com.google.gson.Gson
import okio.IOException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.HTTP
import retrofit2.http.Tag
import retrofit2.HttpException


// CONSUMIMOS NUESTRA API UTILIZANDO RETROFIT (LIBRERIA)

class CocktailApiDataSource : ICocktailDataSource {
    private val TAG = "CocktailApp"


    // CONTENEDOR DE DATOS!
    override suspend fun getCocktailList(search: String): List<Cocktail> {
        Log.d(TAG,"CocktailApiDataSource.getCocktailList")

        return try {

            Log.d(TAG,"CocktailApiDataSource.getCocktailList Search: $search")
            val cocktailResult = RetrofitInstance.cocktailApi.getCocktailSearch(search)
            Log.d(TAG,"CocktailApiDataSource.getCocktailList Result: ${cocktailResult.drinks.size}")
            return cocktailResult.drinks;

        }
        catch (e: HttpException) {
            Log.e(TAG, "Error de HTTP: ${e.code()} ${e.message} ")
            emptyList()
        }

        catch (e: IOException) {
            Log.e(TAG, "Error de Network: ${e.localizedMessage} ")
            emptyList()
        }

        catch (e: Exception) {
            Log.e(TAG, "Error desconocido: ${e.localizedMessage} ")
            emptyList()
        }


    }


}
