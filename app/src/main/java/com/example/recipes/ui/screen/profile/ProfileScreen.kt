package com.example.recipes.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onLogout: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        ProfileHeader()

        Spacer(Modifier.height(16.dp))

        ProfileOptions()

        Spacer(Modifier.weight(1f))

        LogoutButton {
            viewModel.logout()
            onLogout()
        }
    }
}

@Composable
fun ProfileHeader() {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(Modifier.padding(16.dp)) {

            Text(
                text = "Tejas",
                style = MaterialTheme.typography.headlineMedium
            )

            Text("Food Enthusiast")

            Spacer(Modifier.height(12.dp))

            Text("Tejas@gmail.com")
            Text("Joined February 2026")
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
