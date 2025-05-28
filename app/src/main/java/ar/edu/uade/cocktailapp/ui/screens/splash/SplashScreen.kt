package ar.edu.uade.cocktailapp.ui.screens.splash

import android.window.SplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ar.edu.uade.cocktailapp.ui.screens.Screens
import coil.compose.AsyncImage
import kotlinx.coroutines.delay

@Composable
fun SplashScreen (
    modifier: Modifier = Modifier,
    navController: NavController
)
{
    LaunchedEffect(Unit) {
        // con un delay de dos seg, pasamos de pantalla
        delay(2000) //espera 2 segundos
        navController.navigate(Screens.CocktailList.route) {
            popUpTo("spash") {inclusive = true} //evita volver atras
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1F1B24)), // fondo oscuro temático
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            // Imagen de cóctel
            AsyncImage(
                model = "https://cdn-icons-png.flaticon.com/512/3595/3595455.png",
                contentDescription = "Logo Cóctel",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )


            Spacer(modifier = Modifier.height(24.dp))

            // Título
            Text(
                text = "Cocktail Explorer",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

}