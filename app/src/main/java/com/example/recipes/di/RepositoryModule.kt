package com.example.recipes.di

import com.example.recipes.data.remote.api.AuthApi
import com.example.recipes.data.remote.api.RecipeApi
import com.example.recipes.data.repository.AuthRepositoryImpl
import com.example.recipes.data.repository.RecipeRepositoryImpl
import com.example.recipes.domain.repository.AuthRepository
import com.example.recipes.domain.repository.RecipeRepository
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

    @Provides
    @Singleton
    fun provideRecipeRepository(
        api: RecipeApi
    ) : RecipeRepository = RecipeRepositoryImpl(api)
}