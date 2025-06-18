package ar.edu.uade.cocktailapp.ui.screens.Welcome

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ar.edu.uade.cocktailapp.ui.screens.Screens
import ar.edu.uade.cocktailapp.ui.screens.cocktaildetail.CocktailDetailScreenViewModel
import coil.compose.AsyncImage
@Composable
fun WelcomeScreen(navController: NavHostController) {

    val vm: CocktailDetailScreenViewModel = viewModel() // ✅ ViewModel obtenido correctamente

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Texto superior
            Text(
                text = "WELCOME",
                color = Color.White,
                fontSize = 60.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp, // Ajuste de espacio entre líneas
                modifier = Modifier.padding(top = 10.dp)
            )

            // Texto
            Text(
                text = "to start this unique experience",
                color = Color.White,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 30.sp // Ajuste de espacio entre líneas
            )

            // Imagen
            AsyncImage(

                model = "https://previews.123rf.com/images/rrraven/rrraven1512/rrraven151200003/49527926-vector-icono-de-c%C3%B3ctel-negro-sobre-fondo-blanco.jpg",
                contentDescription = "Logo Cóctel",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(400.dp) // Ajuste leve del tamaño
                    .clip(RoundedCornerShape(20.dp))
                    .padding(horizontal = 16.dp) // o el valor que prefieras
            )

            // Frase + botón
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = "Are you ready to start this journey?",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Botón sorpresa
                Button(
                    onClick = {
                        vm.fetchRandomCocktail { cocktail ->
                            val id = cocktail.idDrink?.toIntOrNull() ?: return@fetchRandomCocktail
                            navController.navigate("cocktail_detail_screen/$id")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.White
                    ),
                    border = BorderStroke(1.dp, Color.White),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("🎲 Surprise Me!")
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botón START
                Button(
                    onClick = {
                        navController.navigate(Screens.Login.route) {
                            popUpTo(Screens.Welcome.route) { inclusive = true }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("START")
                }
            }
        }
    }
}
