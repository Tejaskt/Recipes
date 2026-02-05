package com.example.recipes.domain.model

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val imageUrl: String,
    val accessToken: String
)
