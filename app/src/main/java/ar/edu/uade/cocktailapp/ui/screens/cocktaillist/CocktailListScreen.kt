package ar.edu.uade.cocktailapp.ui.screens.cocktaillist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ar.edu.uade.cocktailapp.ui.screens.Screens
import ar.edu.uade.cocktailapp.ui.screens.commons.CocktailUIList

@Composable
fun CocktailListScreen(
    modifier: Modifier = Modifier,
    vm: CocktailListScreenViewModel = viewModel(),
    navController: NavHostController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "CocktailTime",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 45.sp,
                fontWeight = FontWeight.Bold,

            ),
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 48.dp, bottom = 24.dp), // <-- Más aire arriba y espacio abajo
            textAlign = TextAlign.Center
        )


        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            TextField(
                value = vm.uiState.searchQuery,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp)),  // Borde redondeado suave
                label = { Text("Search your cocktail") },
                singleLine = true,
                onValueChange = { vm.searchChange(it) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                shape = RoundedCornerShape(16.dp)
            )


            Spacer(modifier = Modifier.width(4.dp))

            IconButton(
                onClick = { vm.fetchCocktail(vm.uiState.searchQuery) },
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

        }

        Spacer(modifier = Modifier.height(12.dp))


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            // Mostrar lista principal (o resultados de búsqueda)
            if (!vm.uiState.cocktailList.isNullOrEmpty()) {
                CocktailUIList(
                    vm.uiState.cocktailList,
                    Modifier.fillMaxSize(),
                    onClick = { id ->
                        navController.navigate(Screens.CocktailDetail.route + "/${id}")
                    }
                )
            }

            // Mostrar mensaje si la lista está vacía y no está cargando
            if (vm.uiState.cocktailList.isNullOrEmpty() && !vm.uiState.isLoading) {
                Text(
                    text = "No se encontraron resultados",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }

            // Mostrar loader mientras carga
            if (vm.uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

    }
}
