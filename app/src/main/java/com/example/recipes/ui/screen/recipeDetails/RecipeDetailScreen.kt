package com.example.recipes.ui.screen.recipeDetails

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.FavoriteBorder
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.recipes.R
import com.example.recipes.domain.model.Recipe

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
            val isFavorite by viewModel.isFavorite.collectAsState()

            LazyColumn(
                state = listState,
                modifier = Modifier.wrapContentSize().background(MaterialTheme.colorScheme.background)
                    .windowInsetsPadding(WindowInsets.displayCutout),
            ) {
                item {
                    RecipeHeader(
                        recipe = recipe,
                        onBackClick = onBackClick,
                        collapseFraction = collapseFraction,
                        onToggleFavorite = { viewModel.toggleFavorite(recipe) },
                        isFavorite = isFavorite
                    )
                }

                item { RecipeMeta(recipe) }
                item { IngredientInstructionTabs(recipe) }
                item { NutritionInfo(recipe) }
            }
        }
    }
}

@Composable
fun IngredientInstructionTabs(recipe: Recipe) {

    var selectedTab by remember { mutableIntStateOf(0) }

    Column (
        modifier = Modifier.padding(horizontal = 8.dp)
    ){
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

        AnimatedContent(
            targetState = selectedTab,
            transitionSpec = {
                fadeIn(tween(500)) togetherWith
                        fadeOut(tween(350))
            },
            label = "tab_switch_animation"
        ) { tab ->

            if (tab == 0) {

                Column {
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
                            headlineContent = { Text(it) }
                        )
                    }
                }

            } else {

                Column {
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
                            headlineContent = { Text(step) }
                        )
                    }
                }
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
                text = stringResource(R.string.nutrition_info),
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
fun RecipeHeader(
    recipe: Recipe,
    onBackClick: () -> Unit,
    collapseFraction : Float,
    isFavorite : Boolean,
    onToggleFavorite: () -> Unit
) {
    val baseHeight = 280.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(baseHeight)
            .graphicsLayer{
                val scale = 1f - (collapseFraction * 0.2f)
                scaleY = scale
            }
    ) {
        AsyncImage(
            model = recipe.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // dark overlay for text readability
        Box(
            modifier = Modifier.matchParentSize()
                .background(
                    Color.Black.copy(alpha = 0.3f * collapseFraction)
                )
        )

        // Back Button
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.padding(8.dp, top = 16.dp)
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                null,
                tint = Color.Black,
                modifier = Modifier
                    .background(
                        color = Color.White.copy(alpha = 0.9f),
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(4.dp)
            )
        }

        // Favorite Button
        IconButton(
            onClick = onToggleFavorite ,
            modifier = Modifier.align(alignment = Alignment.TopEnd).padding(end = 8.dp,top = 16.dp),
        ) {
            Icon(
                if(isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                null,
                tint = Color.Black,
                modifier = Modifier
                    .background(
                        color = Color.White.copy(alpha = 0.9f),
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(4.dp)
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
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSecondary
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = "${recipe.cuisine} Cuisine • ",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondary
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
fun MetaChip(
    imageVector: ImageVector,
    label: String,
    value: String,
    modifier: Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.fillMaxWidth()
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


