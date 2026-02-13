package com.example.recipes.ui.screen.recipes.components

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.SupervisorAccount
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.recipes.domain.model.Recipe
import com.example.recipes.ui.theme.TextTertiary

@Composable
fun RecipeCard(
    recipe: Recipe,
    onClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),//.clickable{ onClick(recipe.id) },
        onClick = { onClick(recipe.id)} ,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                AsyncImage(
                    model = recipe.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                )
                Column(
                    modifier = Modifier.padding(12.dp)
                ) {

                    Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(
                            text = recipe.mealType[0],
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = " • ${recipe.cuisine}",
                            style = MaterialTheme.typography.bodySmall,
//                            color = TextTertiary
                        )
                    }

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = recipe.title,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(Modifier.height(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CardLastRow(Icons.Outlined.AccessTime,recipe.time)
                        CardLastRow(Icons.Outlined.SupervisorAccount,"${recipe.servings} Servings")
                        CardLastRow(Icons.Outlined.LocalFireDepartment,recipe.calories)
                    }
                }

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                Text(
                    text = recipe.difficulty,
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.labelSmall,
                    color = when (recipe.difficulty) {
                        "Easy" -> Color.Green.copy(green = 0.7f)
                        "Medium"-> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.primary
                    },
                    modifier = Modifier
                        .background(
                            color = when (recipe.difficulty) {
                                "Easy" -> Color.Green.copy(0.2f)
                                "Medium" -> MaterialTheme.colorScheme.secondary.copy(0.5f)
                                else -> MaterialTheme.colorScheme.secondary.copy(0.5f)
                            },
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(top = 2.dp, bottom = 2.dp, start = 6.dp, end = 6.dp)
                )
            }

            Box(
                modifier = Modifier.padding(8.dp).offset(y = 115.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                RatingChip(recipe.rating)
            }

        }
    }
}

@Composable
fun CardLastRow(icon : ImageVector, text : String){
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
        Icon(imageVector = icon, null, modifier = Modifier.size(size = 20.dp))
        Text(text, style = MaterialTheme.typography.bodySmall)
    }
}