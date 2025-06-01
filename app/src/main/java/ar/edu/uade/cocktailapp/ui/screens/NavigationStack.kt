package ar.edu.uade.cocktailapp.ui.screens

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.uade.cocktailapp.ui.screens.cocktaildetail.CocktailDetailScreen
import ar.edu.uade.cocktailapp.ui.screens.cocktaillist.CocktailListScreen
import ar.edu.uade.cocktailapp.ui.screens.extras.WelcomeScreen
import ar.edu.uade.cocktailapp.ui.screens.login.LoginScreen
import ar.edu.uade.cocktailapp.ui.screens.splash.SplashScreen
import com.google.firebase.auth.FirebaseAuth
@Composable
fun NavigationStack(
    navController: NavHostController,
    onGoogleLoginClick: () -> Unit,
    onLogoutClick: () -> Unit,
    userSignedIn: Boolean
) {
    // 🚀 Escucha el estado del login y navega automáticamente a CocktailList
    LaunchedEffect(userSignedIn) {
        if (userSignedIn) {
            navController.navigate(Screens.CocktailList.route) {
                popUpTo(Screens.Login.route) { inclusive = true }
            }
        }
    }

    // 🗺️ Definición de navegación
    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route
    ) {
        // 🌊 Pantalla Splash
        composable(route = Screens.Splash.route) {
            SplashScreen(navController = navController)
        }

        // 🌟 Pantalla Welcome (pantalla extra)
        composable(route = Screens.Welcome.route) {
            WelcomeScreen(navController = navController)
        }

        // 🏡 Pantalla principal (lista de cócteles)
        composable(route = Screens.CocktailList.route) {
            CocktailListScreen(
                navController = navController,
                onLogoutClick = onLogoutClick
            )
        }

        // 🍹 Detalle de cóctel
        composable(route = Screens.CocktailDetail.route + "/{cocktailId}") {
            val id = it.arguments?.getString("cocktailId")
            val cocktailId = id?.toIntOrNull() ?: 0
            CocktailDetailScreen(
                cocktailId = cocktailId,
                navController = navController
            )
        }

        // 🔑 Pantalla de Login (Google Sign-In)
        composable(route = Screens.Login.route) {
            LoginScreen(
                navController = navController,
                onGoogleLoginClick = onGoogleLoginClick
            )
        }
    }
}
