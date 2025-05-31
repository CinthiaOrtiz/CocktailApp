package ar.edu.uade.cocktailapp.ui.screens

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.uade.cocktailapp.ui.screens.cocktaildetail.CocktailDetailScreen
import ar.edu.uade.cocktailapp.ui.screens.cocktaillist.CocktailListScreen
import ar.edu.uade.cocktailapp.ui.screens.extras.WelcomeScreen
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
        composable(route = Screens.Welcome.route) {
            WelcomeScreen(navController = navController)
        }
        composable (route = Screens.CocktailList.route) {
            CocktailListScreen(navController = navController)
        }
        composable (route = Screens.CocktailDetail.route + "/{cocktailId}") {
            var id = it.arguments?.getString("cocktailId")
            val cocktailId = id?.toIntOrNull()
            Log.d("PRUEBA DETAIL", cocktailId.toString())
            CocktailDetailScreen(
                cocktailId = cocktailId ?: 0,
                navController = navController // <--  pasamos el navController
            )
        }

    }
}