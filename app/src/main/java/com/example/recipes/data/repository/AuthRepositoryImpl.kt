package com.example.recipes.data.repository

import com.example.recipes.data.remote.api.AuthApi
import com.example.recipes.data.remote.dto.LoginRequestDto
import com.example.recipes.domain.repository.AuthRepository
import com.example.recipes.utils.NetworkResult
import okio.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi
): AuthRepository {

    private var cachedToken: String? = null

    override suspend fun login(
        username: String,
        password: String
    ): NetworkResult<Unit> {
       return try {

           val response = api.login(LoginRequestDto(username, password))

           if (response.isSuccessful){
               val body = response.body()
               if(body!= null){
                   cachedToken = body.accessToken
                   NetworkResult.Success(Unit)
               }else{
                   NetworkResult.Error("Empty Response")
               }
           }else{
               NetworkResult.Error("Invalid credentials")
           }
       }catch (e: IOException){
           NetworkResult.Error("No internet connection : $e")
       }catch (e : Exception){
           NetworkResult.Error("Something went wrong : $e")
       }
    }

    override fun isLoggedIn(): Boolean = cachedToken != null
}