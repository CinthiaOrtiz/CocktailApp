package ar.edu.uade.cocktailapp.ui.screens.cocktaildetail
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.uade.cocktailapp.data.Cocktail
import ar.edu.uade.cocktailapp.data.CocktailApiDataSource
import ar.edu.uade.cocktailapp.data.CocktailRepository
import ar.edu.uade.cocktailapp.data.RetrofitInstance
import ar.edu.uade.cocktailapp.domain.ICocktailRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CocktailDetailScreenViewModel(
    private val cocktailRepository: ICocktailRepository = CocktailRepository(CocktailApiDataSource())
) : ViewModel() {

    var uiState by mutableStateOf(CocktailDetailScreenState())
        private set

    private var fetchJob: Job? = null

    // Llama a la API y actualiza el cóctel actual
    fun fetchCocktail() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                val detail = cocktailRepository.fetchCocktail(uiState.cocktailId)

                uiState = uiState.copy(
                    cocktailId = uiState.cocktailId,
                    cocktailDetail = detail
                )
            } catch (e: CancellationException) {
                Log.d("CocktailDetailViewModel", "Búsqueda cancelada correctamente")
            } catch (e: Exception) {
                Log.e("CocktailDetailViewModel", "Error inesperado", e)
            }
        }
    }

    // ACTUALIZO EL ID DEL CÓCTEL Y LUEGO LLAMO A LA API SOLO SI ES UN ID NUEVO
    private var lastLoadedId: Int? = null

    fun setCocktailId(cocktailId: Int) {
        if (lastLoadedId == cocktailId) return // Previene llamadas repetidas
        lastLoadedId = cocktailId

        uiState = uiState.copy(
            cocktailId = cocktailId,
            cocktailDetail = uiState.cocktailDetail
        )

        fetchCocktail()

        // 🔁 Verificamos si es favorito en Firebase y actualizamos el estado
        isFavorite(cocktailId.toString()) { favorite ->
            uiState = uiState.copy(isFavorite = favorite)
        }
    }

    fun toggleFavorite(cocktail: Cocktail) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val docRef = FirebaseFirestore.getInstance()
            .collection("users")
            .document(userId)
            .collection("favorites")
            .document(cocktail.idDrink ?: return)

        docRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    // Ya es favorito → eliminar
                    docRef.delete()
                        .addOnSuccessListener {
                            println("Favorito eliminado: ${cocktail.strDrink}")
                            uiState = uiState.copy(isFavorite = false)
                        }
                        .addOnFailureListener { e ->
                            println("Error al eliminar favorito: ${e.message}")
                        }
                } else {
                    // No es favorito → agregar
                    docRef.set(cocktail)
                        .addOnSuccessListener {
                            println("Favorito guardado correctamente: ${cocktail.strDrink}")
                            uiState = uiState.copy(isFavorite = true)
                        }
                        .addOnFailureListener { e ->
                            println("Error al guardar favorito: ${e.message}")
                        }
                }
            }
            .addOnFailureListener { e ->
                println("Error al verificar si es favorito: ${e.message}")
            }
    }



    fun isFavorite(cocktailId: String, onResult: (Boolean) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(userId)
            .collection("favorites")
            .document(cocktailId)
            .get()
            .addOnSuccessListener { document ->
                onResult(document.exists())
            }
    }

    fun fetchRandomCocktail(onSuccess: (Cocktail) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.cocktailApi.getRandomCocktail()
                val cocktail = response.drinks.firstOrNull()
                if (cocktail != null) {
                    onSuccess(cocktail)
                }
            } catch (e: Exception) {
                println("Error al obtener cóctel aleatorio: ${e.message}")
            }
        }
    }


}
