package com.example.stride.presentation.dashboard.landingPage.goalSelection

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.stride.R
import com.example.stride.data.local.GoalSelectionItem
import com.example.stride.utility.theme.textStyleInter16Lh24Fw500
import com.example.stride.utility.theme.textStyleInter24Lh28Fw600

@Composable
fun GoalSelectionItem(item: GoalSelectionItem, backgroundColor : Color){
    BoxWithConstraints(
    ) {
        val maxHeight = maxHeight
        val maxWidth = maxWidth
        Column(
            Modifier.background(backgroundColor)
                .fillMaxWidth()
                .height(maxHeight * 0.7f),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Image(
                painter = painterResource(id = item.imageResId),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(maxHeight * 0.4f),
                contentDescription = "Logo"
            )
            Text(
                text = item.title,
                style = textStyleInter24Lh28Fw600(),
                color = colorResource(id = R.color.black),
                textAlign = TextAlign.Center
            )

            Text(
                text = item.description,
                style = textStyleInter16Lh24Fw500(),
                color = colorResource(id = R.color.black),
                modifier = Modifier.padding(end = 14.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}