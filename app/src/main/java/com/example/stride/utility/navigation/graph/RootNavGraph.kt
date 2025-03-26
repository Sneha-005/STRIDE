package com.example.stride.utility.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.stride.presentation.MainScreen
import com.example.stride.utility.navigation.Graph
import com.example.stride.utility.navigation.NavigationViewModel

@Composable
fun RootNavGraph() {
    val rootNavController: NavHostController = rememberNavController()
    val homeNavController: NavHostController = rememberNavController()

    NavHost(
        navController = rootNavController,
        route = Graph.RootGraph,
        startDestination = Graph.AuthGraph
    ) {
        authNavGraph(rootNavController)
        composable(route = Graph.MainGraph) {
            MainScreen(rootNavController, homeNavController)
        }
        homeNavGraph(rootNavController)
        profileNavGraph(rootNavController)
        dietNavGraph(rootNavController)
        fitnessNavGraph(rootNavController)
    }
}