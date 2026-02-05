package com.example.recipes.domain.repository

import com.example.recipes.utils.NetworkResult

interface AuthRepository {
    suspend fun login(
        username : String,
        password: String
    ): NetworkResult<Unit>

    fun isLoggedIn(): Boolean
}