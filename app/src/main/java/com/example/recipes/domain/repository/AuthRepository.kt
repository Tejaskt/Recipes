package com.example.recipes.domain.repository

import android.content.Intent
import com.example.recipes.domain.model.User
import com.example.recipes.utils.NetworkResult
import com.facebook.AccessToken
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(
        username : String,
        password: String
    ): NetworkResult<Unit>

    fun isLoggedIn(): Flow<Boolean>

    suspend fun logout()

    fun getUser(): Flow<User?>

    // social logins
    suspend fun loginWithGoogle(intent: Intent?): Result<Unit>
    suspend fun loginWithFacebook(token: AccessToken): Result<Unit>

}