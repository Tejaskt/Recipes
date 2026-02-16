package com.example.recipes.ui.screen.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.recipes.R

@Composable
fun ProfileOptions() {

    Card(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {

        Column {
            ProfileOptionItem(stringResource(R.string.my_favorites), "12")
            ProfileOptionItem(stringResource(R.string.my_recipes), "5")
            ProfileOptionItem(stringResource(R.string.notifications))
            ProfileOptionItem(stringResource(R.string.settings))
            ProfileOptionItem(stringResource(R.string.help_support))
        }
    }
}

@Composable
fun ProfileOptionItem(
    title: String,
    badge: String? = null
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = title,
            modifier = Modifier.weight(1f)
        )

        badge?.let {
            Box(
                modifier = Modifier
                    .background(
                        Color(0xFFFFE1D6),
                        RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = it,
                    color = Color(0xFFFF7A3D),
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Spacer(modifier = Modifier.width(8.dp))
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null
        )
    }

    HorizontalDivider()
}