package com.example.recipes.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipes.R
import com.example.recipes.ui.screen.profile.components.LogoutButton
import com.example.recipes.ui.screen.profile.components.ProfileHeader
import com.example.recipes.ui.screen.profile.components.ProfileOptions

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onLogout: () -> Unit
) {

    val user by viewModel.user.collectAsState()
    user ?: return

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        // Orange Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    Brush.linearGradient(
                        listOf(
                            MaterialTheme.colorScheme.tertiary,
                            MaterialTheme.colorScheme.secondary
                        )
                    )
                )
        ) {
            Text(
                text = stringResource(R.string.my_profile),
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(20.dp, top = 32.dp)
            )
        }

        // Main Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 160.dp)
        ) {

            Spacer(modifier = Modifier.weight(1f))

            ProfileOptions()

            Spacer(modifier = Modifier.weight(0.2f))

            LogoutButton {
                viewModel.logout()
                onLogout()
            }
        }

        // Floating Card
        ProfileHeader(
            user = user!!,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = 20.dp)
                .offset(y = 120.dp)
        )
    }
}