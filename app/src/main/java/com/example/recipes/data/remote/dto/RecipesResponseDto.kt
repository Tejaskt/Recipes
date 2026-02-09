package com.example.recipes.data.remote.dto

data class RecipesResponseDto(
    val recipes : List<RecipeDto>,
    val total : Int,
    val skip : Int,
    val limit : Int
)