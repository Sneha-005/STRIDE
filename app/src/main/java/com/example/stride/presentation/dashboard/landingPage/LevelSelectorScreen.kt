package com.example.stride.presentation.dashboard.landingPage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.stride.R
import com.example.stride.utility.composeUtility.CompletePreviews
import com.example.stride.utility.composeUtility.OrientationPreviews
import com.example.stride.utility.theme.textStyleInter16Lh18Fw700
import com.example.stride.utility.theme.textStyleInter24Lh28Fw600

@Composable
fun LevelSelectorScreen(
    uiStates: LandingStates? = LandingStates(),
    setLevelGoal: (String) -> Unit = {},
    navController: NavHostController,
    levelSuccess: () -> Unit = {}
) {
    if (uiStates?.levelState == true) {
        levelSuccess()
    }

    if (uiStates?.isLoading == true) {
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator(color = colorResource(id = R.color.primary0))
        }
    }

    var selectedLevel by remember { mutableStateOf("") }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_color))
    ) {
        val screenWidth = maxWidth
        val screenHeight = maxHeight

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(26.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Physical Activity Level",
                color = Color.White,
                style = textStyleInter24Lh28Fw600(),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )

            val levels = listOf("Beginner", "Intermediate", "Advanced")
            levels.forEach { level ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    backgroundColor = colorResource(id = R.color.charcoal),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight * 0.1f)
                        .clickable {
                            selectedLevel = level
                            setLevelGoal(level)
                        }
                        .then(
                            if (selectedLevel == level) Modifier.border(
                                width = 2.dp,
                                color = colorResource(id = R.color.primary0),
                                shape = RoundedCornerShape(16.dp)
                            ) else Modifier
                        )
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = level,
                            color = colorResource(id = R.color.white),
                            style = textStyleInter24Lh28Fw600()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(screenHeight * 0.02f))
            }

            Button(
                onClick = { navController.navigate("daily_goals_screen") },
                enabled = selectedLevel.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.primary0)),
                shape = RoundedCornerShape(26.dp)
            ) {
                Text(
                    text = "Submit",
                    color = Color.Black,
                    style = textStyleInter16Lh18Fw700()
                )
            }
        }
    }
}

@CompletePreviews
@OrientationPreviews
@Composable
fun LevelSelectorScreenPreview() {
    LevelSelectorScreen(
        navController = rememberNavController()
    )
}
