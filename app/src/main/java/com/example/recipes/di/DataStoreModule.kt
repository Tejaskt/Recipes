package com.example.recipes.di

import android.content.Context
import com.example.recipes.data.local.AuthDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {


    // PROVIDES DATA STORE INSTANCE
    @Provides
    @Singleton
    fun provideAuthDataStore(
        @ApplicationContext context: Context
    ): AuthDataStore {
        return AuthDataStore(context)
    }
}
