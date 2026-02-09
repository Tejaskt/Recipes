package com.example.recipes.data.remote.api

import com.example.recipes.data.remote.dto.RecipesResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface RecipeApi {
    @GET("recipes")
    suspend fun getRecipes() : Response<RecipesResponseDto>
}