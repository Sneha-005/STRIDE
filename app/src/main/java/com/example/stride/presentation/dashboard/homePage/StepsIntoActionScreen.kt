package com.example.stride.presentation.dashboard.homePage

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.stride.R
import com.example.stride.utility.navigation.FitnessRouteScreen
import com.example.stride.utility.navigation.HomeRouteScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StepsIntoActionScreen(viewModel: HomeScreenViewModel, navHostController: NavHostController) {

    BackHandler {
        navHostController.navigate(HomeRouteScreen.StepsIntoActionScreen.route) {
            popUpTo(HomeRouteScreen.HomeScreen.route) { inclusive = false }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getAllWorkouts()
    }

    val workouts by viewModel.workouts.collectAsState()
    val scope = rememberCoroutineScope()
    var showWorkoutBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    var workoutId by remember { mutableStateOf<Int?>(null) }
    val selectedImage by remember { mutableStateOf("") }
    val clickedExerciseNames by viewModel.exerciseNames.collectAsState()
    val selectedName by remember { mutableStateOf("") }
    val totalDuration by viewModel.totalDuration.collectAsState()

    Box(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxSize()
            .background(color = colorResource(id = R.color.sheet_color))
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(workouts) { workout ->
                WorkoutCard(
                    workoutName = workout.name,
                    workoutId = workout.workoutId,
                    imageRes = painterResource(id = R.drawable.walk).toString(),
                    categories = workout.goal,
                    progress = workout.numberOfExercisesCompleted.toFloat() / workout.numberOfExercises,
                    sheetState = sheetState,
                    scope = scope,
                    showBottomSheet = { showWorkoutBottomSheet = it },
                    onArrowClick = { id ->
                        workoutId = id
                        showWorkoutBottomSheet = true
                        viewModel.fetchExerciseById(id.toString())
                    }
                )
            }
        }
    }
    if (showWorkoutBottomSheet) {
        WarmUpBottomSheet(
            sheetState = sheetState,
            onDismiss = { showWorkoutBottomSheet = false },
            exercises = clickedExerciseNames,
            duration = totalDuration.toString(),
            selectedImage = selectedImage,
            selectedName = selectedName,
            navHostController = navHostController,
            onStartClick = {
                Log.d("HomeScreen", "Workout ID: $workoutId")
                workoutId?.let { cardId ->
                    navHostController.navigate("${HomeRouteScreen.FullBodyWarmUpScreen.route}/$cardId")
                }
            }
        )
    }
}