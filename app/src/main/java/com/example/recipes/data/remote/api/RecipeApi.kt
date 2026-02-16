package com.example.recipes.data.remote.api

import com.example.recipes.data.remote.dto.RecipeDto
import com.example.recipes.data.remote.dto.RecipesResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApi {


    /*--- GET ALL RECIPES ---*/
    @GET("recipes")
    suspend fun getRecipes(
        @Query("limit") limit : Int,
        @Query("skip") skip : Int
    ) : Response<RecipesResponseDto>

    @GET("recipes/{id}")
    suspend fun getRecipeById(
        @Path("id") id : Int
    ): Response<RecipeDto>


    /*--- SEARCH ---*/
    @GET("recipes/search")
    suspend fun searchRecipes(
        @Query("q") query : String
    ) : Response<RecipesResponseDto>


    /*--- SORTING ---*/
    @GET("recipes")
    suspend fun getSortedRecipes(
        @Query("sortBy") sortBy : String,
        @Query("order") order : String
    ): Response<RecipesResponseDto>

    @GET("recipes/meal-type/{mealType}")
    suspend fun getRecipeByMealType(
        @Path("mealType") mealType: String,
        @Query("limit") limit : Int,
        @Query("skip") skip : Int
    ): Response<RecipesResponseDto>
}