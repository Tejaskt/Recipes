package com.example.recipes.ui.screen.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipes.R
import com.example.recipes.ui.screen.recipes.components.RecipeCard
import com.example.recipes.ui.screen.search.components.CustomSearchBar
import com.example.recipes.ui.screen.search.components.FilterContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onRecipeClick: (Int) -> Unit
) {

    val sheetState = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(false) }

    val query by viewModel.query.collectAsState()
    val state by viewModel.uiState.collectAsState()
    val filters by viewModel.filters.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = stringResource(R.string.search_recipes),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(16.dp))

        CustomSearchBar(
            query = query,
            onQueryChange = viewModel::onQueryChange,
            onSearchClick = viewModel::search,
            onFilterClick = { showSheet = true }
        )

        Spacer(Modifier.height(12.dp))

        when (state) {

            SearchUiState.Idle -> {
                Text(stringResource(R.string.search_by))
            }

            SearchUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is SearchUiState.Success -> {

                val recipes =
                    (state as SearchUiState.Success).recipes

                Text(
                    text = "${recipes.size} recipes found",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(Modifier.height(12.dp))

                LazyVerticalGrid(
                    modifier = Modifier.weight(1f),
                    columns = GridCells.Fixed(1),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(recipes) { recipe ->
                        RecipeCard(
                            recipe = recipe,
                            onClick = { onRecipeClick(recipe.id) }
                        )
                    }
                }
            }

            is SearchUiState.Error -> {
                Text(
                    text = (state as SearchUiState.Error).message,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }

    // Bottom Sheet OUTSIDE Column
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState
        ) {
            FilterContent(
                filters = filters,
                onCuisineSelected = viewModel::updateCuisine,
                onDifficultySelected = viewModel::updateDifficulty,
                onClear = viewModel::clearFilters,
                onApply = { showSheet = false }
            )
        }
    }
}