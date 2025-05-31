package ar.edu.uade.cocktailapp.data

import android.util.Log
import okio.IOException
import retrofit2.HttpException
// CONSUMIMOS NUESTRA API UTILIZANDO RETROFIT (LIBRERIA)

class CocktailApiDataSource : ICocktailDataSource {
    private val TAG = "CocktailApp"

    // CONTENEDOR DE DATOS!
    override suspend fun getCocktailList(search: String): List<Cocktail> {
        Log.d(TAG, "CocktailApiDataSource.getCocktailList")

        return try {
            Log.d(TAG, "CocktailApiDataSource.getCocktailList Search: $search")
            val cocktailResult = RetrofitInstance.cocktailApi.getCocktailSearch(search)

            // Si la API devuelve drinks como null, devuelvo una lista vacía
            if (cocktailResult.drinks == null) {
                Log.d(TAG, "No se encontraron cócteles")
                return emptyList()
            }

            Log.d(TAG, "CocktailApiDataSource.getCocktailList Result: ${cocktailResult.drinks.size}")
            return cocktailResult.drinks
        } catch (e: HttpException) {
            Log.e(TAG, "Error de HTTP: ${e.code()} ${e.message} ")
            emptyList()
        } catch (e: IOException) {
            Log.e(TAG, "Error de Network: ${e.localizedMessage} ")
            emptyList()
        } catch (e: Exception) {
            Log.e(TAG, "Error desconocido: ${e.localizedMessage} ")
            emptyList()
        }
    }

    // RECUPERO CÓCTEL POR ID
    override suspend fun getCocktailById(cocktailId: Int): Cocktail {
        val response = RetrofitInstance.cocktailApi.getCocktail(cocktailId)
        val drinks = response.drinks
        if (drinks.isNullOrEmpty()) {
            throw IllegalStateException("No se encontró el cóctel con ID: $cocktailId")
        }
        return drinks[0]
    }

}

        /*
        return try {
            // Llamo a la API de búsqueda por ID
            val response = RetrofitInstance.cocktailApi.getCocktail(cocktailId)

            // Devuelvo el primer cóctel si existe, si no devuelvo vacío
            val cocktail = response.drinks
            cocktail ?: emptyCocktail()

        } catch (e: HttpException) {
            Log.e(TAG, "Error de HTTP al buscar cocktail por ID: ${e.code()} ${e.message()}")
            emptyCocktail()
        } catch (e: IOException) {
            Log.e(TAG, "Error de Network al buscar cocktail por ID: ${e.localizedMessage}")
            emptyCocktail()
        } catch (e: Exception) {
            Log.e(TAG, "Error desconocido al buscar cocktail por ID: ${e.localizedMessage}")
            emptyCocktail()
        }
         */
