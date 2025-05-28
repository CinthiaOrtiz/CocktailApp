package ar.edu.uade.cocktailapp.ui.screens.commons


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.uade.cocktailapp.data.Cocktail
import coil.compose.AsyncImage
@Composable
fun CocktailUIItem(
    cocktail: Cocktail,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit
) {
    Card(
        modifier = modifier
            .clickable{
                onClick(cocktail.idDrink)
            }
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
        ) {
            AsyncImage(
                model = cocktail.strDrinkThumb,
                contentDescription = cocktail.strDrink,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(330.dp) // Aumentada de 180.dp a 260.dp
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            )

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(), // Asegura que la columna ocupe el ancho completo
                horizontalAlignment = Alignment.CenterHorizontally // Centra el contenido
            ) {
                Text(
                    text = cocktail.strDrink,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 22.sp // Aumenta el tamaño de la fuente
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center // Centra el texto
                )
                Spacer(modifier = Modifier.height(8.dp)) // Espacio entre los textos
                Text(
                    text = "ID: ${cocktail.idDrink}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 16.sp // Aumenta el tamaño de la fuente
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center // Centra el texto
                )
            }
        }
    }
}
