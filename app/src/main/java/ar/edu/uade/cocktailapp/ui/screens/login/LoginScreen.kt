package ar.edu.uade.cocktailapp.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ar.edu.uade.cocktailapp.ui.screens.Screens
import coil.compose.AsyncImage


@Composable
fun LoginScreen(
    navController: NavHostController,
    onGoogleLoginClick: (onSuccess: () -> Unit) -> Unit, // Ahora recibe una lambda de éxito
    modifier: Modifier = Modifier,
    vm: LoginScreenViewModel = viewModel()
) {

//RECIBO EVENTO Y YA ESTA LOGEADO
    LaunchedEffect(Unit) {
        vm.uiEvent.collect {
            event ->
            navController.navigate(Screens.CocktailList.route) {
                popUpTo(Screens.Login.route) {inclusive = true}
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Texto superior
            Text(
                text = "LOGIN",
                color = Color.White,
                fontSize = 45.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 38.sp,
                modifier = Modifier.padding(top = 32.dp)
            )

            // Imagen
            AsyncImage(
                model = "https://www.azumuta.com/wp-content/uploads/2024/03/integrations-logo-google.png",
                contentDescription = "Cocktail login image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(350.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .padding(horizontal = 16.dp)
            )

            // Frase + botón
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = "Sign in with Google to continue",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        // ✅ Llama a onGoogleLoginClick y navega al home si fue exitoso
                        onGoogleLoginClick {
                            navController.navigate(Screens.CocktailList.route) {
                                popUpTo(Screens.Login.route) { inclusive = true }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Sign in with Google")
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
