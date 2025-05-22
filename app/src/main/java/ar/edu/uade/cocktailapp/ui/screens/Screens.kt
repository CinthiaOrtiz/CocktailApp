package ar.edu.uade.cocktailapp.ui.screens

sealed class Screens(val route: String) {

    object Splash: Screens("splash")
    object CocktailList: Screens("cocktail_list_screen")
    object CocktailDetail : Screens("cocktail_detail_screen")





}