package ar.edu.uade.cocktailapp.ui.screens.cocktaillist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ar.edu.uade.cocktailapp.R
import ar.edu.uade.cocktailapp.ui.screens.Screens
import ar.edu.uade.cocktailapp.ui.screens.commons.CocktailUIList
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

@Composable
fun CocktailListScreen(
    modifier: Modifier = Modifier,
    vm: CocktailListScreenViewModel = viewModel(),
    navController: NavHostController,
    onLogoutClick: () -> Unit,
) {
    val context = LocalContext.current // Necesario para revocar acceso a Google

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Fondo negro para toda la pantalla
            .padding(16.dp)
    ) {
        // Fila superior con el nombre del usuario, botón Logout y Favoritos
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // MOSTRAMOS EN PANTALLA NOMBRE USUARIO
            Text(
                text = "Hello, ${vm.uiState.userName ?: "Usuario"}", // Nombre del usuario
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )

            Row {
                // BOTÓN FAVORITOS ❤️
                IconButton(onClick = {
                    navController.navigate(Screens.Favorites.route)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Ver favoritos",
                        tint = Color.Red
                    )
                }


                // BOTÓN LOGOUT
                Button(
                    onClick = {
                        val googleSignInClient = GoogleSignIn.getClient(
                            context,
                            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken(context.getString(R.string.default_web_client_id))
                                .requestEmail()
                                .build()
                        )
                        googleSignInClient.signOut().addOnCompleteListener {
                            googleSignInClient.revokeAccess().addOnCompleteListener {
                                FirebaseAuth.getInstance().signOut()
                                onLogoutClick()
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("LOGOUT")
                }
            }
        }

        // Título principal
        Text(
            text = "CocktailTime",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 45.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            ),
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 24.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Buscador
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            TextField(
                value = vm.uiState.searchQuery,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp)),
                label = { Text("Find your cocktail", color = Color.Black.copy(alpha = 0.7f)) },
                singleLine = true,
                onValueChange = { vm.searchChange(it) },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = Color(0xFFF5F5F5),
                    unfocusedContainerColor = Color(0xFFF5F5F5),
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
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
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)

        ) {
            // Mostrar lista principal
            if (!vm.uiState.cocktailList.isNullOrEmpty()) {
                CocktailUIList(
                    vm.uiState.cocktailList,
                    Modifier.fillMaxSize(),
                    onClick = { id ->
                        navController.navigate(Screens.CocktailDetail.route + "/${id}")
                    }
                )
            }

            // Mensaje si no hay resultados
            if (vm.uiState.cocktailList.isNullOrEmpty() && !vm.uiState.isLoading) {
                Text(
                    text = "No se encontraron resultados",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 22.sp,
                        color = Color.White
                    ),
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }

            // Loader mientras carga
            if (vm.uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }
        }
    }
}

