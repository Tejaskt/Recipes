package com.example.recipes.di

import com.example.recipes.data.remote.api.AuthApi
import com.example.recipes.data.repository.AuthRepositoryImpl
import com.example.recipes.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthApi
    ): AuthRepository = AuthRepositoryImpl(api)
}