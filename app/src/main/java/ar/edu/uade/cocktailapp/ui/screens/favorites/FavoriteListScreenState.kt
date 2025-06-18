package ar.edu.uade.cocktailapp.ui.screens.favorites

import ar.edu.uade.cocktailapp.data.Cocktail

data class FavoriteListScreenState(
    val isLoading: Boolean = true,
    val favorites: List<Cocktail> = emptyList()
)
