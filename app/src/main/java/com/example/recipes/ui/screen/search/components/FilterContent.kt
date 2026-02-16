package com.example.recipes.ui.screen.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.recipes.R
import com.example.recipes.ui.screen.search.SearchFilterState

@Composable
fun FilterContent(
    filters: SearchFilterState,
    onCuisineSelected: (String) -> Unit,
    onDifficultySelected: (String) -> Unit,
    onClear: () -> Unit,
    onApply: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.filters), style = MaterialTheme.typography.titleLarge)

            TextButton(onClick = onClear) {
                Text(stringResource(R.string.clear_all))
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(stringResource(R.string.cuisine))

        Spacer(Modifier.height(8.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            listOf("All","Italian","Asian","American","Indian","Greek","Mexican")
                .forEach { cuisine ->
                FilterChip(
                    selected = filters.cuisine == cuisine,
                    onClick = { onCuisineSelected(cuisine) },
                    label = { Text(cuisine) }
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(stringResource(R.string.difficulty))

        Spacer(Modifier.height(8.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            listOf("All", "Easy", "Medium", "Hard")
                .forEach { difficulty ->

                    FilterChip(
                        selected = filters.difficulty == difficulty,
                        onClick = { onDifficultySelected(difficulty) },
                        label = { Text(difficulty) }
                    )
                }
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = onApply,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.apply_filters))
        }
    }
}