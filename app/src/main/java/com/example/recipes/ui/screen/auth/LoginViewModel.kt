package com.example.recipes.ui.screen.auth

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipes.domain.repository.AuthRepository
import com.example.recipes.utils.NetworkResult
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val googleSignInClient : GoogleSignInClient,
) : ViewModel()
{

    // UI STATE
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    // EVENT FLOW
    private val _events = MutableSharedFlow<LoginEvent>(
        replay = 0,
        extraBufferCapacity = 1
    )
    val events = _events.asSharedFlow()


    fun onEmailChange(value: String) {
        _uiState.update { it.copy(email = value) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value) }
    }


    /*--- API LOGIN---*/

    fun login(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            when (authRepository.login(
                _uiState.value.email,
                _uiState.value.password
            )) {
                is NetworkResult.Success<*> -> onSuccess()
                is NetworkResult.Error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "Invalid credentials"
                        )
                    }

                is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true, error = null) }
            }
        }
    }

    /*--- SOCIAL LOGINS ---*/

    fun loginWithGoogle(intent: Intent?) {
        viewModelScope.launch {

            _uiState.update { it.copy(isSocialLoading = true) }

            val result = authRepository.loginWithGoogle(intent)

            _uiState.update { it.copy(isSocialLoading = false) }

            result
                .onSuccess {
                    _events.emit(LoginEvent.NavigateToHome)
                }
                .onFailure {
                    _events.emit(LoginEvent.ShowError(it.message ?: "Google failed"))
                }
        }
    }

    fun loginWithFacebook(token: AccessToken) {
        viewModelScope.launch {

            _uiState.update { it.copy(isSocialLoading = true) }

            val result = authRepository.loginWithFacebook(token)

            _uiState.update { it.copy(isSocialLoading = false) }

            result
                .onSuccess {
                    _events.emit(LoginEvent.NavigateToHome)
                }
                .onFailure {
                    _events.emit(LoginEvent.ShowError(it.message ?: "Facebook failed"))
                }
        }
    }

    // GOOGLE SIGN IN CLIENT
    fun getGoogleSingInClient() = googleSignInClient.signInIntent

    fun onLoginError(message: String) {
        viewModelScope.launch {
            _events.emit(LoginEvent.ShowError(message))
        }
    }
}
