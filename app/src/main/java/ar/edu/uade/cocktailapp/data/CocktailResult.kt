package ar.edu.uade.cocktailapp.data


data class CocktailResult(
    val drinks: List<Cocktail>
)

data class Cocktail (
    val strDrink: String,
    val strDrinkThumb: String,
    val idDrink: String

)

