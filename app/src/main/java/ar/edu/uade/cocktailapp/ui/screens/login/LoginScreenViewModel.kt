package ar.edu.uade.cocktailapp.ui.screens.login

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel() {

    var loginState by mutableStateOf(LoginScreenState())
        private set

    private val _uiEvent = Channel<String>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus() {
        loginState = loginState.copy(isLoading = true)
        if (FirebaseAuth.getInstance().currentUser != null) {
            viewModelScope.launch {
                loginState = loginState.copy(isLoading = false, isLoggedIn = true)
                _uiEvent.send("loginOk")
            }
        } else {
            loginState = loginState.copy(isLoading = false)
        }
    }

    fun onLoginError(message: String) {
        loginState = loginState.copy(
            isLoading = false,
            errorMessage = message
        )
    }

    fun clearError() {
        loginState = loginState.copy(errorMessage = null)
    }
}
