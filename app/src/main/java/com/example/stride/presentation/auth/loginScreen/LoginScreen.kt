package com.example.stride.presentation.auth.loginScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.stride.R
import com.example.stride.presentation.auth.getStarted.GetStartedScreen
import com.example.stride.utility.composeUtility.CompletePreviews
import com.example.stride.utility.composeUtility.OrientationPreviews
import com.example.stride.utility.composeUtility.sdp
import com.example.stride.utility.theme.textStyleInter12Lh18Fw500
import com.example.stride.utility.theme.textStyleInter14Lh16Fw700
import com.example.stride.utility.theme.textStyleInter16Lh18Fw700
import com.example.stride.utility.theme.textStyleInter16Lh24Fw400
import com.example.stride.utility.theme.textStyleInter24Lh28Fw600

import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun LoginScreen(
    uiStates: LoginStates? = LoginStates(),
    onForgotPasswordClick: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    setEmail: (String) -> Unit = {},
    setPassword: (String) -> Unit = {},
    loginSuccess: () -> Unit = {},
    initialEmail: String = ""
) {

    var isPasswordFieldFocused by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    var emailError by remember { mutableStateOf(false) }

    if (uiStates?.loginState == true) {
        loginSuccess()
    }

    LaunchedEffect(initialEmail) {
        setEmail(initialEmail)
    }

    if (uiStates?.isLoading == true) {
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator(color = colorResource(id = R.color.coral))
        }
    }

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
            value = initialEmail,
            onValueChange = { setEmail(it) },
            label = {
                Text(
                    "Email",
                    color = colorResource(id = R.color.font_500)
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.coral),
                focusedLabelColor = colorResource(id = R.color.coral),
                focusedTextColor = colorResource(id = R.color.white)
            ),
            isError = emailError,
            singleLine = true,
            readOnly = true,
            textStyle = textStyleInter16Lh24Fw400().copy(textAlign = TextAlign.Start),
            shape = MaterialTheme.shapes.medium,
            trailingIcon = {
                if (uiStates?.isEmailValid == false) {
                    Icon(
                        painter = painterResource(id = R.drawable.email_error),
                        contentDescription = "Error",
                        tint = colorResource(id = R.color.error_color)
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    emailError = true
                    Log.d("LoginScreen", "emailError set to $emailError")

                }
        )
        //how to show this text message
        if (emailError) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "You can't update email here",
                color = colorResource(id = R.color.error_color),
                style = textStyleInter12Lh18Fw500(),
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(Alignment.Start)
            )
        }


        Spacer(modifier = Modifier.height(36.dp))

        OutlinedTextField(
            value = uiStates?.password ?: "",
            onValueChange = { setPassword(it) },
            label = {
                Text("Password",
                    color = colorResource(id = R.color.font_500))
                    },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.coral),
                focusedLabelColor = colorResource(id = R.color.coral),
                focusedTextColor = colorResource(id = R.color.white),
                cursorColor = Color.White
            ),
            maxLines = 1,
            isError = uiStates?.isPasswordValid == false,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
              if (isPasswordFieldFocused) {
                    val image = if (isPasswordVisible)
                        painterResource(id = R.drawable.eye_closed)
                    else
                        painterResource(id = R.drawable.eye_open)

                    IconButton(
                        onClick = { isPasswordVisible = !isPasswordVisible },
                        modifier = Modifier
                            .size(24.sdp)
                    ) {
                        Icon(painter = image, contentDescription = null)
                    }
                }
            },
            singleLine = true,
            textStyle = textStyleInter16Lh24Fw400().copy(textAlign = TextAlign.Start),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isPasswordFieldFocused = focusState.isFocused

                }
        )
        if (uiStates?.isPasswordValid == false)
            Text(
                text = uiStates.errorPasswordMessage,
                color = colorResource(id = R.color.error_color),
                style = textStyleInter12Lh18Fw500(),
                modifier = Modifier
                    .padding(start = 24.sdp, top = 8.sdp)
                    .align(Alignment.Start),
            )

        Spacer(modifier = Modifier.height(76.dp))

        Button(
            onClick = onLoginClick,
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
                .clickable { onForgotPasswordClick() }
        )
    }
}

@OrientationPreviews
@CompletePreviews
@Composable
fun PreviewGetStartedScreen() {
    LoginScreen()
}