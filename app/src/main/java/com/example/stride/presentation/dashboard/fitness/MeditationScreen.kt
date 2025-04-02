package com.example.stride.presentation.dashboard.fitness

import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stride.R
import com.example.stride.utility.composeUtility.CompletePreviews
import com.example.stride.utility.theme.textStyleInter18Lh28Fw600


@Composable
fun MeditationScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.sheet_color))
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.walk),
                contentDescription = "Meditation",
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
            )
            Text(
                text = "5-Minute Meditation",
                style = textStyleInter18Lh28Fw600(),
                textAlign = TextAlign.Center
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MediaControlButton(R.drawable.back_play)
                Spacer(modifier = Modifier.width(16.dp))
                MediaControlButton(R.drawable.play, isPlayButton = true)
                Spacer(modifier = Modifier.width(16.dp))
                MediaControlButton(R.drawable.play_forward)
            }
        }
    }

}

@Composable
fun MediaControlButton(icon: Int, isPlayButton: Boolean = false) {
    val backgroundColor = if (isPlayButton) Color(0xFFDFF962) else Color.Gray

    Button(
        onClick = { /* Handle button click */ },
        colors = ButtonDefaults.buttonColors(backgroundColor),
        shape = CircleShape,
        modifier = Modifier.size(75.dp)
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier
                .size(45.dp)
                .padding(8.dp),
            colorFilter = ColorFilter.tint(Color.Black)
        )
    }
}

@CompletePreviews
@Composable
private fun MeditationScreenPreview() {
    MeditationScreen()
}
