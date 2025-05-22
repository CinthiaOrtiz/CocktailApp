package ar.edu.uade.cocktailapp.ui.screens.cocktaillist

import ads_mobile_sdk.ui
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
        // fetchCocktail()
    }

    private var fetchJob: Job? = null


    fun fetchCocktail() {

        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                // repositorio cuando busca los cocteles, le pasa el texto de busqueda //cocktailRepository.fetchCocktail
                // eso se traslada al repositorio, este se lo translada al cocktailDataSouce y este a la API.getCocktailSearch
                uiState = uiState.copy(cocktailList = cocktailRepository.fetchCocktail(uiState.searchQuery ))

            }
            catch (e: IOException) {
                Log.e("CocktailApp", "Error recuperando lista de Cocteles ")
            }


        }


        //esta func no puede estar suspendida, funcion que inicia la corrutina
        // ESTADO. COPIA ESTADO. PISO VALOR. SOBREESCRIBO ESTADO ACTUAL
        // ME TRAIGO LA LISTA DE COCKTELES DEL JSON
        //uiState = uiState.copy(cocktailList = cocktailRepository.fetchCocktail())

    }

    // linea de busqueda -- CUANDO CAMBIE LA BUSQUEDA -- VOY A ACTUALIZAR EL ESTADO. searchQuery ingreso escrito del usuario
    // funcion tiene la .copia. searchquery cambia por search. cocktailList se mantiene
    fun searchChange(search: String) {
        uiState = uiState.copy(searchQuery = search, cocktailList = uiState.cocktailList)

    }


}


