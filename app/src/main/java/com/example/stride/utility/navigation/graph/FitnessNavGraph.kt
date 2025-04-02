package com.example.stride.utility.navigation.graph

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.stride.presentation.dashboard.fitness.CardioScreen
import com.example.stride.presentation.dashboard.fitness.CategoryScreen
import com.example.stride.presentation.dashboard.fitness.Exercise
import com.example.stride.presentation.dashboard.fitness.FullBodyWarmUpScreen
import com.example.stride.presentation.dashboard.fitness.MeditationScreen
import com.example.stride.presentation.dashboard.fitness.PerformAsanaScreen
import com.example.stride.presentation.dashboard.fitness.StrengthTrainingScreen
import com.example.stride.presentation.dashboard.fitness.StrengthViewModel
import com.example.stride.presentation.dashboard.fitness.YogaScreen
import com.example.stride.presentation.dashboard.homePage.HomeScreenViewModel
import com.example.stride.utility.navigation.FitnessRouteScreen
import com.example.stride.utility.navigation.Graph
import com.example.stride.utility.navigation.TopBar
import com.google.gson.Gson

fun NavGraphBuilder.fitnessNavGraph(rootNavController: NavHostController){
    navigation(
        route = Graph.FitnessGraph,
        startDestination = FitnessRouteScreen.CategoryScreen.route
    ){
        composable(route = FitnessRouteScreen.CategoryScreen.route) {
            CategoryScreen(rootNavController)
        }
        composable(route = FitnessRouteScreen.StrengthTrainingScreen.route) {
            val viewModel: StrengthViewModel = hiltViewModel()
            Column {
                TopBar(
                    onBackClick = {
                        rootNavController.popBackStack()
                    },
                    title = "Strength Training"
                )
                StrengthTrainingScreen(viewModel = viewModel, navHostController = rootNavController)
            }
        }
        composable(route = FitnessRouteScreen.CardioScreen.route) {
            val viewModel: StrengthViewModel = hiltViewModel()
            Column {
                TopBar(
                    onBackClick = {
                        rootNavController.popBackStack()
                    },
                    title = "Cardio"
                )
                CardioScreen(viewModel = viewModel, navHostController = rootNavController)
            }
        }
        composable(route = FitnessRouteScreen.YogaScreen.route) {
            Column {
                TopBar(
                    onBackClick = {
                        rootNavController.popBackStack()
                    },
                    title = "Yoga & Meditation"
                )
                YogaScreen()
            }
        }

        composable(
            "full_body_warm_up_screen?exerciseJson={exerciseJson}",
            arguments = listOf(navArgument("exerciseJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val viewModel: StrengthViewModel = hiltViewModel()
            val json = backStackEntry.arguments?.getString("exerciseJson")
            val exercise: Exercise? = json?.let {
                Gson().fromJson(it, Exercise::class.java)
            }

            if (exercise != null) {
                FullBodyWarmUpScreen(rootNavController, viewModel = viewModel, exercise)
            } else {
                // Handle error case
            }
        }

        composable(route = FitnessRouteScreen.MeditationScreen.route) {
            Column {
                TopBar(
                    onBackClick = {
                        rootNavController.popBackStack()
                    },
                    title = "5-Minute Mindfulness"
                )
                MeditationScreen()
            }
        }
        composable(route = FitnessRouteScreen.PerformAsanaScreen.route) {
            Column{
                TopBar(
                    onBackClick = {
                        rootNavController.popBackStack()
                    },
                    title = ""
                )
                PerformAsanaScreen()
            }
        }
    }
}
