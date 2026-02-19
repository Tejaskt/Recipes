package com.example.recipes.data.repository

import android.content.Intent
import com.example.recipes.data.local.AuthDataStore
import com.example.recipes.data.remote.api.AuthApi
import com.example.recipes.data.remote.dto.LoginRequestDto
import com.example.recipes.domain.model.User
import com.example.recipes.domain.repository.AuthRepository
import com.example.recipes.domain.repository.SocialAuthManager
import com.example.recipes.utils.NetworkResult
import com.facebook.AccessToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okio.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val authDataStore: AuthDataStore,
    private val socialAuthManager: SocialAuthManager
) : AuthRepository {

    /*--- LOGIN ---*/
    override suspend fun login(
        username: String, password: String
    ): NetworkResult<Unit> {
        return try {

            val response = api.login(LoginRequestDto(username, password))

            if (response.isSuccessful) {
                response.body()?.let { dto ->
                    authDataStore.saveUser(
                        userNm = dto.username,
                        firstName = dto.firstName,
                        lastName = dto.lastName,
                        email = dto.email,
                        image = dto.image,
                        token = dto.accessToken
                    )
                    NetworkResult.Success(Unit)
                } ?: NetworkResult.Error("Empty response")
            } else {
                NetworkResult.Error("Invalid credentials")
            }

        } catch (e: IOException) {
            NetworkResult.Error("No internet connection : $e")
        } catch (e: Exception) {
            NetworkResult.Error("Something went wrong : $e")
        }
    }

    override fun isLoggedIn(): Flow<Boolean> = authDataStore.accessToken.map { token ->
        !token.isNullOrEmpty()
    }

    override suspend fun logout() {
        authDataStore.clearToken()
        socialAuthManager.logout()
    }

    override fun getUser(): Flow<User?> = authDataStore.user

    /*--- SOCIAL LOGINS ---*/

    override suspend fun loginWithGoogle(intent: Intent?): Result<Unit> {

        val result = socialAuthManager.loginWithGoogle(intent)

        return result.map { user ->
            authDataStore.saveUser(
                userNm = user.unm,
                firstName = user.firstName,
                lastName = user.lastName,
                email = user.email,
                image = user.image,
                token = "SOCIAL_TOKEN"
            )
        }
    }

    override suspend fun loginWithFacebook(token: AccessToken): Result<Unit> {
        val result = socialAuthManager.loginWithFacebook(token)

        return result.map { user ->
            authDataStore.saveUser(
                userNm = user.unm,
                firstName = user.firstName,
                lastName = user.lastName,
                email = user.email,
                image = user.image,
                token = "SOCIAL_TOKEN"
            )
        }
    }
}