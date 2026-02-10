package com.example.recipes.ui.screen.recipes

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.recipes.domain.model.Recipe
import com.example.recipes.ui.navigation.Routes
import com.example.recipes.ui.screen.recipes.components.BottomNavBar
import com.example.recipes.ui.screen.recipes.components.CategoryChips
import com.example.recipes.ui.screen.recipes.components.ErrorItem
import com.example.recipes.ui.screen.recipes.components.FeaturedRecipeCard
import com.example.recipes.ui.screen.recipes.components.GreetingSection
import com.example.recipes.ui.screen.recipes.components.LoadingItem
import com.example.recipes.ui.screen.recipes.components.RecipeCard
import com.example.recipes.ui.screen.recipes.components.SectionHeader
import kotlin.collections.lastOrNull

@Composable
fun RecipeListScreen(
    viewModel: RecipeViewModel = hiltViewModel(),
    onRecipeItemClick:(Int)->Unit
) {

    val recipes = viewModel.recipes.collectAsLazyPagingItems()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    val featuredRecipe = recipes.itemSnapshotList.items.randomOrNull()


    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { BottomNavBar() },
        contentWindowInsets = WindowInsets.safeDrawing
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            item{
                GreetingSection()
                Spacer(Modifier.height(16.dp))

                CategoryChips(
                    selectedCategory = selectedCategory,
                    onCategorySelected = viewModel::onCategorySelected
                )
                Spacer(Modifier.height(20.dp))

                FeaturedRecipeCard(featuredRecipe)

                Spacer(Modifier.height(24.dp))

                SectionHeader(
                    title = "Popular Recipes",
                    actionText = "See All"
                )
                Spacer(Modifier.height(12.dp))

            }

            items(recipes.itemCount) { index ->
                recipes[index]?.let { recipe ->
                    RecipeCard(recipe = recipe){
                        onRecipeItemClick(recipe.id)
                    }
                    Spacer(Modifier.height(16.dp))
                }
            }

            // Paging states
            when {
                recipes.loadState.refresh is LoadState.Loading -> {
                    item { LoadingItem() }
                }
                recipes.loadState.append is LoadState.Loading -> {
                    item { LoadingItem() }
                }
                recipes.loadState.refresh is LoadState.Error -> {
                    item { ErrorItem("Failed to load recipes") }
                }
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
            //RecipeCard(recipe)
            Spacer(Modifier.height(16.dp))
        }
    }
}





