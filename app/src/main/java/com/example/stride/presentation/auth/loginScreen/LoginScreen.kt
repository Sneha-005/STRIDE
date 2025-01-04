package com.example.stride.presentation.auth.loginScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import com.example.stride.presentation.auth.getStarted.GetStartedScreen
import com.example.stride.utility.composeUtility.CompletePreviews
import com.example.stride.utility.composeUtility.OrientationPreviews
import com.example.stride.utility.theme.textStyleInter14Lh16Fw700
import com.example.stride.utility.theme.textStyleInter16Lh18Fw700
import com.example.stride.utility.theme.textStyleInter16Lh24Fw400
import com.example.stride.utility.theme.textStyleInter24Lh28Fw600

import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun LoginScreen() {
    val email = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_color))
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 106.dp),
    ) {

        Text(
            text = "Log In",
            textAlign = TextAlign.Left,
            style = textStyleInter24Lh28Fw600(),
            modifier = Modifier.padding(bottom = 26.dp)
        )

        Spacer(modifier = Modifier.height(26.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email", color = colorResource(id = R.color.font_500)) },
            placeholder = { Text("Input Text", color = colorResource(id = R.color.font_500)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.coral),
                focusedLabelColor = colorResource(id = R.color.coral)
            ),
            textStyle = textStyleInter16Lh24Fw400().copy(textAlign = TextAlign.Start),
            singleLine = true,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(36.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Password", color = colorResource(id = R.color.font_500)) },
            placeholder = { Text("Input Text", color = colorResource(id = R.color.font_500)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.coral),
                focusedLabelColor = colorResource(id = R.color.coral)
            ),
            singleLine = true,
            textStyle = textStyleInter16Lh24Fw400().copy(textAlign = TextAlign.Start),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(76.dp))

        Button(
            onClick = {  },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.coral)),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(text = "Log In", color = colorResource(id = R.color.black),
                style = textStyleInter16Lh18Fw700()
            )
        }

        Spacer(modifier = Modifier.height(36.dp))

        Text(
            text = "Forgot Password?",
           style = textStyleInter14Lh16Fw700(),
            color = colorResource(id = R.color.coral),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .align(alignment = Alignment.CenterHorizontally)
        )
    }
}

@OrientationPreviews
@CompletePreviews
@Composable
fun PreviewGetStartedScreen() {
    MaterialTheme {
        LoginScreen()
    }
}