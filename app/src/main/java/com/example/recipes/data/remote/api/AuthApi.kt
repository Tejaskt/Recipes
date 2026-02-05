package com.example.recipes.data.remote.api

import com.example.recipes.data.remote.dto.LoginRequestDto
import com.example.recipes.data.remote.dto.LoginResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    @Headers("Content-Type: application/json")
    suspend fun login(
        @Body request: LoginRequestDto
    ): Response<LoginResponseDto>
}
