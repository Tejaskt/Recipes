package com.example.recipes.domain.repository

import androidx.paging.PagingData
import com.example.recipes.domain.model.Recipe
import com.example.recipes.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/*  why there is two repository files...?
*  In MVVM with Clean Architecture, we create a repository interface in the Domain layer
*  and its implementation in the Data layer to enforce abstraction and follow the Dependency
*  Inversion Principle. This ensures that high-level business logic does not depend on low-level data sources.
*  It improves testability, scalability, maintainability, and allows easy replacement of data sources without
*  affecting the rest of the application.
* */
interface RecipeRepository {
    fun getRecipes(category: String): Flow<PagingData<Recipe>>

    suspend fun getRecipeById(id : Int): NetworkResult<Recipe>

    suspend fun searchRecipes( query : String) : NetworkResult<List<Recipe>>

    suspend fun sortRecipes( sortBy : String, order : String) : NetworkResult<List<Recipe>>

    /*--- FAVORITE RECIPES ---*/

    val favoriteItems: StateFlow<List<Recipe>>

    suspend fun addToFavorite(item : Recipe)

    suspend fun removeFromFavorite(recipeId : Int)

    fun isFavorite(recipeId : Int) : Flow<Boolean>
}