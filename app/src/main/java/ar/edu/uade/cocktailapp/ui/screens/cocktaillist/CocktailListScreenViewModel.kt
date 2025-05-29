package ar.edu.uade.cocktailapp.ui.screens.cocktaillist

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.uade.cocktailapp.data.CocktailRepository
import ar.edu.uade.cocktailapp.domain.ICocktailRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okio.IOException
class CocktailListScreenViewModel(
    private val cocktailRepository: ICocktailRepository = CocktailRepository()
) : ViewModel() {

    // LA VISTA OBSERVA LOS CAMBIOS EN EL OBJETO
    var uiState by mutableStateOf(CocktailListScreenState(isLoading = false))
        private set

    private var fetchJob: Job? = null

    init {
        // fetchCocktail()
    }

    // FUNCIÓN PARA BUSCAR CÓCTELES
    fun fetchCocktail() {
        // Cancelo la corrutina anterior si estaba en ejecución
        fetchJob?.cancel()

        fetchJob = viewModelScope.launch {
            try {
                // Indico que la carga comienza
                uiState = uiState.copy(isLoading = true)

                // Hago la llamada a la API a través del repositorio
                val result = cocktailRepository.fetchCocktails(uiState.searchQuery)

                // Actualizo el estado con la lista recibida y apago la carga
                uiState = uiState.copy(
                    cocktailList = result,
                    isLoading = false
                )

            } catch (e: CancellationException) {
                Log.w("CocktailApp", "Corrutina cancelada")
                // Aseguro que no quede el loading colgado
                uiState = uiState.copy(isLoading = false)

            } catch (e: IOException) {
                Log.e("CocktailApp", "Error recuperando lista de cócteles", e)
                uiState = uiState.copy(isLoading = false)

            } catch (e: Exception) {
                Log.e("CocktailApp", "Error desconocido", e)
                uiState = uiState.copy(isLoading = false)
            }
        }
    }

    // FUNCIÓN PARA ACTUALIZAR LA LÍNEA DE BÚSQUEDA
    fun searchChange(search: String) {
        uiState = uiState.copy(searchQuery = search, cocktailList = uiState.cocktailList)
    }
}
