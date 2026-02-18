package com.example.recipes.ui.screen.auth

sealed class LoginEvent {

    // Navigation
    object NavigateToHome : LoginEvent()

    // Show error message
    data class ShowError(val message: String) : LoginEvent()

}
