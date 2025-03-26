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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
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
import com.example.stride.utility.theme.textStyleInter18Lh28Fw600
import com.example.stride.utility.theme.textStyleInter24Lh28Fw600

@Composable
fun AsanaCompleteDialog(
    selectedName: String
) {
    Dialog(onDismissRequest = {  }) {
        Box(
            modifier = Modifier
                .requiredWidth(width = 312.dp)
                .requiredHeight(height = 298.dp)
                .clip(shape = RoundedCornerShape(16.dp))
                .background(color = colorResource(id = R.color.sheet_color))
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxSize()

            )
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 0.dp,
                        y = 55.dp)
                    .width(width = 312.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.thumb),
                    contentDescription = "Icons",
                    modifier = Modifier
                        .width(width = 82.dp)
                        .height(height = 78.dp))
                Text(
                    text = "${selectedName} Complete\nSuccessfully!",
                    color = Color(0xffdddde1),
                    textAlign = TextAlign.Center,
                    style = textStyleInter18Lh28Fw600()
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 0.dp,
                        y = 240.dp)
                    .fillMaxWidth()

            ) {
                Text(
                    text = "+5",
                    color = Color(0xffd1d1d1),
                    style = textStyleInter24Lh28Fw600()
                   )
                Image(
                    painter = painterResource(id = R.drawable.coins),
                    contentDescription = "Group 32",
                    modifier = Modifier
                        .requiredWidth(width = 40.dp)
                        .requiredHeight(height = 39.dp))

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
@CompletePreviews
fun PreviewAsanaCompleteDialog() {
    AsanaCompleteDialog(selectedName = "Warrior Pose")
}
