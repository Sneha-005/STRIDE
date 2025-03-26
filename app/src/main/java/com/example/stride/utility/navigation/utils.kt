package com.example.stride.utility.navigation

import androidx.compose.ui.res.painterResource
import com.example.stride.R

val bottomNavigationItemsList = listOf(
    NavigationItem(
        title = "Home",
        route = MainRouteScreen.HomeScreen.route,
        selectedIcon = { painterResource( id = R.drawable.home_nav) },
        unSelectedIcon = { painterResource( id = R.drawable.home_nav) },
    ),
    NavigationItem(
        title = "Fitness",
        route = MainRouteScreen.CategoryScreen.route,
        selectedIcon = { painterResource( id = R.drawable.fitness_nav) },
        unSelectedIcon = { painterResource( id = R.drawable.fitness_nav) },
    ),
    NavigationItem(
        title = "Diet",
        route = MainRouteScreen.EmptyDietScreen.route,
        selectedIcon = { painterResource( id = R.drawable.diet_nav) },
        unSelectedIcon = { painterResource( id = R.drawable.diet_nav) }
    ),
    NavigationItem(
        title = "Profile",
        route = MainRouteScreen.ProfileScreen.route,
        selectedIcon = { painterResource( id = R.drawable.profile_nav) },
        unSelectedIcon = { painterResource( id = R.drawable.profile_nav) }
    ),
)