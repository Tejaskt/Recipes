package com.example.recipes.di

import com.example.recipes.data.remote.api.AuthApi
import com.example.recipes.data.remote.api.RecipeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/*
Annotation	Meaning
@Module	 => Container for manual dependency recipes
@Provides => How to build a dependency
@InstallIn => Where (which lifecycle) it belongs
@Singleton => How long to keep it (cache rule)
*
* */
@Module
@InstallIn(SingletonComponent::class)
/* Component

*  SingletonComponent -> app lifetime
*           |
* ActivityComponent -> per Activity
*           |
* ViewModelComponent -> per ViewModel

* */

object NetworkModule {

    private const val BASE_URL = "https://dummyjson.com/"

    // PROVIDES HTTP CLIENT INSTANCE
    @Provides
    @Singleton
    /*
    * @SingletonComponent -> if Singleton use with this than one instance for whole app
    * @ActivityComponent -> used with this than one instance per activity.
    *
    * */
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()

    // PROVIDES RETROFIT INSTANCE
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    // PROVIDES LOGIN API INSTANCE
    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    // PROVIDES RECIPE API INSTANCE
    @Provides
    @Singleton
    fun provideRecipeApi(retrofit: Retrofit) : RecipeApi =
        retrofit.create(RecipeApi::class.java)

}
