package com.example.stride.presentation.dashboard.fitness

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.stride.R
import com.example.stride.utility.navigation.FitnessRouteScreen
import com.example.stride.utility.navigation.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardioScreen(viewModel: StrengthViewModel,navHostController: NavHostController) {

    BackHandler {
        navHostController.navigate(FitnessRouteScreen.CardioScreen.route) {
            popUpTo(FitnessRouteScreen.CategoryScreen.route) { inclusive = false }
        }
    }
//    val sheetState = rememberModalBottomSheetState()
//    val scope = rememberCoroutineScope()
//    var showBottomSheet by remember { mutableStateOf(false) }
//    var selectedImage by remember { mutableStateOf("") }
//    var selectedCardId by remember { mutableStateOf<Int?>(null) }
//    var selectedName by remember { mutableStateOf("") }
//
//    Column(
//        modifier = Modifier.fillMaxSize().background(Color.Black)
//    ) {
//        Spacer(modifier = Modifier.padding(7.dp))
//        LaunchedEffect(Unit) {
//            Log.d("CardioScreen", "Fetching exercises")
//            viewModel.fetchExercises("Cardio")
//        }
//        val isLoading by viewModel.isLoading.collectAsState()
//        val exercises by viewModel.exercises.collectAsState()
//        val clickedExerciseNames by viewModel.exerciseNames.collectAsState()
//        val totalDuration by viewModel.totalDuration.collectAsState()
//
//        LaunchedEffect(clickedExerciseNames) {
//            if (clickedExerciseNames.isNotEmpty()) {
//                showBottomSheet = true
//            }
//        }
//
//        Log.d("CardioScreen", "Number of exercises: ${exercises.size}")
//        if (isLoading) {
//            Box(
//                modifier = Modifier.fillMaxSize().background(Color.Black),
//                contentAlignment = Alignment.Center
//            ) {
//                CircularProgressIndicator(color = colorResource(id = R.color.primary0))
//            }
//        } else {
//            LazyColumn(
//                verticalArrangement = Arrangement.spacedBy(8.dp),
//                modifier = Modifier.padding(16.dp)
//            ) {
//                Log.d("CardioScreen", "Number of exercises in LazyColumn: ${exercises.size}")
//                items(exercises) { exercise ->
//                    ExerciseItem(
//                        exercise = exercise,
//                        sheetState = sheetState,
//                        scope = scope,
//                        showBottomSheet = { showBottomSheet = it },
//                        onButtonClick = { id ->
//                            showBottomSheet = true
//                            selectedImage = exercise.image
//                            selectedCardId = exercise.id
//                            selectedName = exercise.name
//                            viewModel.fetchExerciseById(id)
//                        }
//                    )
//                }
//            }
//        }
//        if (showBottomSheet) {
//            WarmUpBottomSheet(
//                sheetState = sheetState,
//                onDismiss = { showBottomSheet = false },
//                exercises = clickedExerciseNames,
//                duration = totalDuration.toString(),
//                selectedImage = selectedImage,
//                navHostController = navHostController,
//                selectedName = selectedName,
//                onStartClick = {
//                    selectedCardId?.let { cardId ->
//                        navHostController.navigate("${FitnessRouteScreen.FullBodyWarmUpScreen.route}/$cardId")
//                    }                }
//            )
//        }
//    }
}