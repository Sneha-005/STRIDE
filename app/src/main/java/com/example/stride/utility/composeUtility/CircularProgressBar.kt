package com.example.stride.utility.composeUtility

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import com.example.stride.utility.theme.textStyleInter18Lh28Fw600

@Composable
fun CircularProgressBar(
    progress: String,
    maxProgress: Float = 1000f,
    size: Dp = 150.dp,
    cal: Boolean
) {
    val sweepAngle = (progress.toInt() / maxProgress) * 360f
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(size)
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val strokeWidth = size.toPx() * 0.12f
            val progressStrokeWidth = size.toPx() * 0.12f

            drawArc(
                color = Color.Gray.copy(alpha = 0.4f),
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(strokeWidth, cap = StrokeCap.Round)
            )

            drawArc(
                color = Color(0xFF9C7FF5),
                startAngle = 135f,
                sweepAngle = (sweepAngle * 0.75f),
                useCenter = false,
                style = Stroke(progressStrokeWidth, cap = StrokeCap.Round)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "${progress.toInt()}",
                style = textStyleInter18Lh28Fw600()
            )
            if(cal)
            Text(
                text = "kCal",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
@CompletePreviews
fun PreviewCircularProgressBar() {
    CircularProgressBar(progress = "730f", cal = true)
}
