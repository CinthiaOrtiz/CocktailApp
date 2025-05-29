package ar.edu.uade.cocktailapp.ui.screens.cocktaillist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ar.edu.uade.cocktailapp.ui.screens.Screens
import ar.edu.uade.cocktailapp.ui.screens.commons.CocktailUIList
import ar.edu.uade.cocktailapp.ui.theme.CocktailAppTheme
import kotlinx.coroutines.flow.debounce

@Composable
fun CocktailListScreen(
    modifier: Modifier = Modifier,
    vm: CocktailListScreenViewModel = viewModel(),
    navController: NavHostController
) {
    // Efecto para buscar automáticamente al escribir
    LaunchedEffect(vm.uiState.searchQuery) {
        snapshotFlow { vm.uiState.searchQuery }
            .debounce(500) // Espera 500ms después de dejar de escribir
            .collect { query ->
                vm.fetchCocktail()
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "CocktailTime",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TextField(
                value = vm.uiState.searchQuery,
                modifier = Modifier.weight(1f),
                label = { Text("Search your cocktail") },
                singleLine = true,
                onValueChange = { vm.searchChange(it) }
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = { vm.fetchCocktail() }
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        CocktailUIList(
            vm.uiState.cocktailList,
            Modifier.fillMaxSize(),
            onClick = { id ->
                navController.navigate(Screens.CocktailDetail.route + "/${id}")
            }
        )
    }
}

