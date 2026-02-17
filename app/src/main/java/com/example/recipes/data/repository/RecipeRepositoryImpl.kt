package com.example.recipes.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.recipes.data.paging.RecipePagingSource
import com.example.recipes.data.remote.api.RecipeApi
import com.example.recipes.data.remote.mapper.toDomain
import com.example.recipes.domain.model.Recipe
import com.example.recipes.domain.repository.RecipeRepository
import com.example.recipes.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import okio.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepositoryImpl @Inject constructor(
    private val api : RecipeApi
) : RecipeRepository {

    /*--- GET ALL RECIPES ---*/
    override fun getRecipes(category: String): Flow<PagingData<Recipe>> {

        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                RecipePagingSource(api,category)
            }
        ).flow

    }

    override suspend fun getRecipeById(id: Int): NetworkResult<Recipe> {
        return try {
            val response = api.getRecipeById(id)

            if(response.isSuccessful){
                response.body()?.let {
                    NetworkResult.Success(it.toDomain())
                } ?: NetworkResult.Error("Empty response")
            }else{
                NetworkResult.Error("Failed to load recipe")
            }

        }catch (e: IOException){
            NetworkResult.Error("No internet connection")
        }catch (e: Exception){
            NetworkResult.Error("Something went wrong")
        }
    }

    /*--- SEARCH RECIPES ---*/
    override suspend fun searchRecipes(query: String): NetworkResult<List<Recipe>> {
        return try {
            val response = api.searchRecipes(query)

            if(response.isSuccessful){
                val body = response.body()
                NetworkResult.Success(
                    body?.recipes?.map { it.toDomain() } ?: emptyList()
                )
            }else{
                NetworkResult.Error("Search Failed")
            }
        }catch (e : Exception){
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }

    /*--- SORT RECIPES ---*/
    override suspend fun sortRecipes(
        sortBy: String,
        order: String
    ): NetworkResult<List<Recipe>> {
        return try {

            val response = api.getSortedRecipes(sortBy,order)

            if(response.isSuccessful){
                val body = response.body()
                NetworkResult.Success(
                    body?.recipes?.map { it.toDomain() } ?: emptyList()
                )
            }else{
                NetworkResult.Error("Sorting Failed")
            }
        }catch (e : Exception){
            NetworkResult.Error(e.message ?: "Unknown Error")
        }
    }


    /*--- FAVORITE RECIPES ---*/
    private val _favoriteItems = MutableStateFlow<List<Recipe>>(emptyList())

    override val favoriteItems = _favoriteItems.asStateFlow()

    override suspend fun addToFavorite(item : Recipe) {
        if(_favoriteItems.value.none{ it.id == item.id }){
            _favoriteItems.value += item
        }
    }

    override suspend fun removeFromFavorite(recipeId: Int) {
        _favoriteItems.value = _favoriteItems.value.filterNot { it.id == recipeId }
    }

    override fun isFavorite(recipeId: Int): Flow<Boolean> {
        return  _favoriteItems.map { list ->
            list.any{ it.id == recipeId }
        }
    }

}