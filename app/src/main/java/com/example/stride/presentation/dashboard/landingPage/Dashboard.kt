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
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.stride.R
import com.example.stride.utility.composeUtility.CompletePreviews
import com.example.stride.utility.composeUtility.OrientationPreviews
import com.example.stride.utility.theme.textStyleInter16Lh18Fw700
import com.example.stride.utility.theme.textStyleInter16Lh24Fw500
import com.example.stride.utility.theme.textStyleInter24Lh28Fw600

@Composable
fun Dashboard(
    navController: NavHostController
) {
    BackHandler {
        (navController.context as? Activity)?.finishAffinity()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_color))
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            val maxHeight = maxHeight

            Image(
                painter = painterResource(id = R.drawable.dashboard),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(maxHeight * 0.4f),
                contentDescription = "Logo"
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = "Let’s Get to Know You!",
                color = colorResource(id = R.color.white),
                style = textStyleInter24Lh28Fw600(),
                textAlign = TextAlign.Center

            )

            Text(
                text = "We’ll tailor your fitness journey based on your answers.",
                color = colorResource(id = R.color.font_500),
                style = textStyleInter16Lh24Fw500(),
                modifier = Modifier.padding(vertical = 14.dp),
                textAlign = TextAlign.Center
            )

        }

        Button(
            onClick = { navController.navigate("questions")},
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.primary0)),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(48.dp)
        ) {
            Text(
                text = "Let's Start",
                color = colorResource(id = R.color.black),
                style = textStyleInter16Lh18Fw700()
            )
        }
    }
}

@OrientationPreviews
@CompletePreviews
@Composable
fun PreviewDashboard() {
    Dashboard(navController = rememberNavController())
}
