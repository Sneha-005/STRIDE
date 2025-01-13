package com.example.stride.presentation.auth

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.stride.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(onBackClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = "") },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Row {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                    Spacer(modifier = androidx.compose.ui.Modifier.width(4.dp))
                    Text(text = "Back")
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.background_color)
        )
    )
}
