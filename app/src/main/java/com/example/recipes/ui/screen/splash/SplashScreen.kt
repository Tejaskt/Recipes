@file:Suppress("COMPOSE_APPLIER_CALL_MISMATCH")

package com.example.recipes.ui.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipes.ui.theme.RecipesTheme
import com.example.recipes.R

@Composable
fun SplashScreen(
    onNavigate: (Boolean) -> Unit, viewModel: SplashViewModel = hiltViewModel()
) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

    LaunchedEffect(isLoggedIn) {
        isLoggedIn?.let { onNavigate(it) }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.tertiary
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        val iconSize = maxWidth * 0.22f
        val logoSize = maxWidth * 0.7f

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().widthIn(max = 420.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.chef_hat),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(iconSize)
                    .background(
                        Color.White.copy(0.2f),
                        RoundedCornerShape(iconSize / 3)
                    )
                    .padding(iconSize * 0.12f)
            )

            Spacer(Modifier.height(this@BoxWithConstraints.maxHeight * 0.03f))

            Text(
                text = "Recipify",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )

            Spacer(Modifier.height(this@BoxWithConstraints.maxHeight * 0.015f))

            Text(
                text = "Discover Delicious Recipes",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                color = Color.White.copy(alpha = 0.8f)
            )

            Spacer(Modifier.height(this@BoxWithConstraints.maxHeight * 0.06f))

            Image(
                painter = painterResource(R.drawable.splash_logo),
                contentDescription = null,
                modifier = Modifier.size(logoSize)
            )
        }
    }
}

@Preview
@Composable
private fun PrevSplash() {
    RecipesTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.tertiary
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.chef_hat),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(64.dp)
                        .background(
                            Color.White.copy(0.2f),
                            shape = MaterialTheme.shapes.large
                        )
                        .padding(10.dp)
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Recipify",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Discover Delicious Recipes",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = Color.White.copy(alpha = 0.8f)
                )

                Spacer(Modifier.height(16.dp))

                Image(
                    painter = painterResource(R.drawable.splash_logo),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().aspectRatio(1.5f)
                )
            }
        }
    }
}
