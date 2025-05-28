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
    var uiState by mutableStateOf(CocktailListScreenState())
        private set

    // Inicialización: podrías llamar a fetchCocktail() acá si quisieras cargar algo al iniciar
    init {
        // fetchCocktail()
    }

    private var fetchJob: Job? = null

    // FUNCIÓN PARA BUSCAR CÓCTELES
    // Repositorio busca los cócteles con el texto de búsqueda (searchQuery)
    // Eso se traslada al repositorio -> luego al CocktailDataSource -> y finalmente a la API (getCocktailSearch)
    fun fetchCocktail() {

        // Cancelo la corrutina anterior si estaba en ejecución
        fetchJob?.cancel()

        fetchJob = viewModelScope.launch {
            try {
                // Hago la llamada a la API a través del repositorio
                val result = cocktailRepository.fetchCocktails(uiState.searchQuery)

                // Actualizo el estado con la lista recibida
                uiState = uiState.copy(cocktailList = result)

            } catch (e: CancellationException) {
                // Cancelación esperada (por ejemplo, si el usuario escribe rápido y se interrumpe la búsqueda anterior)
                Log.w("CocktailApp", "Corrutina cancelada")

            } catch (e: IOException) {
                // Error de red, por ejemplo sin conexión
                Log.e("CocktailApp", "Error recuperando lista de cócteles", e)

            } catch (e: Exception) {
                // Cualquier otro error inesperado
                Log.e("CocktailApp", "Error desconocido", e)
            }
        }

        // Esta función no puede ser suspendida (es decir, no puede tener "suspend")
        // Por eso se usa viewModelScope.launch para iniciar una corrutina
        // ESTADO. COPIA ESTADO. PISO VALOR. SOBREESCRIBO ESTADO ACTUAL
        // ME TRAIGO LA LISTA DE CÓCTELES DEL JSON
        // uiState = uiState.copy(cocktailList = cocktailRepository.fetchCocktail())
    }

    // FUNCIÓN PARA ACTUALIZAR LA LÍNEA DE BÚSQUEDA
    // CUANDO CAMBIE LA BÚSQUEDA -> ACTUALIZO EL ESTADO
    // searchQuery: ingreso escrito del usuario
    // Uso .copy para actualizar solo la parte que cambió (la búsqueda)
    fun searchChange(search: String) {
        uiState = uiState.copy(searchQuery = search, cocktailList = uiState.cocktailList)
    }
}
