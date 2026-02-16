package com.example.recipes.ui.screen.recipes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.recipes.domain.model.Recipe
import com.example.recipes.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)

@HiltViewModel
class RecipeViewModel @Inject constructor(
    repository: RecipeRepository
) : ViewModel() {

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory : StateFlow<String> = _selectedCategory

    val recipes = selectedCategory.flatMapLatest { category ->
        repository.getRecipes(category)
    }
        .cachedIn(viewModelScope)

    fun onCategorySelected(category: String){
        _selectedCategory.value = category
    }

    /*--- GREETING ---*/
    private val _greeting = MutableStateFlow(getGreeting())
    val greeting: StateFlow<String> = _greeting


    // Helper function to calculate greeting based on current hour
    private fun getGreeting(): String {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when (hour) {
            in 5..11 -> "Good Morning 👋"
            in 12..16 -> "Good Afternoon 👋"
            in 17..20 -> "Good Evening 👋"
            else -> "Good Night 👋"
        }
    }

}
