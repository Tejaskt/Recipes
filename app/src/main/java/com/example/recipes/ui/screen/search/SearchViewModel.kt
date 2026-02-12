package com.example.recipes.ui.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipes.domain.repository.RecipeRepository
import com.example.recipes.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _uiState =
        MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState: StateFlow<SearchUiState> = _uiState

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    fun search() {
        val currentQuery = _query.value
        if (currentQuery.isBlank()) return

        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading

            when (val result = repository.searchRecipes(currentQuery)) {

                is NetworkResult.Success ->
                    _uiState.value =
                        SearchUiState.Success(result.data)

                is NetworkResult.Error ->
                    _uiState.value =
                        SearchUiState.Error(result.message)

                else -> {}
            }
        }
    }

    fun sortByNameAsc() {
        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading

            when (val result =
                repository.sortRecipes("name", "asc")) {

                is NetworkResult.Success ->
                    _uiState.value =
                        SearchUiState.Success(result.data)

                is NetworkResult.Error ->
                    _uiState.value =
                        SearchUiState.Error(result.message)

                else -> {}
            }
        }
    }
}
