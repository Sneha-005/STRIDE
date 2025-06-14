package com.example.stride.presentation.auth.signUpScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.stride.R
import com.example.stride.utility.composeUtility.CompletePreviews
import com.example.stride.utility.composeUtility.OrientationPreviews
import com.example.stride.utility.composeUtility.sdp
import com.example.stride.utility.composeUtility.ssp
import com.example.stride.utility.theme.textStyleInter12Lh18Fw400
import com.example.stride.utility.theme.textStyleInter12Lh18Fw500
import com.example.stride.utility.theme.textStyleInter16Lh18Fw700
import com.example.stride.utility.theme.textStyleInter16Lh24Fw400
import com.example.stride.utility.theme.textStyleInter24Lh28Fw600
import androidx.compose.runtime.DisposableEffect
import androidx.core.content.ContextCompat
import android.os.Build
import androidx.annotation.RequiresApi

@Composable
fun SignupScreen(
    uiStates: SignUpStates,
    onSignUpClick: () -> Unit,
    setEmail: (String) -> Unit = {},
    setName: (String) -> Unit = {},
    setPassword: (String) -> Unit = {},
    setConfirmPassword: (String) -> Unit = {},
    onSignupComplete: () -> Unit = {},
    setRegistrationState: (Boolean) -> Unit = {},
    calculatePasswordStrength: (String) -> Int = { 0 },
    initialEmail: String = ""
) {
    var isPasswordFieldFocused by remember { mutableStateOf(false) }
    var isConfirmPasswordFieldFocused by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val strength = uiStates.password?.let { calculatePasswordStrength(it) } ?: 0
    val strengthText = when (strength) {
        0 -> "Very Weak"
        1 -> "Weak"
        2 -> "Moderate"
        3 -> "Strong"
        else -> "Strong"
    }

    LaunchedEffect(initialEmail) {
        setEmail(initialEmail)
    }

    if (uiStates?.isLoading == true) {
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator(color = colorResource(id = R.color.primary0))
        }
    }

    if (uiStates?.registrationState == true) {
        setRegistrationState(false)
        onSignupComplete()
    }

    BoxWithConstraints(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(colorResource(id = R.color.background_color))
    ) {
        val isPortrait = maxWidth < maxHeight
        val padding = if (isPortrait) 24.dp else 16.dp
        val calculatedHeight = maxHeight * 0.08f

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = padding, vertical = 32.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sign Up",
                textAlign = TextAlign.Left,
                style = textStyleInter24Lh28Fw600(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(30.sdp))

            OutlinedTextField(
                value = initialEmail,
                onValueChange = { setEmail(it) },
                label = { Text("Email", color = colorResource(id = R.color.font_500)) },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                isError = emailError,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.coral),
                    focusedLabelColor = colorResource(id = R.color.coral),
                    focusedTextColor = colorResource(id = R.color.white)
                ),
                singleLine = true,
                readOnly = true,
                textStyle = textStyleInter16Lh24Fw400(),
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
                    .height(calculatedHeight)
            )

            Spacer(modifier = Modifier.height(16.sdp))

            OutlinedTextField(
                value = uiStates.name ?: "",
                onValueChange = { setName(it) },
                label = { Text("User Name", color = colorResource(id = R.color.font_500)) },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.purple),
                    focusedLabelColor = colorResource(id = R.color.purple),
                    focusedTextColor = colorResource(id = R.color.white),
                    cursorColor = Color.White
                ),
                maxLines = 1,
                textStyle = textStyleInter16Lh24Fw400().copy(textAlign = TextAlign.Start),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth()
                    .height(calculatedHeight)

            )
            if (uiStates.hasAttemptedSignUp && uiStates.name.isNullOrBlank()) {
                Text(
                    text = "Name cannot be empty",
                    color = colorResource(id = R.color.error_color),
                    style = textStyleInter12Lh18Fw500(),
                    modifier = Modifier
                        .padding(start = 24.sdp, top = 8.sdp)
                        .align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(16.sdp))

            OutlinedTextField(
                value = uiStates.password ?: "",
                onValueChange = { setPassword(it) },
                label = { Text("Password", color = colorResource(id = R.color.font_500)) },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    if (isPasswordFieldFocused) {
                        val image = if (isPasswordVisible)
                            painterResource(id = R.drawable.eye_closed)
                        else
                            painterResource(id = R.drawable.eye_open)

                        IconButton(
                            onClick = { isPasswordVisible = !isPasswordVisible },
                            modifier = Modifier.size(24.sdp)
                        ) {
                            Icon(painter = image, contentDescription = null)
                        }
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.purple),
                    focusedLabelColor = colorResource(id = R.color.purple),
                    focusedTextColor = colorResource(id = R.color.white),
                    cursorColor = Color.White
                ),
                maxLines = 1,
                isError = uiStates.isPasswordValid == false,
                singleLine = true,
                textStyle = textStyleInter16Lh24Fw400().copy(color = colorResource(id = R.color.white)),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(calculatedHeight)
                    .onFocusChanged { focusState -> isPasswordFieldFocused = focusState.isFocused }
            )
            if (uiStates.hasAttemptedSignUp && uiStates.isPasswordValid == false) {
                Text(
                    text = uiStates.errorPasswordMessage,
                    color = colorResource(id = R.color.error_color),
                    style = textStyleInter12Lh18Fw500(),
                    modifier = Modifier
                        .padding(start = 24.sdp, top = 8.sdp)
                        .align(Alignment.Start),
                )
            }

            Spacer(modifier = Modifier.height(26.dp))

            OutlinedTextField(
                value = uiStates.confirmPassword ?: "",
                onValueChange = { setConfirmPassword(it) },
                label = { Text("Confirm Password", color = colorResource(id = R.color.font_500)) },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    if (isConfirmPasswordFieldFocused) {
                        val image = if (isConfirmPasswordVisible)
                            painterResource(id = R.drawable.eye_closed)
                        else
                            painterResource(id = R.drawable.eye_open)

                        IconButton(
                            onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible },
                            modifier = Modifier.size(24.sdp)
                        ) {
                            Icon(painter = image, contentDescription = null)
                        }
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.purple),
                    focusedLabelColor = colorResource(id = R.color.purple),
                    focusedTextColor = colorResource(id = R.color.white),
                    cursorColor = Color.White
                ),
                maxLines = 1,
                isError = uiStates.isConfirmPasswordValid == false,
                singleLine = true,
                textStyle = textStyleInter16Lh24Fw400().copy(color = colorResource(id = R.color.white)),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(calculatedHeight)
                    .onFocusChanged { focusState -> isConfirmPasswordFieldFocused = focusState.isFocused }
            )
            if (uiStates.hasAttemptedSignUp && uiStates.isConfirmPasswordValid == false)
                Text(
                    text = "Passwords do not match",
                    color = colorResource(id = R.color.error_color),
                    style = textStyleInter12Lh18Fw500(),
                    modifier = Modifier
                        .padding(start = 24.sdp, top = 8.sdp)
                        .align(Alignment.Start),
                )

            Spacer(modifier = Modifier.height(26.dp))

            Button(
                onClick = onSignUpClick,
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.primary0)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(calculatedHeight)
            ) {
                Text(
                    text = "Sign Up",
                    color = colorResource(id = R.color.black),
                    style = textStyleInter16Lh18Fw700()
                )
            }
        }
    }
}


@OrientationPreviews
@CompletePreviews
@Composable
fun PreviewSignupScreen() {
    SignupScreen(
        uiStates = SignUpStates(),
        onSignupComplete = {},
        onSignUpClick = {},
        setName = {},
        setEmail = {},
        setPassword = {},
        setConfirmPassword = {},
        setRegistrationState = {},
        calculatePasswordStrength = { 0 }
    )
}