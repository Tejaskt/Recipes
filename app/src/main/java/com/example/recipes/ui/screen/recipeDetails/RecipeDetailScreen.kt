package com.example.recipes.ui.screen.recipeDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.SupervisorAccount
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.recipes.domain.model.Recipe
import com.example.recipes.ui.theme.TextTertiary

@Composable
fun RecipeDetailScreen(
    viewModel: RecipeDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
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
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    RecipeHeader(
                        recipe,
                        onBackClick = onBackClick
                    )
                }

                item {
                    RecipeMeta(recipe)
                }

                item {
                    IngredientInstructionTabs(recipe)
                }

                item {
                    NutritionInfo(recipe)
                }
            }
        }
    }
}

@Composable
fun RecipeHeader(recipe: Recipe, onBackClick: () -> Unit) {
    Box {
        AsyncImage(
            model = recipe.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
        )

        IconButton(
            onClick = onBackClick,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                null, tint = Color.Black,
                modifier = Modifier
                    .background(
                        color = Color.White.copy(alpha = 0.7f),
                        shape = MaterialTheme.shapes.extraLarge
                    )
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun RecipeMeta(recipe: Recipe) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {

        Text(
            text = recipe.title,
            style = MaterialTheme.typography.headlineMedium
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = "${recipe.cuisine} Cuisine • ",
                color = TextTertiary,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = recipe.difficulty,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MetaChip(Icons.Outlined.AccessTime, "Prep Time", "${recipe.prepTime} min", modifier = Modifier.weight(1f))
            MetaChip(Icons.Outlined.LocalFireDepartment, "Cook Time", "${recipe.cookTime} min",modifier = Modifier.weight(1f))
            MetaChip(Icons.Outlined.SupervisorAccount, "Servings", recipe.servings,modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun IngredientInstructionTabs(recipe: Recipe) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Column {
        SecondaryTabRow(selectedTabIndex = selectedTab) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Ingredients (${recipe.ingredients.size})") }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Instructions (${recipe.instructions.size})") }
            )
        }

        if (selectedTab == 0) {
            recipe.ingredients.forEach {
                ListItem(
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.Circle,
                            contentDescription = null,
                            modifier = Modifier.size(6.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    headlineContent = {
                        Text(it)
                    }
                )

            }
        } else {
            recipe.instructions.forEachIndexed { index, step ->
                ListItem(
                    leadingContent = {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .background(
                                    MaterialTheme.colorScheme.primary,
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = (index + 1).toString(),
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    },
                    headlineContent = {
                        Text(step)
                    }
                )

            }
        }
    }
}

@Composable
fun NutritionInfo(recipe: Recipe) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFEFF6EE)
        )
    ) {
        Column {
            Text(
                text = "Nutrition Info",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp, start = 16.dp)
            )
            Text(
                text = "${recipe.calories} calories per serving",
                modifier = Modifier.padding(16.dp),
                color = Color(0xFF2E7D32),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun MetaChip(
    imageVector: ImageVector,
    label: String,
    value: String,
    modifier: Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ), modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                tint = TextTertiary
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}





