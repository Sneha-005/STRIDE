package com.example.stride.presentation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.stride.utility.navigation.BottomNavigationBar
import com.example.stride.utility.navigation.MainRouteScreen
import com.example.stride.utility.navigation.bottomNavigationItemsList
import com.example.stride.utility.navigation.graph.MainNavGraph

@Composable
fun MainScreen(
    rootNavController: NavHostController,
    homeNavController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
    val currentRoute by remember(navBackStackEntry) {
        derivedStateOf { navBackStackEntry?.destination?.route }
    }

    val bottomNavRoutes = listOf(
        MainRouteScreen.HomeScreen.route,
        MainRouteScreen.ProfileScreen.route,
        MainRouteScreen.CategoryScreen.route
    )

    val isBottomBarVisible by remember { derivedStateOf { currentRoute in bottomNavRoutes } }

    println("Current Route: $currentRoute, BottomNav Visible: $isBottomBarVisible")

    Scaffold(
        bottomBar = {
            if (isBottomBarVisible) {
                BottomNavigationBar(
                    items = bottomNavigationItemsList,
                    currentRoute = currentRoute
                ) { currentNavigationItem ->
                    homeNavController.navigate(currentNavigationItem.route) {
                        popUpTo(homeNavController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }
    ) { innerPadding ->
        MainNavGraph(
            rootNavController = rootNavController,
            homeNavController = homeNavController,
            innerPadding
        )
    }
}
