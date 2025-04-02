package com.example.stride.utility.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.stride.R

@Composable
fun BottomNavigationBar(
    items: List<NavigationItem>,
    currentRoute: String?,
    onClick: (NavigationItem) -> Unit,
) {
    NavigationBar(
        containerColor = colorResource(id = R.color.sheet_color)
    ) {
        items.forEach { navigationItem ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorResource(id = R.color.primary0),
                    selectedTextColor = colorResource(id = R.color.primary0),
                    unselectedIconColor = Color.White,
                    unselectedTextColor = Color.White,
                    indicatorColor = Color.Transparent
                ),
                selected = currentRoute == navigationItem.route,
                onClick = { onClick(navigationItem) },
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (currentRoute == navigationItem.route) {
                            Box(
                                modifier = Modifier
                                    .width(43.dp)
                                    .height(3.dp)
                                    .background(colorResource(id = R.color.primary0))
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                        val icon = if (currentRoute == navigationItem.route) {
                            navigationItem.selectedIcon()
                        } else {
                            navigationItem.unSelectedIcon()
                        }

                        when (icon) {
                            is androidx.compose.ui.graphics.vector.ImageVector -> {
                                Icon(imageVector = icon, contentDescription = navigationItem.title)
                            }
                            is androidx.compose.ui.graphics.painter.Painter -> {
                                Icon(painter = icon, contentDescription = navigationItem.title)
                            }
                        }
                    }
                },
                label = {
                    Text(text = navigationItem.title)
                },
                alwaysShowLabel = true
            )
        }
    }
}
