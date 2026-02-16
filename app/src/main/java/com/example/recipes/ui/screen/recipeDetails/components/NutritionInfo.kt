package com.example.recipes.ui.screen.recipeDetails.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.recipes.R
import com.example.recipes.domain.model.Recipe

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