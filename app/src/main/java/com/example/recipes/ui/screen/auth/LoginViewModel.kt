package com.example.recipes.ui.screen.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipes.domain.model.FacebookUser
import com.example.recipes.domain.repository.AuthRepository
import com.example.recipes.utils.NetworkResult
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
    private val authRepository: AuthRepository
) : ViewModel()
{

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    // event flow
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


    /*--- FACEBOOK LOGIN---*/
    private val _authState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val authState: StateFlow<AuthUiState> = _authState

    fun onFacebookError(message: String) {
        _authState.value = AuthUiState.Error(message)
        viewModelScope.launch {
            _events.emit(LoginEvent.ShowError(message))
        }
    }

    fun onFacebookUserFetched(
        name: String,
        email: String,
    ) {
        _authState.value = AuthUiState.Success(FacebookUser(name, email))

        viewModelScope.launch {
            _events.emit(LoginEvent.NavigateToHome)
        }
    }

    /*--- GOOGLE LOGIN---*/

    fun onGoogleLoginSuccess(name : String,email : String) {
        viewModelScope.launch {
            Log.d("GOOGLE_USER", "Name: $name")

            Log.d("GOOGLE_USER", "Email: $email")
            _events.emit(LoginEvent.NavigateToHome)
        }
    }

    fun onGoogleLoginError(message: String) {
        viewModelScope.launch {
            _events.emit(LoginEvent.ShowError(message))
        }
    }



}
