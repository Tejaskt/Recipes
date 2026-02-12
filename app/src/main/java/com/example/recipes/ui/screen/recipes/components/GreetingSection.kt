package com.example.recipes.ui.screen.recipes.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.recipes.ui.theme.TextTertiary

@Composable
fun GreetingSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Good Morning 👋",
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = "Tejas",
                style = MaterialTheme.typography.headlineMedium
            )
        }
        //NotificationIcon()
    }
}
