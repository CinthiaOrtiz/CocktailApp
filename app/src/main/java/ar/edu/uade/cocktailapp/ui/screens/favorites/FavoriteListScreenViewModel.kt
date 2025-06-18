package ar.edu.uade.cocktailapp.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.uade.cocktailapp.data.Cocktail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FavoriteListScreenViewModel : ViewModel() {

    private val _favoriteCocktails = MutableStateFlow<List<Cocktail>>(emptyList())
    val favoriteCocktails: StateFlow<List<Cocktail>> = _favoriteCocktails

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadFavorites() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val snapshot = FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(userId)
                    .collection("favorites")
                    .get()
                    .await()

                val list = snapshot.documents.mapNotNull { it.toObject(Cocktail::class.java) }

                println("Favoritos cargados: ${list.size}")
                _favoriteCocktails.value = list
            } catch (e: Exception) {
                println("Error al cargar favoritos: ${e.message}")
                _favoriteCocktails.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun removeFavorite(cocktail: Cocktail) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        FirebaseFirestore.getInstance()
            .collection("users")
            .document(userId)
            .collection("favorites")
            .document(cocktail.idDrink ?: return)
            .delete()
            .addOnSuccessListener {
                println("Favorito eliminado: ${cocktail.strDrink}")
                loadFavorites() // vuelve a cargar la lista actualizada
            }
            .addOnFailureListener { e ->
                println("Error al eliminar favorito: ${e.message}")
            }
    }


}
