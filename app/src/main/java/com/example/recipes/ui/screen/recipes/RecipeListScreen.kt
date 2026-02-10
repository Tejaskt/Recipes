package com.example.recipes.ui.screen.recipes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipes.domain.model.Recipe
import com.example.recipes.ui.screen.recipes.components.BottomNavBar
import com.example.recipes.ui.screen.recipes.components.CategoryChips
import com.example.recipes.ui.screen.recipes.components.FeaturedRecipeCard
import com.example.recipes.ui.screen.recipes.components.GreetingSection
import com.example.recipes.ui.screen.recipes.components.RecipeCard
import com.example.recipes.ui.screen.recipes.components.SectionHeader

@Preview
@Composable
fun RecipeListScreen(
    viewModel: RecipeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val filterState by viewModel.filterState.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { BottomNavBar() },
        contentWindowInsets = WindowInsets.safeDrawing
    ) { padding ->

        when (uiState) {
            RecipeUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is RecipeUiState.Error -> {
                val message = (uiState as RecipeUiState.Error).message
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Error : $message")
                }
            }

            is RecipeUiState.Success -> {
                val recipes = (uiState as RecipeUiState.Success).recipes
                RecipeListContent(
                    recipes = recipes,
                    selectedCategory = filterState.selectedCategory,
                    onCategorySelected = viewModel::onCategorySelected,
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}

@Composable
fun RecipeListContent(
    recipes: List<Recipe>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        item {
            GreetingSection()
            Spacer(Modifier.height(16.dp))

            //Searchbar()
            //Spacer(Modifier.height(16.dp))

            CategoryChips(
                selectedCategory = selectedCategory,
                onCategorySelected = onCategorySelected
            )
            Spacer(Modifier.height(20.dp))

            FeaturedRecipeCard(recipes.lastOrNull())

            Spacer(Modifier.height(24.dp))

            SectionHeader(
                title = "Popular Recipes",
                actionText = "See All"
            )
            Spacer(Modifier.height(12.dp))
        }

        items(recipes) { recipe ->
            RecipeCard(recipe)
            Spacer(Modifier.height(16.dp))
        }
    }
}





