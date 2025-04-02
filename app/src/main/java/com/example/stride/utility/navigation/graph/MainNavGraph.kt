package com.example.stride.utility.navigation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.stride.presentation.dashboard.homePage.HomeScreen
import com.example.stride.presentation.dashboard.homePage.HomeScreenViewModel
import com.example.stride.presentation.dashboard.profile.ProfileScreen
import com.example.stride.presentation.dashboard.diet.EmptyDietScreen
import com.example.stride.presentation.dashboard.fitness.CategoryScreen
import com.example.stride.presentation.dashboard.profile.UpdateProfileScreenViewModel
import com.example.stride.utility.navigation.Graph
import com.example.stride.utility.navigation.MainRouteScreen
import com.example.stride.utility.navigation.NavigationViewModel

@Composable
fun MainNavGraph(
    rootNavController: NavHostController,
    homeNavController: NavHostController,
    innerPadding: PaddingValues
) {
    val navViewModel: NavigationViewModel = hiltViewModel()

    NavHost(
        navController = homeNavController,
        route = Graph.MainGraph,
        startDestination = MainRouteScreen.HomeScreen.route
    ) {
        composable(route = MainRouteScreen.HomeScreen.route) {
            navViewModel.setCurrentRoute(MainRouteScreen.HomeScreen.route)
            val viewModel: HomeScreenViewModel = hiltViewModel()
            HomeScreen(viewModel, rootNavController)
        }
        composable(route = MainRouteScreen.ProfileScreen.route) {
            navViewModel.setCurrentRoute(MainRouteScreen.ProfileScreen.route)
            val viewModel: HomeScreenViewModel = hiltViewModel()
            val updateViewModel: UpdateProfileScreenViewModel = hiltViewModel()
            ProfileScreen(rootNavController,viewModel,updateViewModel)
        }
        composable(route = MainRouteScreen.EmptyDietScreen.route) {
            navViewModel.setCurrentRoute(MainRouteScreen.EmptyDietScreen.route)
            EmptyDietScreen()
        }
        composable(route = MainRouteScreen.CategoryScreen.route) {
            navViewModel.setCurrentRoute(MainRouteScreen.CategoryScreen.route)
            CategoryScreen(rootNavController)
        }
    }
}