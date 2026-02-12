package com.example.recipes.ui.screen.favorites

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun FavoriteScreen (){
    Text("Favorite Screen", textAlign = TextAlign.Center, modifier = Modifier.fillMaxSize(), style = MaterialTheme.typography.headlineLarge)
}