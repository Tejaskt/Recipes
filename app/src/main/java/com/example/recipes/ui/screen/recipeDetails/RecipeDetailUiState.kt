package com.example.recipes.ui.screen.recipeDetails

import com.example.recipes.domain.model.Recipe

sealed class RecipeDetailUiState {
    object Loading : RecipeDetailUiState()
    data class Success(val recipe: Recipe) : RecipeDetailUiState()
    data class Error(val message: String) : RecipeDetailUiState()
}
