package com.example.recipes.data.repository

import com.example.recipes.data.local.AuthDataStore
import com.example.recipes.data.remote.api.AuthApi
import com.example.recipes.data.remote.dto.LoginRequestDto
import com.example.recipes.domain.repository.AuthRepository
import com.example.recipes.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okio.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val authDataStore: AuthDataStore
): AuthRepository {

    override suspend fun login(
        username: String,
        password: String
    ): NetworkResult<Unit> {
       return try {

           val response = api.login(LoginRequestDto(username, password))

           if (response.isSuccessful){
               response.body()?.let{
                   authDataStore.saveToken(it.accessToken)
                   NetworkResult.Success(Unit)
               } ?: NetworkResult.Error("Empty response")
           }else{
               NetworkResult.Error("Invalid credentials")
           }
       }catch (e: IOException){
           NetworkResult.Error("No internet connection : $e")
       }catch (e : Exception){
           NetworkResult.Error("Something went wrong : $e")
       }
    }

    override fun isLoggedIn(): Flow<Boolean> = authDataStore.accessToken.map { token ->
        !token.isNullOrEmpty()
    }

    override suspend fun logout() {
        authDataStore.clearToken()
    }
}