package com.example.stride.presentation.dashboard.fitness

import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.stride.R
import com.example.stride.utility.navigation.FitnessRouteScreen
import com.example.stride.utility.navigation.Graph
import com.example.stride.utility.navigation.HomeRouteScreen
import com.example.stride.utility.navigation.ProfileRouteScreen
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StrengthTrainingScreen(viewModel: StrengthViewModel,navHostController: NavHostController) {
    val categories = listOf("legs", "shoulders", "back", "calf", "core", "biceps", "chest", "triceps")
    var selectedCategory by remember { mutableStateOf("legs") }


    LaunchedEffect(Unit) {
        viewModel.fetchExercises(selectedCategory)
    }

    BackHandler {
            navHostController.navigate(FitnessRouteScreen.StrengthTrainingScreen.route) {
                popUpTo(FitnessRouteScreen.CategoryScreen.route) { inclusive = false }
            }
    }
    val isLoading by viewModel.isLoading.collectAsState()
    val exercises by viewModel.exercises.collectAsState()
    var selectedCardId by remember { mutableStateOf<Int?>(null) }
    var selectedName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black)
    ) {
        LazyRow {
            items(categories.size) { index ->
                val category = categories[index]
                Box(
                    modifier = Modifier
                        .padding(horizontal = 6.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            if (category == selectedCategory) Color(0xFF444444) else Color(
                                0xFF222222
                            )
                        )
                        .clickable {
                            selectedCategory = category
                            viewModel.fetchExercises(category)
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = category,
                        color = if (category == selectedCategory) Color.White else Color.Gray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize().background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = colorResource(id = R.color.primary0))
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Log.d("CardioScreen", "Number of exercises in LazyColumn: ${exercises.size}")
                items(exercises) { exercise ->
                    ExerciseItem(
                        exercise = exercise,
                        onButtonClick = {
                            val exerciseJson = Uri.encode(Gson().toJson(exercise))
                            navHostController.navigate("full_body_warm_up_screen?exerciseJson=$exerciseJson")

                        }
                    )
                }
            }
        }
    }
}