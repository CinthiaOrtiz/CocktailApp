package ar.edu.uade.cocktailapp.ui.screens.cocktaillist

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.uade.cocktailapp.data.Cocktail
import ar.edu.uade.cocktailapp.data.CocktailRepository
import ar.edu.uade.cocktailapp.data.CocktailResult
import ar.edu.uade.cocktailapp.data.CocktailTestDataSource
import ar.edu.uade.cocktailapp.domain.ICocktailRepository
import com.google.gson.Gson
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okio.IOException

class CocktailListScreenViewModel(
    private val cocktailRepository: ICocktailRepository = CocktailRepository()
)
    : ViewModel()
{
        //var repo : CocktailRepository = CocktailRepository()
        //val repo = CocktailRepository(CocktailTestDataSource())
    // LA VISTA OBSERVA LOS CAMBIOS EN EL OBJETO
    var uiState by mutableStateOf(CocktailListScreenState())
        private set

    init {
        fetchCocktail()
    }

    private var fetchJob: Job? = null


    fun fetchCocktail() {

        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                uiState = uiState.copy(cocktailList = cocktailRepository.fetchCocktail())

            }
            catch (e: IOException) {
                Log.e("CocktailApp", "Error recuperando lista de Anime ")
            }


        }


        //esta func no puede estar suspendida, funcion que inicia la corrutina
        // ESTADO. COPIA ESTADO. PISO VALOR. SOBREESCRIBO ESTADO ACTUAL
        // ME TRAIGO LA LISTA DE COCKTELES DEL JSON
        //uiState = uiState.copy(cocktailList = cocktailRepository.fetchCocktail())

    }

}


