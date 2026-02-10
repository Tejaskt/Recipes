package com.example.recipes.data.remote.api

import com.example.recipes.data.remote.dto.RecipesResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {
    @GET("recipes")
    suspend fun getRecipes(
        @Query("limit") limit : Int,
        @Query("skip") skip : Int
    ) : Response<RecipesResponseDto>
}