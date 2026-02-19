package com.example.recipes.ui.screen.auth

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isSocialLoading: Boolean = false,
    var error: String? = null
)