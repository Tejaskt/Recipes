package com.example.recipes.ui.screen.profile

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.recipes.domain.model.User

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onLogout: () -> Unit
) {

    val user by viewModel.user.collectAsState()
    user ?: return

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    Brush.linearGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        )
                    )
                )
        ) {
            Text(
                text = "My Profile",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.padding(16.dp)
            )

        }

        // Bottom Content
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(top = 180.dp)
                .background(MaterialTheme.colorScheme.background)
        ){

            Spacer(modifier = Modifier.height(100.dp))

            ProfileOptions()

            Spacer(Modifier.weight(1f))

            LogoutButton {
                viewModel.logout()
                onLogout()
            }
        }

        // overlapping
        ProfileHeader(
            user = user!!,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = 16.dp)
                .offset(y = 120.dp)
        )
    }
}

@Composable
fun ProfileHeader(user: User,modifier: Modifier) {

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(24.dp)
    ) {

        Column(Modifier.padding(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                AsyncImage(
                    model = user.image,
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                )

                Spacer(Modifier.width(12.dp))

                Column {
                    Text(
                        text = "${user.firstName} ${user.lastName}",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text("@${user.unm}")
                }
            }

            Spacer(Modifier.height(12.dp))

            Text(user.email)
            Text("Joined recently")
        }
    }
}


@Composable
fun ProfileOptions() {

    val items = listOf(
        "My Favorites",
        "My Recipes",
        "Notifications",
        "Settings",
        "Help & Support"
    )

    items.forEach {
        ListItem(
            headlineContent = { Text(it) },
            trailingContent = {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null)
            }
        )
    }
}

@Composable
fun LogoutButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Text(
            text = "Log Out",
            color = MaterialTheme.colorScheme.error
        )
    }
}
