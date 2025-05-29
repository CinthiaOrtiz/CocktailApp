package ar.edu.uade.cocktailapp.ui.screens.cocktaildetail

import androidx.compose.foundation.background
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
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = cocktail.strDrinkThumb,
            contentDescription = cocktail.strDrink,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(330.dp)
                .clip(RoundedCornerShape(16.dp))
                .padding(15.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Nombre del cóctel
        Text(
            text = cocktail.strDrink,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Categoría
        Text(
            text = cocktail.strCategory,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Ingredientes y medidas
        Text(
            text = "Ingredients",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        ingredients.forEachIndexed { index, ingredient ->
            val measure = measures.getOrNull(index)?.trim() ?: ""
            Text(
                text = "- $ingredient ${if (measure.isNotBlank()) "($measure)" else ""}",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Instrucciones
        Text(
            text = "Preparation",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = cocktail.strInstructions,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
