package com.example.stride.presentation.dashboard.profile

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.stride.R
import com.example.stride.utility.theme.textStyleInter14Lh20Fw400
import com.example.stride.utility.theme.textStyleInter16Lh18Fw700
import com.example.stride.utility.theme.textStyleInter16Lh24Fw600
import com.example.stride.utility.theme.textStyleInter18Lh28Fw600
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.stride.utility.navigation.Graph
import com.example.stride.utility.navigation.ProfileRouteScreen
import com.example.stride.utility.navigation.TopBar
import kotlinx.coroutines.launch

@Composable
fun GoalUpdateScreen(
    uiStates: ProfileState,
    navController: NavHostController,
    viewModel: UpdateProfileScreenViewModel
) {
    BackHandler {
        val canNavigateBack = navController.previousBackStackEntry != null
        if (canNavigateBack) {
            navController.popBackStack()
        } else {
            navController.navigate(ProfileRouteScreen.ProfileScreen.route) {
                popUpTo(ProfileRouteScreen.ProfileScreen.route) { inclusive = false }
            }
        }
    }


    var selectedCard by remember { mutableStateOf<String?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiStates.errorMessage) {
        uiStates.errorMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
            }
        }
    }


    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_color))
    ) {
        val screenHeight = maxHeight
        val spacing = screenHeight / 20
        Column {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(spacing),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(top = 16.dp)
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .height(screenHeight * 0.8f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Your goals will help you to achieve it",
                            color = colorResource(id = R.color.white),
                            style = textStyleInter16Lh24Fw600()
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = spacing),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Cards(
                                imageResId = R.drawable.gym_workout_svgrepo_com,
                                title = "Lose Weight",
                                description = "Burn Calories & Get Ideal body",
                                isSelected = selectedCard == "Lose Weight",
                                onClick = {
                                    selectedCard = "Lose Weight"
                                    viewModel.setFitnessGoal("Lose_Weight")
                                }
                            )
                            Cards(
                                imageResId = R.drawable.gym_fitness_svgrepo_com,
                                title = "Gain Muscles",
                                description = "Build mass & strength",
                                isSelected = selectedCard == "Gain Muscles",
                                onClick = {
                                    selectedCard = "Gain Muscles"
                                    viewModel.setFitnessGoal("Gain_Muscles")
                                }
                            )
                            Cards(
                                imageResId = R.drawable.meditation_round_svgrepo_com,
                                title = "Yoga & Meditation",
                                description = "Feel more healthy",
                                isSelected = selectedCard == "Yoga & Meditation",
                                onClick = {
                                    selectedCard = "Yoga & Meditation"
                                    viewModel.setFitnessGoal("Yoga_And_Meditation")
                                }
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        val context = LocalContext.current

                        Button(
                            onClick = {
                                viewModel.updateUserProfile(context)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.primary0))
                        ) {
                            Text(
                                text = "Save",
                                style = textStyleInter16Lh18Fw700(),
                                color = colorResource(id = R.color.black)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Cards(imageResId: Int, title: String, description: String, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.darker_gray),
        ),
        border = if (isSelected) BorderStroke(2.dp, colorResource(id = R.color.primary0)) else null,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(96.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = title,
                    style = textStyleInter18Lh28Fw600(),
                    color = colorResource(id = R.color.white)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = description,
                    style = textStyleInter14Lh20Fw400(),
                    color = colorResource(id = R.color.white),
                    modifier = Modifier.padding(end = 14.dp),
                    textAlign = TextAlign.Center
                )
            }
            Image(
                painter = painterResource(id = imageResId),
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                contentDescription = "Logo"
            )
        }
    }
}

