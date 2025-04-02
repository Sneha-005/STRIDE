package com.example.stride.presentation.dashboard.diet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.stride.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import com.example.stride.utility.composeUtility.CompletePreviews
import com.example.stride.utility.composeUtility.OrientationPreviews
import com.example.stride.utility.composeUtility.sdp
import com.example.stride.utility.theme.textStyleInter14Lh20Fw500
import com.example.stride.utility.theme.textStyleInter16Lh24Fw600
import com.example.stride.utility.theme.textStyleInter16Lh24Fw700
import androidx.compose.ui.res.painterResource

@Composable
fun EmptyDietScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF262626))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .padding(24.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.e_cart),
                contentDescription = "E-Commerce 05"
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Your Food Log is Empty!",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    style = textStyleInter16Lh24Fw600(),
                    modifier = Modifier
                        .fillMaxWidth())
                Text(
                    text = "Log your food or explore healthy recipes to stay on track.",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    style = textStyleInter14Lh20Fw500(),
                    modifier = Modifier
                        .fillMaxWidth())
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    Button(
                        onClick = { /* Pause Action */ },
                        modifier = Modifier
                            .height(50.sdp)
                        ,
                        shape = RoundedCornerShape(25.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.primary0))
                    ) {
                        Text(
                            text = "Log Food",
                            style = textStyleInter16Lh24Fw700(),
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    OutlinedButton(
                        onClick = { /* Restart Action */ },
                        modifier = Modifier
                            .height(50.dp),
                        shape = RoundedCornerShape(25.dp),
                        border = BorderStroke(2.dp, Color(0xFFC4FF61)),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Transparent)
                    ) {
                        Text(
                            text = "View Recipes",
                            style = textStyleInter16Lh24Fw700(),
                            color = colorResource(id = R.color.primary0)
                        )
                    }
                }
            }
        }
    }
}

@Composable
@CompletePreviews
@OrientationPreviews
private fun EmptyDietScreenPreview() {
    EmptyDietScreen()
}