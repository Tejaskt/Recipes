package com.example.recipes.ui.screen.recipes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RatingChip(rating: Double) {
    Row(
        verticalAlignment = Alignment.CenterVertically,

        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(14.dp)
        )

        Spacer(Modifier.width(4.dp))

        Text(
            text = rating.toString(),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.labelSmall
        )
    }
}
