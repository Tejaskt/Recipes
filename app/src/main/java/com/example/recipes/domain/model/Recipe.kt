package com.example.recipes.domain.model


// USE ONLY FOR SHOWING THE API DATA INTO UI THROUGH MAPPER CLASS
data class Recipe (
    val id : Int,
    val title : String,
    val imageUrl : String,
    val cuisine : String,
    val time : String,
    val calories : String,
    val rating : Double,
    val servings : String,
    val mealType: List<String>,
    val difficulty : String,
    val prepTime: Int,
    val cookTime: Int,
    val ingredients: List<String>,
    val instructions: List<String>
)