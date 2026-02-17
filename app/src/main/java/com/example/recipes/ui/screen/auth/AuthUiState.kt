package com.example.recipes.ui.screen.auth

import com.example.recipes.domain.model.FacebookUser

sealed class AuthUiState {
    object Idle : AuthUiState()
    data class Success(val user: FacebookUser) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}

