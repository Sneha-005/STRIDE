package com.example.stride.presentation.dashboard.landingPage.goalSelection

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.stride.data.local.GoalSelectionItem
import com.example.stride.presentation.dashboard.landingPage.LandingStates
import com.example.stride.utility.theme.textStyleInter16Lh18Fw700
import com.example.stride.utility.theme.textStyleInter24Lh28Fw600
import com.example.stride.utility.composeUtility.CompletePreviews
import com.example.stride.utility.composeUtility.OrientationPreviews
import com.example.stride.utility.composeUtility.sdp

@Composable
fun GoalSelectionScreen(
    uiStates: LandingStates? = LandingStates(),
    setFitnessGoal: (String) -> Unit = {},
    navController: NavHostController,
    goalSuccess: () -> Unit = {}
) {

    if (uiStates?.goalState == true) {
        goalSuccess()
    }

    if (uiStates?.isLoading == true) {
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator(color = colorResource(id = R.color.primary0))
        }
    }

    val items = listOf(
        GoalSelectionItem(
            R.drawable.layer_1,
            "Lose Weight",
            "Achieve a healthier, lighter you with a personalized weight loss journey."
        ),
        GoalSelectionItem(
            R.drawable.layer_2,
            "Gain Muscles",
            "Bulk up with tailored plans for strength and muscle growth."
        ),
        GoalSelectionItem(
            R.drawable.layer_3,
            "Yoga And Meditation",
            "Improve your flexibility, focus, and peace of mind."
        )
    )
    BoxWithConstraints {
        val maxHeight = maxHeight
        val maxWidth = maxWidth
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.background_color)),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "What’s Your Goal?",
                color = Color.White,
                style = textStyleInter24Lh28Fw600(),
                textAlign = TextAlign.Center
            )

            val selectedIndex = remember { mutableStateOf(-1) }

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = Modifier.width(maxWidth)
            ) {
                items(items.size) { index ->
                    val item = items[index]
                    val isSelected = selectedIndex.value == index
                    val cardColor = if (isSelected) colorResource(id = R.color.selected) else colorResource(id = R.color.unselected)

                    Card(
                        modifier = Modifier
                            .width(maxWidth * 0.9f)
                            .padding(end = 16.sdp)
                            .clickable {
                                selectedIndex.value = if (isSelected) -1 else index
                                setFitnessGoal(item.title.replace(" ", "_"))
                            },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(cardColor)
                    ) {
                        GoalSelectionItem(item = item, backgroundColor = cardColor)
                    }
                }
            }

            Button(
                onClick = { navController.navigate("level_selector_screen") },
                enabled = selectedIndex.value != -1,
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.primary0)),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .height((maxHeight * 0.09f).coerceAtMost(48.dp))
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Next",
                    color = colorResource(id = R.color.black),
                    style = textStyleInter16Lh18Fw700()
                )
            }

        }
    }
}

@CompletePreviews
@OrientationPreviews
@Composable
fun GoalSelectionScreenPreview() {
    GoalSelectionScreen(navController = rememberNavController())
}