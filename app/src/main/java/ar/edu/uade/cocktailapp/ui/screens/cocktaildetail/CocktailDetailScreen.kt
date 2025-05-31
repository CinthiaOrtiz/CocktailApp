package ar.edu.uade.cocktailapp.ui.screens.cocktaildetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun CocktailDetailScreen(
    cocktailId: Int,
    navController: NavHostController, // <-- Agregamos el navController aquí
    modifier: Modifier = Modifier,
    vm: CocktailDetailScreenViewModel = viewModel()
) {
    vm.setCocktailId(cocktailId)

    if (vm.uiState.cocktailDetail.idDrink.isNullOrBlank()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        // Llamada correcta al Composable de detalle
        CocktailUIItemDetail(
            cocktail = vm.uiState.cocktailDetail,
            navController = navController // <-- Pasamos el navController
        )
    }
}


