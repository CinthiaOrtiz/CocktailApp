package ar.edu.uade.cocktailapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import ar.edu.uade.cocktailapp.ui.screens.NavigationStack
import ar.edu.uade.cocktailapp.ui.screens.Screens
import ar.edu.uade.cocktailapp.ui.theme.CocktailAppTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class MainActivity : ComponentActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        googleSignInClient = GoogleSignIn.getClient(
            this,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        )

        setContent {
            val navController = rememberNavController()
            var userSignedIn by remember { mutableStateOf(false) } // Estado para la sesión

            // Launcher ahora actualiza el estado en lugar de navegar
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult()
            ) { result ->
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener { authResult ->
                            if (authResult.isSuccessful) {
                                userSignedIn = true
                            }
                        }
                } catch (e: ApiException) {
                    Log.e("AUTH", "Error in Google Sign-In", e)
                }
            }

            CocktailAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavigationStack(
                            navController = navController,
                            onGoogleLoginClick = { launcher.launch(googleSignInClient.signInIntent) },
                            onLogoutClick = {
                                FirebaseAuth.getInstance().signOut()
                                navController.navigate(Screens.Login.route) {
                                    popUpTo(Screens.CocktailList.route) { inclusive = true }
                                }
                            },
                            userSignedIn = userSignedIn //  Pasamos el estado a la UI
                        )
                    }
                }
            }
        }
    }
}
