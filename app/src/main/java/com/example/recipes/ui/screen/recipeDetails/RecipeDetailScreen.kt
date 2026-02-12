package com.example.recipes.ui.screen.recipeDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipes.ui.screen.recipeDetails.components.IngredientInstructionTabs
import com.example.recipes.ui.screen.recipeDetails.components.NutritionInfo
import com.example.recipes.ui.screen.recipeDetails.components.RecipeHeader
import com.example.recipes.ui.screen.recipeDetails.components.RecipeMeta

@Composable
fun RecipeDetailScreen(
    viewModel: RecipeDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {

    // SCROLL STATE
    val listState = rememberLazyListState()

    // DERIVED COLLAPSE PROGRESS
    val collapseFraction by remember {
        derivedStateOf {
            val scrollOffset = if(listState.firstVisibleItemIndex > 0){
                300f
            } else{
                listState.firstVisibleItemScrollOffset.toFloat()
            }
            (scrollOffset / 300f).coerceIn(0f,1f)
        }
    }

    val state by viewModel.uiState.collectAsState()

    when (state) {
        RecipeDetailUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is RecipeDetailUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = (state as RecipeDetailUiState.Error).message
                )
            }
        }

        is RecipeDetailUiState.Success -> {

            val recipe = (state as RecipeDetailUiState.Success).recipe

            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
            ) {
                item {
                    RecipeHeader(
                        recipe = recipe,
                        onBackClick = onBackClick,
                        collapseFraction = collapseFraction,
                        onFavoriteClick = viewModel::favoriteItemSelect
                    )
                }

                item { RecipeMeta(recipe) }
                item { IngredientInstructionTabs(recipe) }
                item { NutritionInfo(recipe) }
            }
        }
    }
}




