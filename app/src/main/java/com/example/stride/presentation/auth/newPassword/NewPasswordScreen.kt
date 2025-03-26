package com.example.stride.presentation.auth.newPassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.stride.utility.composeUtility.CompletePreviews
import com.example.stride.utility.composeUtility.OrientationPreviews
import com.example.stride.utility.composeUtility.sdp
import com.example.stride.utility.theme.textStyleInter12Lh18Fw500
import com.example.stride.utility.theme.textStyleInter16Lh18Fw700
import com.example.stride.utility.theme.textStyleInter16Lh24Fw400
import com.example.stride.utility.theme.textStyleInter24Lh28Fw600

@Composable
fun NewPasswordScreen(
    uiStates: NewPasswordStates,
    setConfirmPassword: (String) -> Unit = {},
    setPassword: (String) -> Unit = {},
    onSavePasswordClick: () -> Unit = {},
    calculatePasswordStrength: (String) -> Int = { 0 }
) {
    var isPasswordFieldFocused by remember { mutableStateOf(false) }
    var isConfirmPasswordFieldFocused by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val strength = uiStates.password?.let { calculatePasswordStrength(it) } ?: 0

    val strengthText = when (strength) {
        0 -> "Very Weak"
        1 -> "Weak"
        2 -> "Moderate"
        3 -> "Strong"
        else -> "Strong"
    }

    if (uiStates.isLoading) {
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator(color = colorResource(id = R.color.primary0))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_color))
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 84.dp),
    ) {
        Text(
            text = "Create a New Password",
            textAlign = TextAlign.Left,
            style = textStyleInter24Lh28Fw600(),
            modifier = Modifier.padding(bottom = 26.dp)
        )

        Text(
            text = "Please set a new password for your Stride account.",
            textAlign = TextAlign.Left,
            color = colorResource(id = R.color.font_500),
            style = textStyleInter16Lh24Fw400(),
            modifier = Modifier.padding(bottom = 26.dp)
        )

        Spacer(modifier = Modifier.height(26.dp))

        OutlinedTextField(
            value = uiStates.password ?: "",
            onValueChange = { setPassword(it) },
            label = {
                Text("Password", color = colorResource(id = R.color.font_500))
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.purple),
                focusedLabelColor = colorResource(id = R.color.purple),
                focusedTextColor = colorResource(id = R.color.white),
                cursorColor = Color.White
            ),
            maxLines = 1,
            isError = uiStates.isPasswordValid == false,
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
            singleLine = true,
            textStyle = textStyleInter16Lh24Fw400().copy(textAlign = TextAlign.Start),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState -> isPasswordFieldFocused = focusState.isFocused }
        )
        if (uiStates.isPasswordValid == false)
            Text(
                text = uiStates.errorPasswordMessage,
                color = colorResource(id = R.color.error_color),
                style = textStyleInter12Lh18Fw500(),
                modifier = Modifier
                    .padding(start = 24.sdp, top = 8.sdp)
                    .align(Alignment.Start),
            )

        Spacer(modifier = Modifier.height(26.dp))

        if (isPasswordFieldFocused && strength < 4) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(colorResource(id = R.color.dark_gray))
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(start = 8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(
                                when {
                                    strength == 4 -> Color.Green
                                    strength == 2 -> Color.Yellow
                                    else -> Color.LightGray
                                }
                            )
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(start = 8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(
                                when {
                                    strength == 4 -> Color.Green
                                    strength == 2 -> Color.Yellow
                                    else -> Color.LightGray
                                }
                            )
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(start = 8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(
                                when {
                                    strength == 4 -> Color.Green
                                    strength == 2 -> Color.Yellow
                                    else -> Color.LightGray
                                }
                            )
                    )
                }

                Text(
                    text = strengthText,
                    color = when (strength) {
                        1 -> Color.Red
                        2 -> Color.Yellow
                        3 -> Color.Green
                        else -> Color.Gray
                    },
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.End),
                    style = textStyleInter12Lh18Fw500()
                )

                Text(
                    text = "Use 8-12 characters with at least one uppercase letter, number, and special character.",
                    style = textStyleInter16Lh24Fw400(),
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        } else {
            OutlinedTextField(
                value = uiStates.confirmPassword ?: "",
                onValueChange = { setConfirmPassword(it) },
                label = {
                    Text("Confirm Password", color = colorResource(id = R.color.font_500))
                },
                colors = OutlinedTextFieldDefaults.colors(
<<<<<<< HEAD
                    focusedBorderColor = colorResource(id = R.color.coral),
                    focusedLabelColor = colorResource(id = R.color.coral),
=======
                    focusedBorderColor = colorResource(id = R.color.purple),
                    focusedLabelColor = colorResource(id = R.color.purple),
                    errorBorderColor = colorResource(id = R.color.error_color),
>>>>>>> e55541d (added id)
                    focusedTextColor = colorResource(id = R.color.white),
                    cursorColor = Color.White
                ),
                maxLines = 1,
                isError = uiStates.isPasswordValid == false,
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
                singleLine = true,
                textStyle = textStyleInter16Lh24Fw400().copy(textAlign = TextAlign.Start),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        isConfirmPasswordFieldFocused = focusState.isFocused
                    }
            )
            if (uiStates.isConfirmPasswordValid == false)
                Text(
                    text = uiStates.errorPasswordMessage,
                    color = colorResource(id = R.color.error_color),
                    style = textStyleInter12Lh18Fw500(),
                    modifier = Modifier
                        .padding(start = 24.sdp, top = 8.sdp)
                        .align(Alignment.Start),
                )
        }

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(26.dp))
        Button(
            onClick = {
                onSavePasswordClick()
            },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.primary0)),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = "Save Password",
                color = colorResource(id = R.color.black),
                style = textStyleInter16Lh18Fw700()
            )
        }
        if (uiStates.isPasswordSaved) {
            Text(
                text = "Password saved successfully!",
                color = Color.Green,
                style = textStyleInter16Lh24Fw400(),
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
@OrientationPreviews
@CompletePreviews
@Composable
fun PreviewNewPasswordScreen() {
    MaterialTheme {
        NewPasswordScreen(
            uiStates = NewPasswordStates(),
            onSavePasswordClick = {},
            setPassword = {},
            setConfirmPassword = {},
            calculatePasswordStrength = { 0 }
        )
    }
}