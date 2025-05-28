package ar.edu.uade.cocktailapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.uade.cocktailapp.ui.screens.cocktaildetail.CocktailDetailScreen
import ar.edu.uade.cocktailapp.ui.screens.cocktaillist.CocktailListScreen
import ar.edu.uade.cocktailapp.ui.screens.splash.SplashScreen


@Composable
fun NavigationStack() {
    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route
    ) {

        composable(route = Screens.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable (route = Screens.CocktailList.route) {
            CocktailListScreen(navController = navController)
        }
        composable (route = Screens.CocktailDetail.route + "/{cocktailId}") {
            var id = it.arguments?.getString("cocktailId")
            val cocktailId = id?.toIntOrNull()
            CocktailDetailScreen(cocktailId ?: 0)
        }

    }


}