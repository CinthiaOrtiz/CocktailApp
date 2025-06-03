package ar.edu.uade.cocktailapp.ui.screens.splash


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
        delay(3000) // espera 2 segundos
        navController.navigate(Screens.Welcome.route) {
            popUpTo(Screens.Splash.route) { inclusive = true }
        }
    }

    // Diseño de la SplashScreen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black), // Fondo oscuro para destacar la imagen
        contentAlignment = Alignment.Center
    ) {
        // Imagen principal (logo o cóctel)
        AsyncImage(
            model = "https://media.istockphoto.com/id/1400681592/es/foto/guarnici%C3%B3n-de-lim%C3%B3n-salpicando-en-copa-de-c%C3%B3ctel-artesanal-rosa.jpg?s=612x612&w=0&k=20&c=Gonqg-MyZ1WnvVZXl4wKiPP5Q41cOOhD19-aDUU1B90=",
            contentDescription = "Logo Cóctel",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(450.dp) // Ajuste leve del tamaño
                .clip(RoundedCornerShape(20.dp))
                .padding(horizontal = 16.dp) // o el valor que prefieras

        )

        // Texto en la parte inferior
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp), // Ajuste: subimos un poco el texto
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "CocktailTime",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Discover the best cocktails",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 25.sp
            )
        }
    }
}
