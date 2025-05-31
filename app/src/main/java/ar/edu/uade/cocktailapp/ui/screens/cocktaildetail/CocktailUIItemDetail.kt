package ar.edu.uade.cocktailapp.ui.screens.cocktaildetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.uade.cocktailapp.data.Cocktail
import coil.compose.AsyncImage

@Composable
fun CocktailUIItemDetail(
    cocktail: Cocktail,
    modifier: Modifier = Modifier
) {
    val ingredients = cocktail.getIngredients()
    val measures = cocktail.getMeasures()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Espacio inicial para separar de la barra superior
        Spacer(modifier = Modifier.height(24.dp))

        // Imagen del cóctel
        AsyncImage(
            model = cocktail.strDrinkThumb,
            contentDescription = cocktail.strDrink,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp) // Un poco más grande
                .clip(RoundedCornerShape(20.dp))
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Nombre del cóctel
        Text(
            text = cocktail.strDrink,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Categoría
        Text(
            text = cocktail.strCategory,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Ingredientes
        Text(
            text = "INGREDIENTS",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(8.dp))

        ingredients.forEachIndexed { index, ingredient ->
            val measure = measures.getOrNull(index)?.trim() ?: ""
            Text(
                text = "- $ingredient ${if (measure.isNotBlank()) "($measure)" else ""}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, bottom = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Preparación
        Text(
            text = "PREPARATION",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = cocktail.strInstructions,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.align(Alignment.Start)
        )
    }
}
