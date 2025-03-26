package com.example.stride.presentation.dashboard.homePage

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import android.content.res.Configuration
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.stride.R
import com.example.stride.utility.composeUtility.CircularProgressBar
import com.example.stride.utility.navigation.HomeRouteScreen
import com.example.stride.utility.theme.textStyleInter16Lh18Fw700
import com.example.stride.utility.theme.textStyleInter16Lh24Fw700
import com.example.stride.utility.theme.textStyleInter24Lh28Fw600
import com.example.stride.utility.theme.textStyleInter32Lh38Fw600
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FullBodyWarmUpScreen(
    navHostController: NavHostController,
    viewModel: HomeScreenViewModel,
    workoutId: String
) {
    BackHandler {
        navHostController.navigate(HomeRouteScreen.FullBodyWarmUpScreen.route) {
            popUpTo(HomeRouteScreen.HomeScreen.route) { inclusive = false }
        }
    }

    val exercises by viewModel.exerciseDetails.collectAsState()
    var currentIndex by remember { mutableIntStateOf(0) }
    var isResting by remember { mutableStateOf(false) }
    var remainingTime by remember { mutableIntStateOf(0) }
    var timerJob by remember { mutableStateOf<Job?>(null) }
    var isTimerRunning by remember { mutableStateOf(false) }
    var exerciseId by remember { mutableStateOf<Int?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    LaunchedEffect(Unit) {
        viewModel.fetchExerciseById(workoutId)
    }

    LaunchedEffect(exercises) {
        if (exercises.isNotEmpty()) {
            val exercise = exercises[currentIndex]
            remainingTime = exercise.duration.takeIf { it > 0 } ?: exercise.reps.takeIf { it > 0 }?.let { 20 } ?: 10
            exerciseId = exercise.id
        }
    }

    fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModel.viewModelScope.launch {
            isTimerRunning = true
            while (remainingTime > 0) {
                delay(1000L)
                remainingTime--
            }

            if (remainingTime == 0) {
                if (isResting) {
                    if (currentIndex < exercises.size - 1) {
                        currentIndex++
                        isResting = false
                        exerciseId = exercises[currentIndex].id
                    }
                } else {
                    Log.d("FullBodyWarmUpScreen", "Calling completeExercise for exerciseId: $exerciseId")
                    viewModel.completeWorkout(workoutId, exerciseId.toString())
                    isResting = true
                    remainingTime = exercises[currentIndex].restTime
                }
            }
            isTimerRunning = false
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
        isTimerRunning = false
    }

    fun skipExercise() {
        if (currentIndex < exercises.size - 1) {
            currentIndex++
            isResting = false
            val nextExercise = exercises[currentIndex]
            remainingTime = nextExercise.duration.takeIf { it > 0 } ?: nextExercise.reps.takeIf { it > 0 }?.let { 20 } ?: 10
            exerciseId = nextExercise.id
        } else {
            showDialog = true
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (exercises.isEmpty()) {
            CircularProgressIndicator(color = colorResource(id = R.color.primary0))
        } else {
            val exercise = exercises[currentIndex]

            BoxWithConstraints {
                val width = maxWidth
                val height = maxHeight
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colorResource(id = R.color.sheet_color)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (isLandscape) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(width - 72.dp)
                                    .clip(CircleShape)
                                    .background(color = Color(0xFF202023)),
                                contentAlignment = Alignment.Center
                            ) {
                                if (!isResting) {
                                    Image(
                                        painter = rememberAsyncImagePainter(
                                           exercise.image.toIntOrNull() ?: R.drawable.walk
                                        ),
                                        contentDescription = "Exercise",
                                        modifier = Modifier.size(180.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                } else {
                                    CircularProgressBar(
                                        progress = remainingTime.toString(),
                                        maxProgress = exercise.restTime.toFloat(),
                                        size = width - 72.dp,
                                        cal = false
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(24.dp))

                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(
                                    text = if (isResting) "Relax for ${exercise.restTime}" else exercise.name,
                                    style = textStyleInter24Lh28Fw600()
                                )
                                if (!isResting) {
                                    Text(
                                        text = "$remainingTime",
                                        style = textStyleInter32Lh38Fw600()
                                    )
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    OutlinedButton(
                                        onClick = { startTimer() },
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(50.dp),
                                        shape = RoundedCornerShape(25.dp),
                                        border = BorderStroke(2.dp, Color(0xFFC4FF61))
                                    ) {
                                        Text(
                                            text = "Start",
                                            style = textStyleInter16Lh24Fw700(),
                                            color = colorResource(id = R.color.primary0)
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(16.dp))

                                    Button(
                                        onClick = { stopTimer() },
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(50.dp),
                                        shape = RoundedCornerShape(25.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = colorResource(
                                                id = R.color.primary0
                                            )
                                        )
                                    ) {
                                        Text(
                                            text = "Pause",
                                            style = textStyleInter16Lh24Fw700(),
                                            color = Color.Black
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                TextButton(onClick = { skipExercise() }) {
                                    Text(
                                        text = "Skip",
                                        style = textStyleInter16Lh18Fw700(),
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    } else {

                        Column(
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp)
                        ) {
                            Text(
                                text = if (isResting) "Relax for ${exercise.restTime}" else exercise.name,
                                style = textStyleInter24Lh28Fw600(),
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            Box(
                                modifier = Modifier
                                    .size(width - 72.dp)
                                    .clip(CircleShape)
                                    .background(color = Color(0xFF202023)),
                                contentAlignment = Alignment.Center
                            ) {
                                if (!isResting) {
                                    Image(
                                        painter = painterResource(
                                            id = exercise.image.toIntOrNull() ?: R.drawable.walk
                                        ),
                                        contentDescription = "Exercise",
                                        modifier = Modifier.size(180.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                } else {
                                    CircularProgressBar(
                                        progress = remainingTime.toString(),
                                        maxProgress = exercise.restTime.toFloat(),
                                        size = width - 72.dp,
                                        cal = false
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))
                            if (!isResting) {
                                Text(
                                    text = "$remainingTime",
                                    style = textStyleInter32Lh38Fw600()
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                OutlinedButton(
                                    onClick = { startTimer() },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(50.dp),
                                    shape = RoundedCornerShape(25.dp),
                                    border = BorderStroke(
                                        2.dp,
                                        Color(0xFFC4FF61)
                                    ),
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Transparent)
                                ) {
                                    Text(
                                        text = "Start",
                                        style = textStyleInter16Lh24Fw700(),
                                        color = colorResource(id = R.color.primary0)
                                    )
                                }

                                Spacer(modifier = Modifier.width(16.dp))

                                Button(
                                    onClick = { stopTimer() },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(50.dp),
                                    shape = RoundedCornerShape(25.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = colorResource(
                                            id = R.color.primary0
                                        )
                                    )
                                ) {
                                    Text(
                                        text = "Pause",
                                        style = textStyleInter16Lh24Fw700(),
                                        color = Color.Black
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            TextButton(onClick = { skipExercise() }) {
                                Text(
                                    text = "Skip",
                                    style = textStyleInter16Lh18Fw700(),
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
        if (showDialog) {
            WorkoutCompleteDialog(navController = navHostController, viewModel)
        }
    }
}