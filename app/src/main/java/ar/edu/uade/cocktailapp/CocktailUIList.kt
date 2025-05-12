package ar.edu.uade.cocktailapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
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
            cocktail -> CocktailUIItem(cocktail)
        }
    }


}