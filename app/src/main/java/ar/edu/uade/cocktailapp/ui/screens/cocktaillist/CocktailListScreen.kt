package ar.edu.uade.cocktailapp.ui.screens.cocktaillist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
            text = "LISTADO COCKTAIL API",
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
        //creamos campo de busqueda

        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically

        ){
            TextField(
                value = vm.uiState.searchQuery,
                modifier = Modifier.weight(1f),
                label = { Text("Busca tu mejor cocktel") },
                singleLine = true,
                onValueChange = {vm.searchChange(it)}
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {vm.fetchCocktail()}
            ) {
                Text("Buscar")
            }
        }



        Spacer(modifier = Modifier.height(12.dp))
        //lista
        CocktailUIList(vm.uiState.cocktailList, Modifier.fillMaxSize()) //LLAMO A LA LISTA DE COCTELES

    }


}


