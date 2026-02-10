package com.example.recipes.domain.repository

import androidx.paging.PagingData
import com.example.recipes.domain.model.Recipe
import com.example.recipes.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getRecipes(category: String): Flow<PagingData<Recipe>>

    suspend fun getRecipeById(id : Int): NetworkResult<Recipe>
}