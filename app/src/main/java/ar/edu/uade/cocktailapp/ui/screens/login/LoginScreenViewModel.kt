package ar.edu.uade.cocktailapp.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel: ViewModel() {


    private val _uiEvent = Channel<String>()
    val uiEvent = _uiEvent.receiveAsFlow()


    init {
        checkAuthStatus()
    }


    //DISPARAMOS EL EVENTO

    fun checkAuthStatus() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            viewModelScope.launch {
                _uiEvent.send("loginOk")
            }

        }

    }

}