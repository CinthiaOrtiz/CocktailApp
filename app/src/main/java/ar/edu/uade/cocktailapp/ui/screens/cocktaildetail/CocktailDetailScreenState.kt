package ar.edu.uade.cocktailapp.ui.screens.cocktaildetail

import ar.edu.uade.cocktailapp.data.Cocktail
import ar.edu.uade.cocktailapp.data.emptyCocktail

data class CocktailDetailScreenState(
    val cocktailId: Int = 0,
    val cocktailDetail: Cocktail = emptyCocktail()) {

}