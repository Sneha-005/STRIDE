package com.example.stride.presentation.dashboard.fitness

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stride.R
import com.example.stride.utility.composeUtility.CompletePreviews

@Composable
fun PerformAsanaScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.layer_1),
            contentDescription = "Yoga Pose",
            modifier = Modifier
                .height(180.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Padahastasana",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "(Standing Forward Bend)",
            color = Color.Gray,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        val steps = listOf(
            "Inhale and lift your arms overhead.",
            "Stretch your whole body upward.",
            "Slightly arch your back while keeping your core engaged.",
            "Keep your biceps close to your ears."
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            steps.forEachIndexed { index, step ->
                StepItem(stepNumber = index + 1, stepText = step, isLast = index == steps.lastIndex)
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = { /* Perform Asana Action */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD6FF62)),
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Perform Asana", color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun StepItem(stepNumber: Int, stepText: String, isLast: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = stepNumber.toString().padStart(2, '0'),
            color = Color.Gray,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(28.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Canvas(
                modifier = Modifier.size(24.dp)
            ) {
                val radius = size.minDimension / 2
                drawCircle(
                    color = Color.Gray,
                    radius = radius,
                    style = Stroke(width = 3f)
                )
                drawCircle(
                    color = Color.Gray,
                    radius = radius / 3
                )
            }

            if (!isLast) {
                Spacer(modifier = Modifier.height(4.dp))
                DottedLine(modifier = Modifier.height(40.dp))
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Step $stepNumber",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = stepText,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun DottedLine(modifier: Modifier = Modifier) {
    Canvas(
        modifier = modifier.width(2.dp)
    ) {
        val dotRadius = 3f
        val space = 10f
        var currentY = 0f

        while (currentY < size.height) {
            drawCircle(
                color = Color.Gray.copy(alpha = 0.6f),
                radius = dotRadius,
                center = center.copy(y = currentY + dotRadius)
            )
            currentY += (dotRadius * 2) + space
        }
    }
}

@Composable
@CompletePreviews
fun PerformAsanaScreenPreview() {
    PerformAsanaScreen()
}
