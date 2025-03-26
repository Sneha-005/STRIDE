package com.example.stride.presentation.dashboard.diet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.stride.R
import com.example.stride.utility.composeUtility.CompletePreviews
import com.example.stride.utility.composeUtility.OrientationPreviews
import com.example.stride.utility.theme.textStyleInter14Lh20Fw400
import com.example.stride.utility.theme.textStyleInter14Lh20Fw500
import com.example.stride.utility.theme.textStyleInter18Lh28Fw600

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipeDetailScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_google),
                contentDescription = "Diet Description",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Text(
                text = "Nutrition",
                modifier = Modifier.fillMaxWidth(),
                style = textStyleInter18Lh28Fw600()
            )
            Text(
                text = "Description",
                modifier = Modifier.fillMaxWidth(),
                style = textStyleInter18Lh28Fw600()
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "",
                    modifier = Modifier.fillMaxWidth(),
                    style = textStyleInter14Lh20Fw400(),
                    maxLines = 4
                )
                Text(
                    text = "Read More...",
                    modifier = Modifier.fillMaxWidth(),
                    style = textStyleInter14Lh20Fw400(),
                    color = colorResource(id = R.color.purple)
                )
            }
            Row {
                Text(
                    text = "Ingredients",
                    modifier = Modifier.fillMaxWidth(),
                    style = textStyleInter18Lh28Fw600()
                )
                Text(
                    text = "items",
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth(),
                    style = textStyleInter14Lh20Fw500(),
                    color = colorResource(id = R.color.light_gray)
                )
            }
            Row {
                Text(
                    text = "Step by Step",
                    modifier = Modifier.fillMaxWidth(),
                    style = textStyleInter18Lh28Fw600()
                )
                Text(
                    text = "Steps",
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth(),
                    style = textStyleInter14Lh20Fw500(),
                    color = colorResource(id = R.color.light_gray)
                )
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(16.dp)
            ) {
//                items(steps) { step ->
//                    StepItem(step)
//                }
            }
        }
    }
}
//@Composable
//fun StepItem(step: RecipeStep) {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp)
//    ) {
//        Text(
//            text = "Step ${step.stepNumber}",
//            style = textStyleInter14Lh20Fw500()
//        )
//        Text(
//            text = step.title,
//            style = textStyleInter14Lh20Fw500()
//        )
//        Text(
//            text = step.description,
//            style = textStyleInter12Lh16Fw500()
//        )
//        Divider(
//            modifier = Modifier.padding(vertical = 8.dp),
//            color = Color.Gray
//        )
//    }
//}

@Composable
@CompletePreviews
@OrientationPreviews
fun RecipeDetailScreenPreview() {
    RecipeDetailScreen()
}