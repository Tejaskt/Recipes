package com.example.recipes.ui.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipes.domain.model.Recipe
import com.example.recipes.domain.repository.RecipeRepository
import com.example.recipes.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _filters = MutableStateFlow(SearchFilterState())
    val filters: StateFlow<SearchFilterState> = _filters

    private val _rawResults = MutableStateFlow<List<Recipe>>(emptyList())

    val uiState: StateFlow<SearchUiState> =
        combine(_rawResults, _filters) { recipes, filters ->

            val filtered = recipes.filter { recipe ->

                val cuisineMatch =
                    filters.cuisine == "All" ||
                            recipe.cuisine.equals(filters.cuisine, true)

                val difficultyMatch =
                    filters.difficulty == "All" ||
                            recipe.difficulty.equals(filters.difficulty, true)

                cuisineMatch && difficultyMatch
            }

            SearchUiState.Success(filtered)

        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            SearchUiState.Idle
        )

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    fun search() {
        val currentQuery = _query.value
        if (currentQuery.isBlank()) return

        viewModelScope.launch {

            when (val result =
                repository.searchRecipes(currentQuery)) {

                is NetworkResult.Success -> {
                    _rawResults.value = result.data
                }

                else -> {}
            }
        }
    }

    fun updateCuisine(cuisine: String) {
        _filters.value = _filters.value.copy(cuisine = cuisine)
    }

    fun updateDifficulty(difficulty: String) {
        _filters.value = _filters.value.copy(difficulty = difficulty)
    }

    fun clearFilters() {
        _filters.value = SearchFilterState()
    }
}

