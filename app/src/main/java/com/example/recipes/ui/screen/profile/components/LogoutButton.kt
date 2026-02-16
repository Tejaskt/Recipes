package com.example.recipes.ui.screen.profile.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.recipes.R

@Composable
fun LogoutButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFFE1D6)
        )
    ) {
        Text(
            text = stringResource(R.string.log_out),
            color = Color.Red
        )
    }
}