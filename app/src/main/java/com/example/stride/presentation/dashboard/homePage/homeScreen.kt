package com.example.stride.presentation.dashboard.homePage

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.stride.R
import com.example.stride.utility.theme.textStyleInter16Lh24Fw600
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.stride.data.repository.StepRepository
import com.example.stride.utility.composeUtility.CircularProgressBar
import com.example.stride.utility.composeUtility.CompletePreviews
import com.example.stride.utility.composeUtility.OrientationPreviews
import com.example.stride.utility.navigation.HomeRouteScreen
import com.example.stride.utility.stepCounter.getCurrentDate
import com.example.stride.utility.theme.textStyleInter10Lh14Fw500
import com.example.stride.utility.theme.textStyleInter14Lh20Fw400
import com.example.stride.utility.theme.textStyleInter18Lh28Fw600
import com.example.stride.utility.theme.textStyleInter28Lh34Fw600
import com.example.stride.utility.theme.textStyleInter32Lh38Fw600

@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeScreenViewModel, navController: NavHostController) {

    val context = LocalContext.current
    val activity = context as? Activity
    BackHandler {
        activity?.finish()
    }
    val repository = StepRepository(context)
    var dailySteps by remember { mutableIntStateOf(0) }
    var showBottomSheet by remember { mutableStateOf(false) }
    var clickedCard by remember { mutableStateOf("") }
    val sheetState = rememberModalBottomSheetState()
    val step = 200f
    val steps = (1200 / step).toInt()
    val calories = HomeScreenStates().caloriesConsumed
    val waterIntake by remember { mutableFloatStateOf(0f) }
    val scope = rememberCoroutineScope()
    var showWorkoutBottomSheet by remember { mutableStateOf(false) }
    val selectedImage by remember { mutableStateOf("") }
    val clickedExerciseNames by viewModel.exerciseNames.collectAsState()
    val totalDuration by viewModel.totalDuration.collectAsState()
    var workoutId by remember { mutableStateOf<Int?>(null) }
    val selectedName by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val stepsData = repository.getStepsForDate(getCurrentDate())
        dailySteps = stepsData?.stepCount ?: 0
        viewModel.getWorkoutForUser()
    }
    BoxWithConstraints (
        modifier = Modifier.fillMaxSize()
            .background(color = colorResource(id = R.color.background_color))
    ){
        val maxWidth = maxWidth
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(21.dp)
        ) {
            Row {
                 Text(
                    text = "Hello, User",
                    style = textStyleInter28Lh34Fw600()
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.notification),
                    contentDescription = "Notification",
                    colorFilter = ColorFilter.tint(color = colorResource(id = R.color.white)),
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                )
            }
            Row {
                OutlinedCard(
                    modifier = Modifier
                        .width(maxWidth/2 - 32.dp)
                        .height(182.dp)
                        .clickable {
                            clickedCard = "Walk"
                            showBottomSheet = true
                                   },
                    colors = CardDefaults.outlinedCardColors(
                        containerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row {
                            Text(
                                text = "Walk",
                                style = textStyleInter18Lh28Fw600(),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Image(
                                painter = painterResource(id = R.drawable.walk),
                                contentDescription = "Edit",
                                colorFilter = ColorFilter.tint(color = colorResource(id = R.color.white)),
                                modifier = Modifier
                                    .size(24.dp)

                            )
                        }

                        Text(
                            text = dailySteps.toString(),
                            style = textStyleInter32Lh38Fw600(),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "steps",
                            style = textStyleInter14Lh20Fw400(),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                OutlinedCard(
                    modifier = Modifier
                        .width(maxWidth/2 - 32.dp)
                        .height(182.dp)
                        .clickable {
                            clickedCard = "Calories"
                            showBottomSheet = true
                                   },

                    colors = CardDefaults.outlinedCardColors(
                        containerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                        .align(Alignment.CenterHorizontally)

                    ) {
                        Row {
                            Text(
                                text = "Calories",
                                style = textStyleInter18Lh28Fw600(),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Image(
                                painter = painterResource(id = R.drawable.calorie),
                                contentDescription = "Edit",
                                colorFilter = ColorFilter.tint(color = colorResource(id = R.color.white)),
                                modifier = Modifier
                                    .size(24.dp)
                                ,
                            )
                        }
                        CircularProgressBar(
                            progress = calories.toString(),
                            size = 150.dp,
                            cal = true
                        )
                    }
                }
            }
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(112.dp)
                    .clickable {
                        clickedCard = "Water Intake"
                        showBottomSheet = true
                    },
                colors = CardDefaults.outlinedCardColors(
                    containerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row {
                        Text(
                            text = "Water Intake",
                            style = textStyleInter18Lh28Fw600(),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Image(
                            painter = painterResource(id = R.drawable.water),
                            contentDescription = "Edit",
                            colorFilter = ColorFilter.tint(color = colorResource(id = R.color.white)),
                            modifier = Modifier
                                .size(24.dp),
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(12.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color.DarkGray)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(waterIntake)
                                .height(12.dp)
                                .background(colorResource(id = R.color.purple))
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            repeat(steps + 1) {
                                Canvas(modifier = Modifier.size(6.dp)) {
                                    drawCircle(color = Color.Gray)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                repeat(steps + 1) { index ->
                                    Text(
                                        text = "${(index * step).toInt()} ml",
                                        color = Color.White,
                                        style = textStyleInter10Lh14Fw500()
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                    }

                }
            }
            Row {
                Text(
                    text = "Step into Action",
                    style = textStyleInter16Lh24Fw600()
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "view",
                    style = textStyleInter14Lh20Fw400(),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .clickable {
                            navController.navigate(HomeRouteScreen.StepsIntoActionScreen.route)
                        }
                )
                Image(
                    painter = painterResource(id = R.drawable.arrow_right),
                    contentDescription = "Icon",
                    colorFilter = ColorFilter.tint(color = colorResource(id = R.color.white)),
                    modifier = Modifier
                        .size(15.dp)
                        .align(Alignment.CenterVertically)
                        .clickable {
                            navController.navigate(HomeRouteScreen.StepsIntoActionScreen.route)
                        }
                )
            }
            val workouts by viewModel.workouts.collectAsState()


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
    }
    if (showWorkoutBottomSheet) {
        WarmUpBottomSheet(
            sheetState = sheetState,
            onDismiss = { showWorkoutBottomSheet = false },
            exercises = clickedExerciseNames,
            duration = totalDuration.toString(),
            selectedImage = selectedImage,
            selectedName = selectedName,
            navHostController = navController,
            onStartClick = {
                Log.d("HomeScreen", "Workout ID: $workoutId")
                workoutId?.let { cardId ->
                    navController.navigate("${HomeRouteScreen.FullBodyWarmUpScreen.route}/$cardId")
                }
            }
        )
    }

    if (showBottomSheet) {
        when (clickedCard) {
            "Walk" -> {
                if (dailySteps == 0) {
                    BottomSheet(
                        image = R.drawable.walk,
                        title = "Steps",
                        description = "Keep moving—your steps will update automatically!",
                        count = "0 Steps Recorded!",
                        buttonText = "",
                        sheetState = sheetState,
                        onDismiss = { showBottomSheet = false },
                        navController = navController
                    )
                } else {
                    DataBottomSheet(
                        image = R.drawable.walk,
                        title = "Steps",
                        value = dailySteps.toFloat(),
                        buttonText = "View",
                        sheetState = sheetState,
                        onDismiss = { showBottomSheet = false },
                        viewModel = viewModel
                    )
                }
            }
            "Calories" -> {
                if (calories == 0) {
                    BottomSheet(
                        image = R.drawable.calorie,
                        title = "Calories",
                        description = "Log your meals to start tracking your calories.",
                        count = "0 Calories Logged!",
                        buttonText = "Log Food",
                        sheetState = sheetState,
                        onDismiss = { showBottomSheet = false },
                        navController = navController
                    )
                } else {
                    DataBottomSheet(
                        image = R.drawable.calorie,
                        title = "Calories",
                        value = calories.toFloat(),
                        buttonText = "Log Food",
                        sheetState = sheetState,
                        onDismiss = { showBottomSheet = false },
                        viewModel = viewModel
                    )
                }
            }
            "Water Intake" -> {
                if (waterIntake == 0f) {
                    BottomSheet(
                        image = R.drawable.water,
                        title = "Water Intake",
                        description = "Stay hydrated—log your water intake now.",
                        count = "0 Glasses Logged!",
                        buttonText = "Update Amount",
                        sheetState = sheetState,
                        onDismiss = { showBottomSheet = false },
                        navController = navController
                    )
                } else {
                    DataBottomSheet(
                        image = R.drawable.water,
                        title = "Water Intake",
                        value = calories.toFloat(),
                        buttonText = "Done",
                        sheetState = sheetState,
                        onDismiss = { showBottomSheet = false },
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}


@Composable
@CompletePreviews
@OrientationPreviews
fun HomeScreenPreview() {
    HomeScreen( viewModel = hiltViewModel(), navController = rememberNavController())
}
