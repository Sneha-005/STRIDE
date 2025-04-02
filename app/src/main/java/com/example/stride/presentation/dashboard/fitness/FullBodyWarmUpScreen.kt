package com.example.stride.presentation.dashboard.fitness

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.stride.R
import com.example.stride.utility.theme.textStyleInter16Lh24Fw700
import com.example.stride.utility.theme.textStyleInter24Lh28Fw600
import com.example.stride.utility.theme.textStyleInter32Lh38Fw600
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FullBodyWarmUpScreen(
    navController: NavController,
    viewModel: StrengthViewModel,
    exercise: Exercise
) {
    var remainingTime by remember { mutableIntStateOf(0) }
    var timerJob by remember { mutableStateOf<Job?>(null) }
    var isTimerRunning by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    LaunchedEffect(Unit) {
        remainingTime = exercise.duration?.toIntOrNull() ?: exercise.reps?.let { 20 } ?: 10
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
                viewModel.completeExercise(exercise.id.toString(), context)
                showDialog = true
            }
            isTimerRunning = false
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
        isTimerRunning = false
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

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
                            Image(
                                painter = rememberAsyncImagePainter(
                                    exercise.image
                                ),
                                contentDescription = "Exercise",
                                modifier = Modifier.size(180.dp),
                                contentScale = ContentScale.Fit
                            )
                        }
                        Spacer(modifier = Modifier.width(24.dp))

                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(
                                text = exercise.name,
                                style = textStyleInter24Lh28Fw600()
                            )
                            Text(
                                text = "$remainingTime",
                                style = textStyleInter32Lh38Fw600()
                            )
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
                            text = exercise.name,
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
                            Image(
                                painter = rememberAsyncImagePainter(
                                    exercise.image
                                ),
                                contentDescription = "Exercise",
                                modifier = Modifier.size(180.dp),
                                contentScale = ContentScale.Fit
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "$remainingTime",
                            style = textStyleInter32Lh38Fw600()
                        )
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
                    }
                }
            }
        }
        if (showDialog) {
            ExerciseCompleteDialog(navController = navController, viewModel)
        }
    }
}