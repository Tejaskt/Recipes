package com.example.recipes.domain.repository

import com.example.recipes.domain.model.User
import com.example.recipes.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(
        username : String,
        password: String
    ): NetworkResult<Unit>

    fun isLoggedIn(): Flow<Boolean>

    suspend fun logout()

    fun getUser(): Flow<User?>
}