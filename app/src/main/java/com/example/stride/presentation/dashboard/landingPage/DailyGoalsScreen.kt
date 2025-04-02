package com.example.stride.presentation.dashboard.landingPage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.stride.R
import com.example.stride.utility.composeUtility.sdp
import com.example.stride.utility.theme.textStyleInter14Lh20Fw400
import com.example.stride.utility.theme.textStyleInter16Lh24Fw400
import com.example.stride.utility.theme.textStyleInter24Lh28Fw600

@Composable
fun DailyGoalsScreen(
    uiStates: LandingStates? = LandingStates(),
    setStepsGoals: (Float) -> Unit = {},
    setCalorieGoals: (Float) -> Unit = {},
    setWaterIntakeGoals: (Float) -> Unit = {},
    navController: NavHostController
){
    var stepsGoal by remember { mutableStateOf("") }
    var calorieGoal by remember { mutableStateOf( "") }
    var waterIntakeGoal by remember { mutableStateOf("") }

    val isFormValid = stepsGoal.toFloatOrNull()?.let { it > 0 } == true &&
            calorieGoal.toFloatOrNull()?.let { it > 0 } == true &&
            waterIntakeGoal.toFloatOrNull()?.let { it > 0 } == true

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_color))
    ) {
        val screenWidth = maxWidth
        val screenHeight = maxHeight

        val isPortrait = screenHeight > screenWidth

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = (screenWidth * 0.05f)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(if (isPortrait) 16.sdp else 8.sdp))

            Text(
                text = "Set Your Daily Goals",
                color = Color.White,
                fontSize = (if (isPortrait) 20.sp else 16.sp),
                style = textStyleInter24Lh28Fw600(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(if (isPortrait) 16.sdp else 8.sdp))

            Text(
                text = "Track your progress and stay motivated by setting personalized daily goals.",
                color = colorResource(id = R.color.font_500),
                fontSize = (if (isPortrait) 20.sp else 16.sp),
                style = textStyleInter14Lh20Fw400(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(if (isPortrait) 16.sdp else 8.sdp))

            OutlinedTextField(
                value = stepsGoal,
                onValueChange = {
                    stepsGoal = it
                    val ageValue = it.toIntOrNull() ?: 0
                    setStepsGoals(ageValue.toFloat())
                },
                label = { Text(text = "Steps", color = Color.White) },
                shape = MaterialTheme.shapes.medium,
                singleLine = true,
                textStyle = textStyleInter16Lh24Fw400().copy(textAlign = TextAlign.Start),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.purple),
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight * 0.12f)
            )

            Spacer(modifier = Modifier.height(if (isPortrait) 16.sdp else 8.sdp))

            OutlinedTextField(
                value = calorieGoal,
                onValueChange = {
                    calorieGoal = it
                    val ageValue = it.toIntOrNull() ?: 0
                    setCalorieGoals(ageValue.toFloat())
                },
                label = { Text(text = "Calories(in kCal)", color = Color.White) },
                shape = MaterialTheme.shapes.medium,
                singleLine = true,
                textStyle = textStyleInter16Lh24Fw400().copy(textAlign = TextAlign.Start),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.purple),
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight * 0.12f)
            )

            Spacer(modifier = Modifier.height(if (isPortrait) 16.sdp else 8.sdp))

            OutlinedTextField(
                value = waterIntakeGoal,
                onValueChange = {
                    waterIntakeGoal = it
                    val ageValue = it.toIntOrNull() ?: 0
                    setWaterIntakeGoals(ageValue.toFloat())
                },
                label = { Text(text = "Water Intake(in L)", color = Color.White) },
                shape = MaterialTheme.shapes.medium,
                singleLine = true,
                textStyle = textStyleInter16Lh24Fw400().copy(textAlign = TextAlign.Start),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.purple),
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight * 0.12f)
            )

            Spacer(modifier = Modifier.height(if (isPortrait) 16.sdp else 8.sdp))

            Button(
                onClick = {
                    if (isFormValid)
                        navController.navigate("end_dashboard")
                },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.primary0)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height((screenHeight * 0.09f).coerceAtMost(48.dp))
            ) {
                Text(
                    text = "Next",
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(if (isPortrait) 16.sdp else 8.sdp))
        }
    }
}

@Composable
@Preview
fun DailyGoalsScreenPreview(){
    DailyGoalsScreen(
        navController = rememberNavController()
    )
}