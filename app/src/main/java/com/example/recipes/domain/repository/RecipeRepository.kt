package com.example.recipes.domain.repository

import com.example.recipes.domain.model.Recipe
import com.example.recipes.utils.NetworkResult

interface RecipeRepository {
    suspend fun getRecipes(): NetworkResult<List<Recipe>>
}