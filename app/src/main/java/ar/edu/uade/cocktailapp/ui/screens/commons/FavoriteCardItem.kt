package ar.edu.uade.cocktailapp.ui.commons

import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import ar.edu.uade.cocktailapp.data.Cocktail
import com.bumptech.glide.Glide
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon


@Composable
fun FavoriteCardItem(
    cocktail: Cocktail,
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    var isFavorite by remember { mutableStateOf(true) } // Empieza como favorito

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1C)) // Fondo gris oscuro
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            // Imagen del cóctel
            AndroidView(
                factory = { context ->
                    ImageView(context).apply {
                        Glide.with(context)
                            .load(cocktail.strDrinkThumb ?: "")
                            .centerCrop()
                            .into(this)
                        scaleType = ImageView.ScaleType.CENTER_CROP
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            )

            // Botón de corazón (esquina superior derecha)
            IconButton(
                onClick = {
                    onToggleFavorite()
                    isFavorite = false // Oculta visualmente el favorito
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Quitar de favoritos",
                    tint = if (isFavorite) Color.Red else Color.White
                )

            }

            // Texto abajo con nombre y categoría
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .background(Color(0xFF1C1C1C))
                    .padding(12.dp)
            ) {
                Text(
                    text = cocktail.strDrink ?: "Sin nombre",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = cocktail.strCategory ?: "Sin categoría",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        }
    }
}

