package ar.edu.uade.cocktailapp.ui.screens.cocktaillist

import ar.edu.uade.cocktailapp.data.Cocktail

data class CocktailListScreenState(
    val cocktailList: List<Cocktail> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val userName: String? = null // puede seguir siendo opcional si no siempre se usa
)