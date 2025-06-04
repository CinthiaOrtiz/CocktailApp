package ar.edu.uade.cocktailapp.ui.screens.cocktaildetail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.uade.cocktailapp.data.CocktailApiDataSource
import ar.edu.uade.cocktailapp.data.CocktailRepository
import ar.edu.uade.cocktailapp.domain.ICocktailRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.CancellationException

class CocktailDetailScreenViewModel(
   private val cocktailRepository: ICocktailRepository = CocktailRepository(CocktailApiDataSource())
) : ViewModel()

{

    var uiState by mutableStateOf(CocktailDetailScreenState())
        private set


    private var fetchJob: Job? = null
    fun fetchCocktail() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {

                val detail = cocktailRepository.fetchCocktail(uiState.cocktailId)

                uiState = uiState.copy(
                    cocktailId = uiState.cocktailId,
                    cocktailDetail = cocktailRepository.fetchCocktail(uiState.cocktailId)
                )
            } catch (e: CancellationException) {
                // Cancelación esperada, no hacemos nada
                Log.d("CocktailDetailViewModel", "Búsqueda cancelada correctamente")
            } catch (e: Exception) {
                Log.e("CocktailDetailViewModel", "Error inesperado", e)
            }
        }
    }

    // ACTUALIZO EL ID DEL CÓCTEL Y LUEGO LLAMO A LA API PARA RECUPERAR LOS DETALLES
    fun setCocktailId(cocktailId: Int): Unit {
        uiState = uiState.copy(
            cocktailId = cocktailId,
            cocktailDetail = uiState.cocktailDetail
        )
        fetchCocktail()
    }
}
