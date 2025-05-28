package ar.edu.uade.cocktailapp.data


data class CocktailResult(
    val drinks: List<Cocktail>
)

data class CocktailDetailResult (
    val drinks : Cocktail
)

data class Cocktail (
    val strDrink: String,
    val strDrinkThumb: String,
    val idDrink: Int

)

fun emptyCocktail() : Cocktail {
    return Cocktail("", "", 0 )
}

