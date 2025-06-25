package ar.edu.uade.cocktailapp.data

import android.util.Log
import ar.edu.uade.cocktailapp.data.local.CocktailDataBaseProvider
import ar.edu.uade.cocktailapp.data.local.CocktailDatabase
import ar.edu.uade.cocktailapp.data.local.toExternal
import ar.edu.uade.cocktailapp.data.local.toLocal
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import okio.IOException
import retrofit2.HttpException

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
        Log.d("DEBUG_ORIGEN", "Llamada a getCocktailById con ID: $cocktailId")

        val db = FirebaseFirestore.getInstance()
        val dbLocal = CocktailDataBaseProvider.dbLocal

        //ROOM

        var cocktailLocal = dbLocal.cocktailDao().findById(cocktailId)
        if (cocktailLocal != null) {
            Log.d("COCKTAILDB", "encontrado en Room")
            return cocktailLocal.toExternal()
        }




        val docRef = db.collection("Favoritos").document(cocktailId.toString())
        // Intento buscar el cóctel en Firestore
        val snapshot = docRef.get().await()
        val cocktailFromFirestore = snapshot.toObject(Cocktail::class.java)

        if (cocktailFromFirestore != null) {
            Log.d("COCKTAILDB", "encontrado en Firestore")


            val cocktailLocal = cocktailFromFirestore.toLocal()
            dbLocal.cocktailDao().insert(cocktailLocal)

            return cocktailFromFirestore
        }
        else {
            Log.d("COCKTAILDB", "no encontrado en Firestore, llamando a la API")

            // Si no está, lo traigo desde la API
            val response = RetrofitInstance.cocktailApi.getCocktail(cocktailId)
            val drinks = response.drinks

            if (drinks.isNullOrEmpty()) {
                throw IllegalStateException("No se encontró el cóctel con ID: $cocktailId")
            }

            val cocktail = drinks[0]

            // Lo guardo en Firestore para la próxima vez
            docRef.set(cocktail)


            val cocktailLocal = cocktail.toLocal()
            dbLocal.cocktailDao().insert(cocktailLocal)

            return cocktail
        }
    }



}