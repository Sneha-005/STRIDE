package com.example.stride.utility.navigation.graph

import androidx.compose.foundation.layout.Column
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.stride.presentation.dashboard.fitness.StrengthViewModel
import com.example.stride.presentation.dashboard.homePage.FullBodyWarmUpScreen
import com.example.stride.presentation.dashboard.homePage.HomeScreen
import com.example.stride.presentation.dashboard.homePage.HomeScreenViewModel
import com.example.stride.presentation.dashboard.homePage.StepsIntoActionScreen
import com.example.stride.utility.navigation.FitnessRouteScreen
import com.example.stride.utility.navigation.Graph
import com.example.stride.utility.navigation.HomeRouteScreen
import com.example.stride.utility.navigation.TopBar

fun NavGraphBuilder.homeNavGraph(rootNavController: NavHostController) {
    navigation(
        route = Graph.HomeGraph,
        startDestination = HomeRouteScreen.HomeScreen.route
    ){
        composable(route = HomeRouteScreen.HomeScreen.route) {
            val viewModel: HomeScreenViewModel = hiltViewModel()
            HomeScreen(navController = rootNavController, viewModel = viewModel )
        }
        composable(route = HomeRouteScreen.StepsIntoActionScreen.route) {
            val viewModel: HomeScreenViewModel = hiltViewModel()
            Column {
                TopBar(
                    onBackClick = {
                        rootNavController.popBackStack()
                    },
                    title = "Step into action"
                )
                StepsIntoActionScreen(viewModel = viewModel, navHostController = rootNavController)
            }
        }
        composable(
            route = "${HomeRouteScreen.FullBodyWarmUpScreen.route}/{cardId}",
            arguments = listOf(navArgument("cardId") { type = NavType.IntType })
        ){ backStackEntry ->
            val viewModel: HomeScreenViewModel = hiltViewModel()
            val cardId = backStackEntry.arguments?.getInt("cardId") ?:0
            Column {
                TopBar(
                    onBackClick = {
                        rootNavController.popBackStack()
                    },
                    title = "Full Body Warm Up"
                )
                FullBodyWarmUpScreen(
                    navHostController = rootNavController,
                    viewModel = viewModel,
                    workoutId = cardId.toString()
                )
            }
        }
    }

}