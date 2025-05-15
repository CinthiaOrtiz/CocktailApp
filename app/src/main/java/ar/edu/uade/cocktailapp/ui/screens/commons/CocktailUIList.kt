package ar.edu.uade.cocktailapp.ui.screens.commons

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import ar.edu.uade.cocktailapp.data.Cocktail

//list cocteles

@Composable
fun CocktailUIList(
    list: List<Cocktail>,
    modifier: Modifier = Modifier
){
    LazyColumn (
        modifier = modifier
    ) {
        items(
            items = list,
            key = { it -> it.idDrink}
        ) {
            cocktail ->
            CocktailUIItem(cocktail)
        }
    }


}