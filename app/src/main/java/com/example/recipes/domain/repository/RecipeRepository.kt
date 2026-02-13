package com.example.recipes.domain.repository

import androidx.paging.PagingData
import com.example.recipes.domain.model.Recipe
import com.example.recipes.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface RecipeRepository {
    fun getRecipes(category: String): Flow<PagingData<Recipe>>

    suspend fun getRecipeById(id : Int): NetworkResult<Recipe>

    suspend fun searchRecipes( query : String) : NetworkResult<List<Recipe>>

    suspend fun sortRecipes( sortBy : String, order : String) : NetworkResult<List<Recipe>>


    val favoriteItems: StateFlow<List<Recipe>>
    suspend fun addToFavorite(item : Recipe)

    suspend fun removeFromFavorite(recipeId : Int)

    fun isFavorite(recipeId : Int) : Flow<Boolean>
}