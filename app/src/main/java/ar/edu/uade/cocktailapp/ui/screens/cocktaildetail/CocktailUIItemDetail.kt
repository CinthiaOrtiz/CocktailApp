package ar.edu.uade.cocktailapp.ui.screens.cocktaildetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.uade.cocktailapp.data.Cocktail
import coil.compose.AsyncImage

@Composable
fun CocktailUIItemDetail(
    cocktail: Cocktail,
    modifier: Modifier = Modifier,
    //onClick: (Int) -> Unit // <-- Pasa la función como parámetro
) {
    Column(
        modifier = modifier
            .fillMaxSize()

            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        AsyncImage(
            model = cocktail.strDrinkThumb,
            contentDescription = cocktail.strDrink,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(330.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = cocktail.strDrink,
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 26.sp
            ),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "ID: ${cocktail.idDrink}",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 18.sp
            ),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

