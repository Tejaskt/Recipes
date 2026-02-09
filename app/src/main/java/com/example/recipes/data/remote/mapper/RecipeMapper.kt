package com.example.recipes.data.remote.mapper

import com.example.recipes.data.remote.dto.RecipeDto
import com.example.recipes.domain.model.Recipe

fun RecipeDto.toDomain() : Recipe {
    return Recipe(
        id = id,
        title = name,
        imageUrl = image,
        cuisine = cuisine,
        time = "${prepTimeMinutes + cookTimeMinutes} min",
        calories = "$caloriesPerServing cal",
        rating = rating,
        servings = "$servings servings",
        mealType = mealType,
        difficulty = difficulty
    )
}