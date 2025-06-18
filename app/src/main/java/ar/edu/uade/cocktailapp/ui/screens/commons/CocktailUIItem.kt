package ar.edu.uade.cocktailapp.ui.screens.commons


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.uade.cocktailapp.data.Cocktail
import coil.compose.AsyncImage

//item detalle

@Composable
fun CocktailUIItem(
    cocktail: Cocktail,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit
) {
    Card(
        modifier = modifier
            .clickable {
                onClick(cocktail.idDrink?.toIntOrNull() ?: 0)
            }
            .padding(8.dp)
            .fillMaxWidth()
            .height(320.dp), // ⬅️ Altura visual igual a favoritos
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1C)) // ✅ Fondo gris oscuro
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            // Imagen del cóctel (usando AsyncImage como ya tenías)
            AsyncImage(
                model = cocktail.strDrinkThumb ?: "", // ✅ null-safe
                contentDescription = cocktail.strDrink ?: "Imagen del cóctel", // ✅ null-safe
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            )

            // Contenido inferior
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .background(Color(0xFF1C1C1C)) // ✅ mantiene fondo gris
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Nombre del cóctel
                Text(
                    text = cocktail.strDrink ?: "Sin nombre",
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 22.sp),
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                // ID del cóctel
                Text(
                    text = "ID: ${cocktail.idDrink ?: "0"}",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                    color = Color.White.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
