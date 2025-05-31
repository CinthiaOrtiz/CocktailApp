package ar.edu.uade.cocktailapp.ui.screens.cocktaildetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import androidx.navigation.NavHostController
import ar.edu.uade.cocktailapp.data.Cocktail
import coil.compose.AsyncImage
@Composable
fun CocktailUIItemDetail(
    cocktail: Cocktail,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val ingredients = cocktail.getIngredients()
    val measures = cocktail.getMeasures()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black) // Fondo negro para toda la pantalla
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Espacio inicial para separar de la barra superior
        Spacer(modifier = Modifier.height(24.dp))

        // Nombre del cóctel
        Text(
            text = cocktail.strDrink,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White, // Texto blanco
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Categoría
        Text(
            text = cocktail.strCategory,
            fontSize = 26.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White.copy(alpha = 0.7f), // Texto blanco suavizado
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Imagen del cóctel
        AsyncImage(
            model = cocktail.strDrinkThumb,
            contentDescription = cocktail.strDrink,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth(0.8f) // Más centrado visualmente
                .aspectRatio(1f) // Imagen cuadrada
                .clip(RoundedCornerShape(10.dp))
                .border(2.dp, Color.White, RoundedCornerShape(10.dp)) // Marco blanco
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Ingredientes
        Text(
            text = "INGREDIENTS",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(8.dp))

        ingredients.forEachIndexed { index, ingredient ->
            val measure = measures.getOrNull(index)?.trim() ?: ""
            Text(
                text = "- $ingredient ${if (measure.isNotBlank()) "($measure)" else ""}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White.copy(alpha = 0.7f), // Texto blanco suavizado
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, bottom = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Preparación
        Text(
            text = "PREPARATION",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = cocktail.strInstructions,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            color = Color.White.copy(alpha = 0.7f), // Texto blanco suavizado
            modifier = Modifier.align(Alignment.Start)
        )

        // Al final de tu Column, antes del cierre }
        Spacer(modifier = Modifier.height(24.dp))

// Botón Back
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, start = 16.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Back")
            }
        }

    }
}
