package com.example.recipes.data.repository

import com.example.recipes.data.remote.api.RecipeApi
import com.example.recipes.data.remote.mapper.toDomain
import com.example.recipes.domain.model.Recipe
import com.example.recipes.domain.repository.RecipeRepository
import com.example.recipes.utils.NetworkResult
import java.io.IOException
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val api : RecipeApi
) : RecipeRepository {

    override suspend fun getRecipes(): NetworkResult<List<Recipe>> {

        return try {

            val response = api.getRecipes()

            if (response.isSuccessful){
                val body = response.body()

                if(body != null){
                    NetworkResult.Success(
                        body.recipes.map {
                            it.toDomain()
                        }
                    )
                }else{
                    NetworkResult.Error("No data available")
                }

            }else{
                NetworkResult.Error("Failed to load recipes")
            }

        } catch (e : IOException) {
            NetworkResult.Error("No internet connection")
        } catch (e : Exception){
            NetworkResult.Error("Something went wrong!!")
        }
    }
}