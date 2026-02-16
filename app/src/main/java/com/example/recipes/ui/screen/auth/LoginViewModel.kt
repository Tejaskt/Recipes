package com.example.recipes.ui.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipes.domain.repository.AuthRepository
import com.example.recipes.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEmailChange(value: String) {
        _uiState.update { it.copy(email = value) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value) }
    }

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
}
