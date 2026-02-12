package com.example.recipes.ui.screen.search

import android.os.Message
import com.example.recipes.domain.model.Recipe

sealed class SearchUiState {
    object Idle : SearchUiState()
    object Loading : SearchUiState()
    data class Success(val recipes: List<Recipe>) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
}