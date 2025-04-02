package com.example.stride.utility.navigation.graph

import androidx.compose.foundation.layout.Column
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.stride.presentation.dashboard.diet.DietRecordScreen
import com.example.stride.presentation.dashboard.diet.EmptyDietScreen
import com.example.stride.utility.navigation.DietRouteScreen
import com.example.stride.utility.navigation.Graph
import com.example.stride.presentation.dashboard.diet.LogFoodScreen
import com.example.stride.presentation.dashboard.diet.RecipeDetailScreen
import com.example.stride.presentation.dashboard.diet.RecipeScreen
import com.example.stride.utility.navigation.TopBar

fun NavGraphBuilder.dietNavGraph(rootNavController: NavHostController){
    navigation(
        route = Graph.DietGraph,
        startDestination = DietRouteScreen.EmptyDietScreen.route

    ){
        composable(route = DietRouteScreen.EmptyDietScreen.route) {
            EmptyDietScreen()
        }

        composable(route = DietRouteScreen.DailyGoal.route){
            DietRecordScreen()
        }

        composable(route = DietRouteScreen.LogFoodScreen.route) {
            Column {
                TopBar(
                    onBackClick = {
                        rootNavController.popBackStack()
                    },
                    title = "Log Food"
                )
                LogFoodScreen()
            }
        }

        composable(route = DietRouteScreen.DietRecordScreen.route){
            DietRecordScreen()
        }

        composable(route = DietRouteScreen.RecipeScreen.route){
            TopBar(
                onBackClick = {
                    rootNavController.popBackStack()
                },
                title = "Recipes"
            )
            RecipeScreen()
        }
        composable(route = DietRouteScreen.RecipeDetailScreen.route){
            Column{
                TopBar(
                    onBackClick = {
                        rootNavController.popBackStack()
                    },
                    title = ""
                )
                RecipeDetailScreen()
            }
        }
    }

}