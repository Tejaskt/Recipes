package com.example.recipes.ui.screen.recipes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.example.recipes.R
import com.example.recipes.domain.model.Recipe
import com.example.recipes.utils.RecipeCard

@Composable
fun RecipeListScreen(
    viewModel: RecipeViewModel = hiltViewModel(),
    onRecipeItemClick: (Int) -> Unit
) {

    val recipes = viewModel.recipes.collectAsLazyPagingItems()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val greeting by viewModel.greeting.collectAsState()
    val featuredRecipe : Recipe? = recipes.itemSnapshotList.items.getOrNull(2)

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {

        Text(
            text = greeting,
            style = MaterialTheme.typography.headlineMedium
        )
            Spacer(Modifier.height(16.dp))

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
                    title = stringResource(R.string.popular_recipes),
                    actionText = stringResource(R.string.see_all)
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
                    item { ErrorItem(stringResource(R.string.failed_to_load_recipes)) }
                }
            }
        }
    }
}

@Composable
fun CategoryChips(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    val categories = listOf("All", "Breakfast", "Lunch", "Dinner", "Snack", "Dessert")

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            CategoryChip(
                text = category,
                selected = category == selectedCategory,
                onClick = { onCategorySelected(category) }
            )
        }
    }
}

@Composable
fun CategoryChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable{onClick()}
            .background(
                if (selected)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.surface,
                RoundedCornerShape(50)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = if (selected)
                MaterialTheme.colorScheme.onPrimary
            else
                MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Composable
fun ErrorItem(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(message)
    }
}

@Composable
fun FeaturedRecipeCard(recipe: Recipe?) {
    recipe ?: return

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box {
            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, Color.Black.copy(0.6f))
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.featured_recipe),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
                Text(
                    text = "${recipe.cuisine} • ${recipe.time}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Composable
fun LoadingItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun SectionHeader(
    title: String,
    actionText: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = actionText,
            color = MaterialTheme.colorScheme.primary
        )
    }
}


