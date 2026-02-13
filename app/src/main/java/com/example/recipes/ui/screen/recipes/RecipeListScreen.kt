package com.example.recipes.ui.screen.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.recipes.domain.model.Recipe
import com.example.recipes.ui.screen.recipes.components.CategoryChips
import com.example.recipes.ui.screen.recipes.components.ErrorItem
import com.example.recipes.ui.screen.recipes.components.FeaturedRecipeCard
import com.example.recipes.ui.screen.recipes.components.GreetingSection
import com.example.recipes.ui.screen.recipes.components.LoadingItem
import com.example.recipes.ui.screen.recipes.components.RecipeCard
import com.example.recipes.ui.screen.recipes.components.SectionHeader

@Composable
fun RecipeListScreen(
    viewModel: RecipeViewModel = hiltViewModel(),
    onRecipeItemClick: (Int) -> Unit
) {

    val recipes = viewModel.recipes.collectAsLazyPagingItems()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    val featuredRecipe : Recipe? = recipes.itemSnapshotList.randomOrNull()

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {

            GreetingSection()
            Spacer(Modifier.height(16.dp))

            //Searchbar()
            //Spacer(Modifier.height(16.dp))

            CategoryChips(
                selectedCategory = selectedCategory,
                onCategorySelected = viewModel::onCategorySelected
            )
            Spacer(Modifier.height(20.dp))

        LazyColumn(
            Modifier.fillMaxWidth()
        ) {

            item {
                FeaturedRecipeCard(featuredRecipe)

                Spacer(Modifier.height(24.dp))
            }

            item {

                SectionHeader(
                    title = "Popular Recipes",
                    actionText = "See All"
                )

                Spacer(Modifier.height(12.dp))
            }

            items(
                count = recipes.itemCount,
                key = { index ->
                    recipes[index]?.id ?: index
                }
            ) { index ->

                recipes[index]?.let { recipe ->

                    RecipeCard(
                        recipe = recipe,
                        onClick = { onRecipeItemClick(recipe.id) }
                    )

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



