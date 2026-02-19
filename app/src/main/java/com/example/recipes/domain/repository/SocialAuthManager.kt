package com.example.recipes.domain.repository

import android.content.Intent
import com.example.recipes.domain.model.User
import com.facebook.AccessToken

interface SocialAuthManager {
    suspend fun loginWithGoogle(intent: Intent?): Result<User>
    suspend fun loginWithFacebook(token: AccessToken): Result<User>
    suspend fun logout()
}
