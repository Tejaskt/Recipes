package com.example.recipes.ui.screen.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.recipes.R
import com.example.recipes.domain.model.User

@Composable
fun ProfileHeader(
    user: User,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                AsyncImage(
                    model = user.image,
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    MaterialTheme.colorScheme.tertiary,
                                    MaterialTheme.colorScheme.secondary
                                )
                            )
                        )
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "${user.firstName} ${user.lastName}",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "@${user.unm}",
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Outlined.Email,null, tint = Color.Gray,modifier = Modifier.size(16.dp))
                Text(
                    user.email,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(16.dp))

            //  Stats Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ProfileStat("12", stringResource(R.string.favorites))
                ProfileStat("5", stringResource(R.string.recipes))
                ProfileStat("28", stringResource(R.string.reviews))
            }
        }
    }
}

@Composable
fun ProfileStat(number: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = number,
            color = Color(0xFFFF7A3D),
            style = MaterialTheme.typography.titleMedium
        )
        Text(label, color = Color.Gray)
    }
}
