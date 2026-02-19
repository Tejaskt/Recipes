package com.example.recipes.di

import com.example.recipes.data.repository.AuthRepositoryImpl
import com.example.recipes.data.repository.RecipeRepositoryImpl
import com.example.recipes.data.repository.SocialAuthManagerImpl
import com.example.recipes.domain.repository.AuthRepository
import com.example.recipes.domain.repository.RecipeRepository
import com.example.recipes.domain.repository.SocialAuthManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ) : AuthRepository

    @Binds
    @Singleton
    abstract fun bindRecipeRepository(
        impl : RecipeRepositoryImpl
    ) : RecipeRepository

    @Binds
    @Singleton
    abstract fun bindSocialAuthManager(
        impl : SocialAuthManagerImpl
    ) : SocialAuthManager

}

/* Refactor
* Core Difference
* @Provides	                            @Binds
*
* You construct object manually	        Hilt constructs it
* Works without @Inject constructor	    Requires @Inject constructor
* Can contain logic	                    No logic allowed
* Slightly more overhead	            More efficient (compile-time binding)
* Used for complex creation	            Used for simple interface → implementation mapping
*
* */