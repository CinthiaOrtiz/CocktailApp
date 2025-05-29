package ar.edu.uade.cocktailapp.ui.screens.cocktaildetail

import androidx.compose.runtime.Composable
import ar.edu.uade.cocktailapp.data.Cocktail

@Composable

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
