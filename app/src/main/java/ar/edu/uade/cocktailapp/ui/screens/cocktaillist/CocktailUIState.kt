package ar.edu.uade.cocktailapp.ui.screens.cocktaillist

import ar.edu.uade.cocktailapp.data.Cocktail

data class CocktailUIState (
    val searchQuery: String = "",
    val cocktailList: List<Cocktail> = emptyList(),
    val isLoading: Boolean = false
)