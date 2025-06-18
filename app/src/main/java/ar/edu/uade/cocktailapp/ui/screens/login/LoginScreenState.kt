package ar.edu.uade.cocktailapp.ui.screens.login

data class LoginScreenState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val errorMessage: String? = null
)
