package com.example.recipes.ui.screen.recipes

import com.example.recipes.domain.model.Recipe

sealed class RecipeUiState {
    object Loading : RecipeUiState()
    data class Success(val recipes : List<Recipe>) : RecipeUiState()
    data class Error(val message : String) : RecipeUiState()
}

