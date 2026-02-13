package com.example.recipes.ui.screen.recipeDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipes.domain.model.Recipe
import com.example.recipes.domain.repository.RecipeRepository
import com.example.recipes.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: RecipeRepository
) : ViewModel() {

    private val recipeId: Int =
        checkNotNull(savedStateHandle["recipeId"])

    private val _uiState =
        MutableStateFlow<RecipeDetailUiState>(
            RecipeDetailUiState.Loading
        )
    val uiState: StateFlow<RecipeDetailUiState> = _uiState

    init {
        loadRecipe()
    }

    private fun loadRecipe() {
        viewModelScope.launch {
            when (val result =
                repository.getRecipeById(recipeId)
            ) {
                is NetworkResult.Success ->
                    _uiState.value =
                        RecipeDetailUiState.Success(result.data)

                is NetworkResult.Error ->
                    _uiState.value =
                        RecipeDetailUiState.Error(result.message)

                else -> {}
            }
        }
    }

    val isFavorite : StateFlow<Boolean> =
        repository.isFavorite(recipeId)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                false
            )

    fun toggleFavorite(recipe: Recipe){
        viewModelScope.launch {
            if(isFavorite.value){
                repository.removeFromFavorite(recipeId)
            }else{
                repository.addToFavorite(recipe)
            }
        }

    }

}