package com.example.stride.presentation.auth.resetPassword

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.stride.R
import com.example.stride.utility.composeUtility.CompletePreviews
import com.example.stride.utility.composeUtility.OrientationPreviews
import com.example.stride.utility.composeUtility.sdp
import com.example.stride.utility.theme.textStyleInter12Lh18Fw500
import com.example.stride.utility.theme.textStyleInter16Lh18Fw700
import com.example.stride.utility.theme.textStyleInter16Lh24Fw400
import com.example.stride.utility.theme.textStyleInter24Lh28Fw600


@Composable
fun ResetPasswordScreen(
    uiStates: ResetPasswordStates,
    onVerificationCodeClick: () -> Unit,
    setEmail: (String) -> Unit,
    stateSuccess: () -> Unit = {},
    setForgotPasswordState: (Boolean) -> Unit,
    initialEmail: String = ""

) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    if(uiStates.forgotPasswordState) {
        stateSuccess()
        setForgotPasswordState(false)
    }

    LaunchedEffect(initialEmail) {
        setEmail(initialEmail)
    }

    if(uiStates.isLoading){
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator(color = colorResource(id = R.color.coral))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_color))
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(screenHeight * 0.13f))

        Text(
            text = "Reset Your Password",
            textAlign = TextAlign.Left,
            style = textStyleInter24Lh28Fw600(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Enter your email address to receive a\nverification code.",
            textAlign = TextAlign.Left,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            color = colorResource(id = R.color.font_500),
            style = textStyleInter16Lh24Fw400(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(26.dp))

        OutlinedTextField(
            value = initialEmail,
            onValueChange = { setEmail(it) },
            label = { Text("Email", color = colorResource(id = R.color.font_500)) },
            placeholder = { Text("Input Text", color = colorResource(id = R.color.font_500)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.coral),
                focusedLabelColor = colorResource(id = R.color.coral),
                focusedTextColor = colorResource(id = R.color.white),
                cursorColor = Color.White

            ),
            readOnly = true,
            isError = uiStates.isEmailValid == false,
            singleLine = true,
            textStyle = textStyleInter16Lh24Fw400().copy(textAlign = TextAlign.Start),
            shape = MaterialTheme.shapes.medium,
            trailingIcon = {
                if (uiStates.isEmailValid == false) {
                    Icon(
                        painter = painterResource(id = R.drawable.email_error),
                        contentDescription = "Error",
                        tint = colorResource(id = R.color.error_color)
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        )
        if (uiStates.isEmailValid == false)
            Text(
                text = uiStates.errorEmailMessage,
                color = colorResource(id = R.color.error_color),
                style = textStyleInter12Lh18Fw500(),
                modifier = Modifier
                    .padding(start = 20.sdp, top=8.sdp)
                    .align(Alignment.Start),
            )

        Spacer(modifier = Modifier.height(screenHeight * 0.4f))

        Button(
            onClick = onVerificationCodeClick,
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.coral)),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = "Send Verification Code",
                color = colorResource(id = R.color.black),
                style = textStyleInter16Lh18Fw700()
            )
        }
    }
}
@OrientationPreviews
@CompletePreviews
@Composable
fun PreviewGetStartedScreen() {
    ResetPasswordScreen(
        uiStates = ResetPasswordStates(),
        onVerificationCodeClick = {},
        setEmail = {},
        stateSuccess = {},
        setForgotPasswordState = {}
    )
}

