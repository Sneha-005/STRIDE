package com.example.stride.presentation.dashboard.homePage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.stride.utility.composeUtility.CircularProgressBar
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.stride.R
import com.example.stride.utility.composeUtility.CompletePreviews
import com.example.stride.utility.theme.textStyleInter16Lh24Fw600
import com.example.stride.utility.theme.textStyleInter16Lh24Fw700
import com.example.stride.utility.theme.textStyleInter24Lh28Fw600
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutBottomSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val exercises = listOf(
        "Standard Push-Ups",
        "Chest Dips",
        "Wide-Arm Push-Ups",
        "Floor Press",
        "Resistance Band Flyes",
        "Pilates Chest Expansion"
    )
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp)
                .background(colorResource(id = R.color.sheet_color)),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
            ) {
                CircularProgressBar(
                    progress = "0.5f",
                    maxProgress = 1f,
                    size = 80.dp,
                    cal = false
                )
                Column {
                    Text(
                        text = "Fullbody Workout",
                        style = textStyleInter24Lh28Fw600(),
                    )
                    Text(
                        text = "20 min remaining",
                        style = textStyleInter16Lh24Fw600(),
                    )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Remaining Exercises",
                        style = textStyleInter24Lh28Fw600()
                    )
                    exercises.forEach { exercise ->
                        Text(
                            text = "• $exercise",
                            style = textStyleInter24Lh28Fw600(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }
            Button(
                onClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onDismiss()
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.primary0)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp)
            ) {
                Text(text = "Continue Exercise", color = Color.Black, style = textStyleInter16Lh24Fw700())
            }
        }
    }
}
