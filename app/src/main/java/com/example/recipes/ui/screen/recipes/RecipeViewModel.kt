package com.example.recipes.ui.screen.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipes.domain.model.Recipe
import com.example.recipes.domain.repository.RecipeRepository
import com.example.recipes.ui.screen.recipes.components.RecipeFilterState
import com.example.recipes.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {


    /*----- UI STATE -----*/
    private val _uiState = MutableStateFlow<RecipeUiState>(RecipeUiState.Loading)
    val uiState : StateFlow<RecipeUiState> = _uiState


    /*----- FILTER STATE -----*/
    private val _filterState = MutableStateFlow(RecipeFilterState())
    val filterState : StateFlow<RecipeFilterState> = _filterState

    private var allRecipes : List<Recipe> = emptyList()

    init {
        loadRecipes()
    }

    // Function to load the recipes.
    private fun loadRecipes(){
        viewModelScope.launch {
           // _uiState.value = RecipeUiState.Loading // check this later. for behavior on load effect.
            when(val result = repository.getRecipes()){
                is NetworkResult.Loading ->
                    _uiState.value = RecipeUiState.Loading

                is NetworkResult.Success ->{
//                    _uiState.value = RecipeUiState.Success(result.data)
                    allRecipes = result.data
                    applyFilter()
                }

                is NetworkResult.Error ->
                    _uiState.value = RecipeUiState.Error(result.message)
            }
        }
    }

    fun onCategorySelected(category: String){
        _filterState.value = _filterState.value.copy(selectedCategory = category)
        applyFilter()
    }

    private fun applyFilter(){
        val selected = _filterState.value.selectedCategory

        val filteredRecipes = if(selected == "All"){
            allRecipes
        }else{
            allRecipes.filter {
                it.mealType[0].equals(selected, ignoreCase = true)
            }
        }

        _uiState.value = RecipeUiState.Success(filteredRecipes)
    }
}