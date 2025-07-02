package ar.edu.uade.cocktailapp.data

import android.util.Log
import ar.edu.uade.cocktailapp.data.local.CocktailDataBaseProvider
import ar.edu.uade.cocktailapp.data.local.CocktailDatabase
import ar.edu.uade.cocktailapp.data.local.toExternal
import ar.edu.uade.cocktailapp.data.local.toLocal
import com.google.firebase.auth.FirebaseAuth
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

        val dbLocal = CocktailDataBaseProvider.dbLocal

        // ROOM
        val cocktailLocal = dbLocal.cocktailDao().findById(cocktailId)
        if (cocktailLocal != null) {
            Log.d("COCKTAILDB", "Encontrado en Room")
            return cocktailLocal.toExternal()
        }

        // FIRESTORE (solo si el usuario está logueado)
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            val db = FirebaseFirestore.getInstance()
            val docRef = db.collection("users")
                .document(userId)
                .collection("favorites")
                .document(cocktailId.toString())

            // Intento buscar el cóctel en Firestore
            val snapshot = docRef.get().await()
            val cocktailFromFirestore = snapshot.toObject(Cocktail::class.java)

            if (cocktailFromFirestore != null) {
                Log.d("COCKTAILDB", "Encontrado en Firestore")

                val cocktailLocalFromFS = cocktailFromFirestore.toLocal()
                dbLocal.cocktailDao().insert(cocktailLocalFromFS)

                return cocktailFromFirestore
            }
        }

        Log.d("COCKTAILDB", "No encontrado en Room ni Firestore, llamando a la API")

        // Si no está, lo traigo desde la API
        val response = RetrofitInstance.cocktailApi.getCocktail(cocktailId)
        val drinks = response.drinks

        if (drinks.isNullOrEmpty()) {
            throw IllegalStateException("No se encontró el cóctel con ID: $cocktailId")
        }

        val cocktail = drinks[0]

        // Lo guardo en Room
        val cocktailLocalNew = cocktail.toLocal()
        dbLocal.cocktailDao().insert(cocktailLocalNew)

        // Y si el usuario está logueado, también lo guardo en Firestore
        userId?.let {
            val db = FirebaseFirestore.getInstance()
            val docRef = db.collection("users")
                .document(it)
                .collection("favorites")
                .document(cocktailId.toString())

            docRef.set(cocktail)
        }

        return cocktail
    }
}
