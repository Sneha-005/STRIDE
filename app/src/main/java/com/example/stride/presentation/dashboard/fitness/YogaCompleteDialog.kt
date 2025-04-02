package com.example.stride.presentation.dashboard.fitness

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.stride.R
import com.example.stride.utility.composeUtility.CompletePreviews

@Composable
fun YogaCompleteDialog() {
    Dialog(onDismissRequest = {  }) {
    Box(
        modifier = Modifier
            .requiredWidth(width = 312.dp)
            .requiredHeight(height = 414.dp)
            .clip(shape = RoundedCornerShape(16.dp))
            .background(color = Color(0xff1a1a1d))
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()

        )
        Column(
            verticalArrangement = Arrangement.spacedBy(13.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 0.dp,
                    y = 35.dp)
                .requiredWidth(width = 312.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.thumb),
                contentDescription = "Icons",
                modifier = Modifier
                    .requiredWidth(width = 82.dp)
                    .requiredHeight(height = 78.dp))
            Text(
                text = "yoga aasan",
                color = Color(0xffdddde1),
                textAlign = TextAlign.Center,
                lineHeight = 1.56.em,
                style = TextStyle(
                    fontSize = 18.sp),
                modifier = Modifier
                    .fillMaxWidth())
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 0.dp,
                    y = 178.dp)
                .requiredWidth(width = 312.dp)
                .requiredHeight(height = 39.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.coins),
                contentDescription = "Group 32",
                modifier = Modifier
                    .requiredWidth(width = 40.dp)
                    .requiredHeight(height = 39.dp))
            Text(
                text = "+5",
                color = Color(0xffd1d1d1),
                lineHeight = 1.17.em,
                style = MaterialTheme.typography.headlineSmall)
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(33.dp, Alignment.Top),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 18.5.dp,
                    y = 240.7054443359375.dp)
                .requiredWidth(width = 275.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(17.628204345703125.dp, Alignment.Start),
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(height = 57.dp)
                    .clip(shape = RoundedCornerShape(14.102563858032227.dp))
                    .background(color = Color(0xff29292d))
                    .padding(horizontal = 14.102563858032227.dp,
                        vertical = 6.169871807098389.dp)
            ) {
                Column() {
                    Text(
                        text = "20",
                        color = Color(0xffdddde1),
                        textAlign = TextAlign.Center,
                        lineHeight = 1.12.em,
                        style = TextStyle(
                            fontSize = 22.000001907348633.sp),
                        modifier = Modifier
                            .requiredWidth(width = 106.dp))
                    Text(
                        text = "Total Exercises",
                        color = Color(0xffb0b0b0),
                        textAlign = TextAlign.Center,
                        lineHeight = 1.6.em,
                        style = TextStyle(
                            fontSize = 13.199999809265137.sp),
                        modifier = Modifier
                            .requiredWidth(width = 106.dp))
                }
                Column() {
                    Text(
                        text = "233",
                        color = Color(0xffdddde1),
                        textAlign = TextAlign.Center,
                        lineHeight = 1.12.em,
                        style = TextStyle(
                            fontSize = 22.000001907348633.sp),
                        modifier = Modifier
                            .requiredWidth(width = 106.dp))
                    Text(
                        text = "Calories Burnt",
                        color = Color(0xffb0b0b0),
                        textAlign = TextAlign.Center,
                        lineHeight = 1.6.em,
                        style = TextStyle(
                            fontSize = 13.199999809265137.sp),
                        modifier = Modifier
                            .requiredWidth(width = 106.dp))
                }
            }
            Property1filled()
        }
        Icon(
            painter = painterResource(id = R.drawable.cross),
            contentDescription = "Actions & UI/times",
            tint = Color(0xffd1d1d1),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 267.dp,
                    y = 23.dp))
    }
}
    }

@Composable
fun Property1filled(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(height = 48.dp)
            .clip(shape = RoundedCornerShape(30.dp))
            .background(color = Color(0xffd9f779))
    ) {
        Text(
            text = "Next Challenge",
            color = Color(0xff262626),
            lineHeight = 9.38.em,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold))
    }
}

@Composable
@CompletePreviews
fun YogaCompleteDialogPreview() {
    YogaCompleteDialog()
}