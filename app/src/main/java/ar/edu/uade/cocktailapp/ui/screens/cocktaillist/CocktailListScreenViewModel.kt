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
        // Carga inicial: buscar 5 cócteles aleatorios
        fetchRandomCocktails()
    }

    // FUNCIÓN PARA CARGAR 5 CÓCTELES ALEATORIOS EN EL HOME
    private fun fetchRandomCocktails() {
        // Cancelo la corrutina anterior si estaba en ejecución
        fetchJob?.cancel()

        fetchJob = viewModelScope.launch {
            try {
                // Indico que la carga comienza
                uiState = uiState.copy(isLoading = true)

                // Obtengo la lista completa y tomo 5 aleatorios de la BUSQUEDA
                val allCocktails = cocktailRepository.fetchCocktails("a") ?: emptyList() // Búsqueda general para obtener varios
                val randomList = allCocktails.take(5) // Limito a 5 resultados

                // Actualizo el estado con la lista aleatoria
                uiState = uiState.copy(
                    cocktailList = randomList,
                    isLoading = false
                )
            } catch (e: Exception) {
                uiState = uiState.copy(isLoading = false)
                Log.e("CocktailApp", "Error cargando cócteles aleatorios", e)
            }
        }
    }

    // FUNCIÓN PARA BUSCAR CÓCTELES
    fun fetchCocktail(search: String) {
        // Si la búsqueda está vacía, usamos "a" como búsqueda por defecto (para obtener resultados)
        val query = if (search.isBlank()) "a" else search

        // Cancelo la corrutina anterior si estaba en ejecución
        fetchJob?.cancel()

        fetchJob = viewModelScope.launch {
            try {
                // Indico que la carga comienza
                uiState = uiState.copy(isLoading = true)

                // Hago la llamada a la API a través del repositorio
                val result = cocktailRepository.fetchCocktails(query)

                // LIMITAR a 5 resultados
                val limitedResult = result?.take(5) ?: emptyList()

                // Actualizo el estado con la lista recibida y apago la carga
                uiState = uiState.copy(
                    cocktailList = limitedResult,
                    isLoading = false
                )

            } catch (e: CancellationException) {
                Log.w("CocktailApp", "Corrutina cancelada")
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
        fetchCocktail(search) // Busca con el nuevo texto
    }
}
