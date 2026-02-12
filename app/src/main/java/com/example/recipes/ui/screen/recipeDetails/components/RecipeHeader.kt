package com.example.recipes.ui.screen.recipeDetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.recipes.domain.model.Recipe

@Composable
fun RecipeHeader(
    recipe: Recipe,
    onBackClick: () -> Unit,
    collapseFraction : Float
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
            modifier = Modifier.fillMaxSize()
                .background(
                    Color.Black.copy(alpha = 0.3f * collapseFraction)
                )
        )

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

    }
}