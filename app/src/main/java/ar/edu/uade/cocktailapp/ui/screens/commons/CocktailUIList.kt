package ar.edu.uade.cocktailapp.ui.screens.commons

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import ar.edu.uade.cocktailapp.data.Cocktail

//list cocteles

@Composable
fun CocktailUIList(
    list: List<Cocktail>?,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = list ?: emptyList(),
            key = { it.idDrink?.toIntOrNull() ?: 0 } // Convertir a Int, usar 0 si es null
        ) { cocktail ->
            CocktailUIItem(
                cocktail,
                onClick = { onClick(cocktail.idDrink?.toIntOrNull() ?: 0) }
            )
        }
    }
}
