package com.example.stride.presentation.dashboard.landingPage

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.stride.R
import com.example.stride.utility.composeUtility.CompletePreviews
import com.example.stride.utility.composeUtility.OrientationPreviews
import com.example.stride.utility.theme.textStyleInter16Lh18Fw700
import com.example.stride.utility.theme.textStyleInter16Lh24Fw500
import com.example.stride.utility.theme.textStyleInter24Lh28Fw600

@Composable
fun EndDashboard(
    uiStates: LandingStates? = LandingStates(),
    landingSuccess: () -> Unit = {},
    navController: NavHostController,
    onLandingClick: () -> Unit = {}
) {

    if (uiStates?.isLandingState == true) {
        landingSuccess()
    }

    if (uiStates?.isLoading == true) {
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator(color = colorResource(id = R.color.coral))
        }
    }

    BackHandler {
        (navController.context as? Activity)?.finishAffinity()
    }

    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        val maxHeight = maxHeight
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.background_color))
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            Image(
                painter = painterResource(id = R.drawable.end_dashboard),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(maxHeight * 0.4f),
                contentDescription = "Logo"
            )


            Text(
                text = "Your Personalized Journey is Ready!",
                color = colorResource(id = R.color.white),
                style = textStyleInter24Lh28Fw600(),
                textAlign = TextAlign.Center
            )

            Text(
                text = "We’ve tailored the app to suit your goals. Let’s start your journey to success!",
                color = colorResource(id = R.color.font_500),
                style = textStyleInter16Lh24Fw500(),
                modifier = Modifier.padding(vertical = 14.dp),
                textAlign = TextAlign.Center
            )


            Button(
                onClick = {
                    onLandingClick()
                },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.primary0)),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(48.dp)
            ) {
                Text(
                    text = "Start Your Journey",
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
fun EndDashboardPreview() {
    EndDashboard(navController = rememberNavController())
}