package com.example.recipes.ui.screen.recipes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.recipes.domain.model.Recipe
import com.example.recipes.ui.screen.recipes.components.BottomNavBar
import com.example.recipes.ui.screen.recipes.components.CategoryChips
import com.example.recipes.ui.screen.recipes.components.FeaturedRecipeCard
import com.example.recipes.ui.screen.recipes.components.GreetingSection
import com.example.recipes.ui.screen.recipes.components.RatingChip
import com.example.recipes.ui.screen.recipes.components.RecipeCard
import com.example.recipes.ui.screen.recipes.components.Searchbar
import com.example.recipes.ui.screen.recipes.components.SectionHeader

@Preview
@Composable
fun RecipeListScreen (
    viewModel: RecipeViewModel = hiltViewModel()
){
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { BottomNavBar() },
        contentWindowInsets = WindowInsets.safeDrawing
    ) { padding ->
        when (state) {
            RecipeUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is RecipeUiState.Error -> {
                val message = (state as RecipeUiState.Error).message
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Error : $message")
                }
            }

            is RecipeUiState.Success -> {
                val recipes = (state as RecipeUiState.Success).recipes
                RecipeListContent(
                    recipes = recipes,
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}

@Composable
fun RecipeListContent(
    recipes : List<Recipe>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 16.dp),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        item{
            GreetingSection()
            Spacer(Modifier.height(16.dp))
            //Searchbar()
            //Spacer(Modifier.height(16.dp))
            CategoryChips()
            Spacer(Modifier.height(20.dp))
            FeaturedRecipeCard(recipes.firstOrNull())
            Spacer(Modifier.height(24.dp))
            SectionHeader(
                title = "Popular Recipes",
                actionText = "See All"
            )
            Spacer(Modifier.height(12.dp))
        }

        items(recipes){ recipe ->
            RecipeCard(recipe)
            Spacer(Modifier.height(16.dp))
        }
    }
}





