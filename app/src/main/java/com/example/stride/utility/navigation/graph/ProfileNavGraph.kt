package com.example.stride.utility.navigation.graph

import androidx.compose.foundation.layout.Column
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.stride.presentation.dashboard.homePage.HomeScreenViewModel
import com.example.stride.presentation.dashboard.profile.GoalUpdateScreen
import com.example.stride.presentation.dashboard.profile.LevelUpdateScreen
import com.example.stride.presentation.dashboard.profile.ProfileScreen
import com.example.stride.presentation.dashboard.profile.UpdateProfileScreen
import com.example.stride.presentation.dashboard.profile.AchievementsScreen
import com.example.stride.presentation.dashboard.profile.ContactUsScreen
import com.example.stride.presentation.dashboard.profile.PrivacyPolicyScreen
import com.example.stride.presentation.dashboard.profile.ProfileState
import com.example.stride.presentation.dashboard.profile.UpdateProfileScreenViewModel
import com.example.stride.utility.navigation.Graph
import com.example.stride.utility.navigation.ProfileRouteScreen
import com.example.stride.utility.navigation.TopBar

fun NavGraphBuilder.profileNavGraph(rootNavController: NavHostController){
    navigation(
        startDestination = ProfileRouteScreen.ProfileScreen.route,
        route = Graph.ProfileGraph
    ) {
        composable(route = ProfileRouteScreen.ProfileScreen.route) {
            val viewModel: HomeScreenViewModel = hiltViewModel()
            val updateViewModel: UpdateProfileScreenViewModel = hiltViewModel()
            ProfileScreen(rootNavController, viewModel,updateViewModel)
        }
        composable(route = ProfileRouteScreen.UpdateProfileScreen.route ){
            val viewModel: UpdateProfileScreenViewModel = hiltViewModel()
            Column{
                TopBar(
                    onBackClick = {
                        rootNavController.popBackStack()
                    },
                    title = "Edit Profile"
                )
                UpdateProfileScreen(
                    viewModel = viewModel,
                    navController = rootNavController
                )
            }
        }
        composable(route = ProfileRouteScreen.GoalUpdateScreen.route){
            val viewModel: UpdateProfileScreenViewModel = hiltViewModel()
            Column {
                TopBar(
                    onBackClick = {
                        rootNavController.popBackStack()
                    },
                    title = "Your Goals"
                )
                GoalUpdateScreen(
                    uiStates = ProfileState(),
                    viewModel = viewModel,
                    navController = rootNavController
                )
            }
        }
        composable(route = ProfileRouteScreen.LevelUpdateScreen.route){
            val viewModel: UpdateProfileScreenViewModel = hiltViewModel()
            val states = ProfileState()
            Column {
                TopBar(
                    onBackClick = {
                        rootNavController.popBackStack()
                    },
                    title = "Physical Activity Level"
                )
                LevelUpdateScreen(
                    uiStates = states,
                    viewModel = viewModel,
                    navController = rootNavController
                )
            }
        }
        composable(route = ProfileRouteScreen.AchievementsScreen.route){
            val viewModel: HomeScreenViewModel = hiltViewModel()
            Column {
                TopBar(
                    onBackClick = {
                        rootNavController.popBackStack()
                    },
                    title = "Edit Achievement"
                )
                AchievementsScreen(
                    viewModel = viewModel,
                    navController = rootNavController
                )
            }
        }
        composable(route = ProfileRouteScreen.ContactUsScreen.route) {
            Column {
                TopBar(
                    onBackClick = {
                        rootNavController.popBackStack()
                    },
                    title = "Contact Us"
                )
                ContactUsScreen(navController = rootNavController)
            }
        }
        composable(route = ProfileRouteScreen.PrivacyPolicyScreen.route) {
            Column {
                TopBar(
                    onBackClick = {
                        rootNavController.popBackStack()
                    },
                    title = "Privacy Policy"
                )
                PrivacyPolicyScreen()
            }
        }
    }
}
