package ar.edu.uade.cocktailapp.ui.screens.cocktaillist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.edu.uade.cocktailapp.ui.screens.commons.CocktailUIList
import ar.edu.uade.cocktailapp.ui.theme.CocktailAppTheme

@Composable
fun CocktailListScreen(
    // NO RECIBE MAS LA LISTA. RECIBE UN VIEW MODEL
    //cocktailList: List<Cocktail>,
    modifier: Modifier = Modifier,
    vm : CocktailListScreenViewModel = viewModel()

)

{
    Column (
        modifier = Modifier.fillMaxSize().padding(16.dp)
    )

    {
        Text(
            text = "COCKTAIL APP",
            style = MaterialTheme.typography.headlineLarge.copy( // Asegura que sea grande
                fontSize = 30.sp, // Ajusta el tamaño de la fuente
                fontWeight = FontWeight.Bold // Puedes hacer el texto más fuerte
            ),
            modifier = modifier
                .fillMaxWidth() // Asegura que el texto ocupe todo el ancho
                .padding(vertical = 16.dp), // Espaciado arriba y abajo
            textAlign = TextAlign.Center // Centra el texto
        )

        Spacer(modifier = Modifier.height(12.dp))
        //lista
        CocktailUIList(vm.uiState.cocktailList, Modifier.fillMaxSize()) //LLAMO A LA LISTA DE COCTELES

    }

}

@Preview(showBackground = true)
@Composable
fun CocktailListScreenPreview() {
    CocktailAppTheme {
        //CocktailListScreen(List<Cocktail>())
    }
}
