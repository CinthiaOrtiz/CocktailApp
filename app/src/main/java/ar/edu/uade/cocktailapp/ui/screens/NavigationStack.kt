package ar.edu.uade.cocktailapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ar.edu.uade.cocktailapp.ui.screens.cocktaillist.CocktailListScreen
import ar.edu.uade.cocktailapp.ui.screens.extras.WelcomeScreen
import ar.edu.uade.cocktailapp.ui.screens.login.LoginScreen
import ar.edu.uade.cocktailapp.ui.screens.splash.SplashScreen
import com.tuapp.ui.screens.cocktaildetail.CocktailDetailScreen


@Composable
fun NavigationStack(
    navController: NavHostController,
    onGoogleLoginClick: (onSuccess: () -> Unit) -> Unit, // ✅ Modificado para aceptar onSuccess
    onLogoutClick: () -> Unit,
    userSignedIn: Boolean
) {

    // Redirige automáticamente SOLO si estamos en Login y ya se logueó
    LaunchedEffect(userSignedIn) {
        if (userSignedIn && navController.currentDestination?.route == Screens.Login.route) {
            navController.navigate(Screens.CocktailList.route) {
                popUpTo(Screens.Login.route) { inclusive = true }
            }
        }
    }
    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route
    ) {
        // Pantalla Splash
        composable(route = Screens.Splash.route) {
            SplashScreen(navController = navController)
        }

        // Pantalla Welcome
        composable(route = Screens.Welcome.route) {
            WelcomeScreen(navController = navController)
        }

        // Pantalla de Login (Google Sign-In)
        composable(route = Screens.Login.route) {
            LoginScreen(
                navController = navController,
                onGoogleLoginClick = { onSuccess ->
                    onGoogleLoginClick {
                        // Llama a onSuccess cuando el login fue exitoso
                        onSuccess()
                    }
                }
            )
        }

        // Pantalla principal HOME (lista de cócteles)
        composable(route = Screens.CocktailList.route) {
            CocktailListScreen(
                navController = navController,
                onLogoutClick = onLogoutClick
            )
        }

        // Detalle de cóctel
        composable(route = Screens.CocktailDetail.route + "/{cocktailId}") {
            val id = it.arguments?.getString("cocktailId")
            val cocktailId = id?.toIntOrNull() ?: 0
            CocktailDetailScreen(
                cocktailId = cocktailId,
                navController = navController
            )
        }
    }
}
