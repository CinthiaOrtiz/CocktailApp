package ar.edu.uade.cocktailapp.ui.screens.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ar.edu.uade.cocktailapp.ui.commons.FavoriteCardItem
import ar.edu.uade.cocktailapp.ui.screens.splash.SplashScreenLoading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteListScreen(
    navController: NavHostController,
    vm: FavoriteListScreenViewModel = viewModel()
) {
    val favoriteCocktails by vm.favoriteCocktails.collectAsState()
    val isLoading by vm.isLoading.collectAsState()

    // Carga inicial de favoritos desde Firebase
    LaunchedEffect(Unit) {
        vm.loadFavorites()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Favoritos", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        },
        containerColor = Color.Black
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when {
                isLoading -> {
                    // Loading Spinner
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        SplashScreenLoading()
                    }
                }

                favoriteCocktails.isEmpty() -> {
                    // Mensaje si no hay favoritos
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            "No tienes cócteles favoritos todavía.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White
                        )
                    }
                }

                else -> {
                    // Lista de favoritos
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        items(favoriteCocktails) { cocktail ->
                            FavoriteCardItem(
                                cocktail = cocktail,
                                onClick = {
                                    // Navegar al detalle del cóctel
                                    val cocktailId = cocktail.idDrink?.toIntOrNull() ?: return@FavoriteCardItem
                                    navController.navigate("cocktail_detail_screen/$cocktailId")
                                },
                                onToggleFavorite = {
                                    // Eliminar de favoritos
                                    vm.removeFavorite(cocktail)
                                }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }
        }
    }
}
