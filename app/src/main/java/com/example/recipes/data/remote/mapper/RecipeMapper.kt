package com.example.recipes.data.remote.mapper

import com.example.recipes.data.remote.dto.RecipeDto
import com.example.recipes.domain.model.Recipe

fun RecipeDto.toDomain() : Recipe {
    return Recipe(
        id = id,
        title = name,
        imageUrl = image,
        cuisine = cuisine,
        difficulty = difficulty,
        prepTime = prepTimeMinutes,
        cookTime = cookTimeMinutes,
        servings = servings,
        rating = rating,
        ingredients = ingredients,
        instructions = instructions,
        calories = "$caloriesPerServing cal",
        mealType = mealType,
        time = "${prepTimeMinutes + cookTimeMinutes} min"
    )
}