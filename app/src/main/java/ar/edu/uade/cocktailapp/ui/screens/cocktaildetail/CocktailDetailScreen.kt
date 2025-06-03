package com.tuapp.ui.screens.cocktaildetail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ar.edu.uade.cocktailapp.data.Cocktail
import ar.edu.uade.cocktailapp.ui.screens.cocktaildetail.CocktailDetailScreenViewModel
import coil.compose.AsyncImage

// Pantalla principal de detalle del cóctel
@Composable
fun CocktailDetailScreen(
    cocktailId: Int,
    navController: NavHostController, // Pasamos el navController para volver atrás con botón
    modifier: Modifier = Modifier,
    vm: CocktailDetailScreenViewModel = viewModel()
) {
    // Establecemos el ID para que el ViewModel cargue el detalle
    vm.setCocktailId(cocktailId)

    // Mostrar loading si aún no tenemos datos
    if (vm.uiState.cocktailDetail.idDrink.isNullOrBlank()) {
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
            navController = navController
        )
    }
}

// Composable que muestra los detalles del cóctel
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
            .background(Color.Black)
            .verticalScroll(rememberScrollState()) // Permite desplazamiento completo
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Nombre del cóctel
        Text(
            text = cocktail.strDrink ?: "Sin nombre",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

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

//  FOTO DETAIL
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
                AsyncImage(
                    model = cocktail.strDrinkThumb ?: "",
                    contentDescription = cocktail.strDrink ?: "Imagen del cóctel",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(20.dp))
                )
            }
        }

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

        // Botón Back: permite volver tocándolo, pero también funciona el botón físico y el swipe back
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, start = 16.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Button(
                onClick = { navController.popBackStack() }, // Vuelve manualmente a la pantalla anterior
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("BACK")
            }
        }

    }
}

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
