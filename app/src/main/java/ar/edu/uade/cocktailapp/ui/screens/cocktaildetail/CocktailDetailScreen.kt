package com.tuapp.ui.screens.cocktaildetail

import android.widget.ImageView
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ar.edu.uade.cocktailapp.data.Cocktail
import ar.edu.uade.cocktailapp.ui.screens.cocktaildetail.CocktailDetailScreenViewModel
import ar.edu.uade.cocktailapp.ui.screens.splash.SplashScreenLoadingDetail
import com.bumptech.glide.Glide

// Funciones de extensión para obtener ingredientes y medidas no nulos
fun Cocktail.getIngredients(): List<String> {
    return listOfNotNull(
        strIngredient1, strIngredient2, strIngredient3, strIngredient4, strIngredient5,
        strIngredient6, strIngredient7, strIngredient8, strIngredient9, strIngredient10,
        strIngredient11, strIngredient12, strIngredient13, strIngredient14, strIngredient15
    ).filter { it.isNotBlank() }
}

fun Cocktail.getMeasures(): List<String> {
    return listOfNotNull(
        strMeasure1, strMeasure2, strMeasure3, strMeasure4, strMeasure5,
        strMeasure6, strMeasure7, strMeasure8, strMeasure9, strMeasure10,
        strMeasure11, strMeasure12, strMeasure13, strMeasure14, strMeasure15
    ).filter { it.isNotBlank() }
}

// Composable del botón de favorito ❤️
@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit

) {
    IconToggleButton(
        checked = isFavorite,
        onCheckedChange = { onToggleFavorite() }
    ) {
        Icon(
            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = if (isFavorite) "Quitar de favoritos" else "Agregar a favoritos",
            tint = if (isFavorite) Color.Red else Color.White,
            modifier = Modifier.size(30.dp)
        )
    }
}


// Pantalla  detalle del cóctel
@Composable
fun CocktailDetailScreen(
    cocktailId: Int,
    navController: NavHostController, // Pasamos el navController para volver atrás con botón
    modifier: Modifier = Modifier,
    vm: CocktailDetailScreenViewModel = viewModel()
) {
    // Establecemos el ID para que el ViewModel cargue el detalle
    LaunchedEffect(cocktailId) {
        vm.setCocktailId(cocktailId)
    }

    // Mostrar loading si aún no tenemos datos
    if (vm.uiState.cocktailDetail.idDrink.isNullOrBlank()) {
        SplashScreenLoadingDetail()
        return

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Card visual mientras se carga
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.size(150.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Loading...", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    } else {
        // Llamada al Composable de detalle
        CocktailUIItemDetail(
            cocktail = vm.uiState.cocktailDetail,
            navController = navController,
            viewModel = vm // ← Esto es clave
        )

    }
}

// Composable que muestra los detalles del cóctel
// CocktailUIItemDetail
@Composable
fun CocktailUIItemDetail(
    cocktail: Cocktail,
    navController: NavHostController,
    viewModel: CocktailDetailScreenViewModel, // ← Agregado
    modifier: Modifier = Modifier
)
 {
    val ingredients = cocktail.getIngredients()
    val measures = cocktail.getMeasures()

    // Estado para el botón de favorito ❤️
     val isFavorite = viewModel.uiState.isFavorite


     Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .verticalScroll(rememberScrollState()) // Permite desplazamiento completo
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Nombre del cóctel
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = cocktail.strDrink ?: "Sin nombre",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )

            // botón favorito ❤️
            FavoriteButton(
                isFavorite = isFavorite,
                onToggleFavorite = {
                    viewModel.toggleFavorite(cocktail)
                }
            )



        }

        Spacer(modifier = Modifier.height(12.dp))

        // Categoría del cóctel
        Text(
            text = cocktail.strCategory ?: "Sin categoría",
            fontSize = 26.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White.copy(alpha = 0.7f),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // FOTO DETAIL con GLIDE
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(330.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                modifier = Modifier.fillMaxSize()
            ) {
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
                        .fillMaxSize()
                        .clip(RoundedCornerShape(20.dp))
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // INGREDIENTS AND MEASURES
        Text(
            text = "INGREDIENTS AND MEASURES",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(8.dp))

        ingredients.forEachIndexed { index, ingredient ->
            val measure = measures.getOrNull(index)?.trim().orEmpty()
            Text(
                text = "- $ingredient ${if (measure.isNotBlank()) "($measure)" else ""}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White.copy(alpha = 0.7f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, bottom = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Instrucciones de preparación
        Text(
            text = "PREPARATION",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = cocktail.strInstructions ?: "No instructions available",
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            color = Color.White.copy(alpha = 0.7f),
            modifier = Modifier.align(Alignment.Start)
        )

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
                Text("BACK")
            }
        }
         /*
         val context = LocalContext.current

         Button(
             onClick = {
                 viewModel.clearRoomDatabase(context)
             },
             colors = ButtonDefaults.buttonColors(
                 containerColor = Color.Red,
                 contentColor = Color.White
             ),
             modifier = Modifier
                 .padding(top = 16.dp)
         ) {
             Text("BORRAR BASE ROOM")
         } */


     }
}
