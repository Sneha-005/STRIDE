package com.example.stride.presentation.auth.getStarted

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stride.R
import com.example.stride.utility.theme.textStyleInter16Lh28Fw600
import com.example.stride.utility.theme.textStyleInter16Lh28Fw700

@Composable
fun GetStartedScreen() {
    val email = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A1A))
            .padding(horizontal = 24.dp, vertical = 16.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(80.dp)
                .padding(bottom = 24.dp)
        )
        Spacer(modifier = Modifier.height(46.dp))

        Text(
            text = "Get Started",
            textAlign = TextAlign.Left,
            style = textStyleInter16Lh28Fw600(),
            modifier = Modifier.padding(bottom = 26.dp)
        )

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email", color = colorResource(id = R.color.font_500)) },
            placeholder = { Text("Input Text", color = colorResource(id = R.color.font_500)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.coral),
                focusedLabelColor = colorResource(id = R.color.coral)
            ),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(46.dp))

        Button(
            onClick = { /* Handle Continue */ },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.coral)),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(text = "Continue", color = colorResource(id = R.color.black),
                style = textStyleInter16Lh28Fw700())
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Divider(
                modifier = Modifier
                    .height(1.dp)
                    .width(80.dp),
                color = colorResource(id = R.color.divider)
            )
            Text(
                text = "or",
                color = colorResource(id = R.color.divider),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Divider(
                modifier = Modifier
                    .height(1.dp)
                    .width(80.dp),
                color = colorResource(id = R.color.divider)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Handle Google Login */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            border = BorderStroke(1.dp, colorResource(id = R.color.coral)),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.icon_google),
                    contentDescription = "Google Logo",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Continue with Google", color = colorResource(id = R.color.coral), style = textStyleInter16Lh28Fw700())
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewGetStartedScreen() {
    MaterialTheme {
        GetStartedScreen()
    }
}

