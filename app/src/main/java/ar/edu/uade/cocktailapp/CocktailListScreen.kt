package ar.edu.uade.cocktailapp

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.uade.cocktailapp.ui.theme.CocktailAppTheme

@Composable
fun CocktailListScreen(cocktailList: List<Cocktail>, modifier: Modifier = Modifier) {
    Text(
        text = "BYE Mundo",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun CocktailListScreenPreview() {
    CocktailAppTheme {
        //CocktailListScreen(List<Cocktail>())
    }
}