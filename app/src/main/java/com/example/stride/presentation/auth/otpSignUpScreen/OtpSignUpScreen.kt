package com.example.stride.presentation.auth.otpSignUpScreen

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.stride.R
import com.example.stride.utility.composeUtility.CompletePreviews
import com.example.stride.utility.composeUtility.OrientationPreviews
import com.example.stride.utility.composeUtility.sdp
import com.example.stride.utility.theme.textStyleInter16Lh18Fw700
import com.example.stride.utility.theme.textStyleInter16Lh24Fw400
import com.example.stride.utility.theme.textStyleInter24Lh28Fw600


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OtpSignUpScreen(
    uiStates: OtpSignUpStates,
    onOtpChange: (String) -> Unit,
    onVerifyClick: (String) -> Unit,
    onResendOtpClick: () -> Unit,
    setOtp: (String) -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    var resendMessage by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current
    var otp1 by remember { mutableStateOf("") }
    var otp2 by remember { mutableStateOf("") }
    var otp3 by remember { mutableStateOf("") }
    var otp4 by remember { mutableStateOf("") }
    var timer by remember { mutableStateOf(60) }

    val otp = otp1 + otp2 + otp3 + otp4
    val isOtpCorrect = uiStates.isOtpVerified
    Log.d("otp", otp)

    LaunchedEffect(key1 = timer) {
        if (timer > 0) {
            kotlinx.coroutines.delay(1000L)
            timer -= 1
        }
    }

    if (uiStates?.isLoading == true) {
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator(color = colorResource(id = R.color.coral))
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_color))
    ) {
        val maxWidth = maxWidth
        val maxHeight = maxHeight

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(maxHeight * 0.13f))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = if (isLandscape) screenWidth / 20 else screenWidth / 8
                    ),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Verify your Account",
                    color = colorResource(id = R.color.font_600),
                    style = textStyleInter24Lh28Fw600()
                )
                Text(
                    text = "Enter the 4-digit code sent to your email",
                    color = colorResource(id = R.color.font_500),
                    style = textStyleInter16Lh24Fw400()
                )
            }

            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.sdp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    OutlinedTextField(
                        value = otp1,
                        onValueChange = {
                            if (it.length <= 1) otp1 = it
                            if (it.isNotEmpty()) {
                                focusManager.moveFocus(FocusDirection.Next)
                            }
                            if (it.isEmpty()) {
                                focusManager.clearFocus()
                            }
                            setOtp(otp1 + otp2 + otp3 + otp4)

                        },
                        shape = RoundedCornerShape(10.sdp),
                        modifier = Modifier
                            .padding(top = 10.sdp)
                            .width(50.sdp),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Number
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Next) }
                        ),
                        textStyle = textStyleInter16Lh24Fw400().copy(textAlign = TextAlign.Start),
                        maxLines = 1,
                        isError = uiStates.isOtpValid == false,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = if (isOtpCorrect) Color.Green else colorResource(id = R.color.coral),
                            unfocusedBorderColor = if (isOtpCorrect) Color.Green else colorResource(id = R.color.font_500),
                            errorBorderColor = if (isOtpCorrect) Color.Green else colorResource(id = R.color.error_color),
                            cursorColor = Color.White
                        ),

                        )
                    OutlinedTextField(
                        value = otp2,
                        onValueChange = {
                            if (it.length <= 1) otp2 = it
                            if (it.isNotEmpty()) {
                                focusManager.moveFocus(FocusDirection.Next)
                            }
                            if (it.isEmpty()) focusManager.moveFocus(FocusDirection.Previous)
                            setOtp(otp1 + otp2 + otp3 + otp4)

                        },
                        shape = RoundedCornerShape(10.sdp),
                        modifier = Modifier
                            .padding(top = 10.sdp)
                            .width(50.sdp),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Number
                        ),
                        textStyle = textStyleInter16Lh24Fw400().copy(textAlign = TextAlign.Start),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Next) }
                        ),
                        maxLines = 1,
                        isError = uiStates?.isOtpValid == false,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = if (isOtpCorrect) Color.Green else colorResource(id = R.color.coral),
                            unfocusedBorderColor = if (isOtpCorrect) Color.Green else colorResource(id = R.color.font_500),
                            errorBorderColor = if (isOtpCorrect) Color.Green else colorResource(id = R.color.error_color),
                            cursorColor = Color.White
                        )
                    )
                    OutlinedTextField(
                        value = otp3,
                        onValueChange = {
                            if (it.length <= 1) otp3 = it
                            if (it.isNotEmpty()) {
                                focusManager.moveFocus(FocusDirection.Next)
                            }
                            if (it.isEmpty()) {
                                focusManager.moveFocus(FocusDirection.Previous)
                            }
                            setOtp(otp1 + otp2 + otp3 + otp4)

                        },
                        shape = RoundedCornerShape(10.sdp),
                        modifier = Modifier
                            .padding(top = 10.sdp)
                            .width(50.sdp),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Number
                        ),
                        textStyle = textStyleInter16Lh24Fw400().copy(textAlign = TextAlign.Start),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Next) }
                        ),
                        maxLines = 1,
                        isError = uiStates?.isOtpValid == false,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = if (isOtpCorrect) Color.Green else colorResource(id = R.color.coral),
                            unfocusedBorderColor = if (isOtpCorrect) Color.Green else colorResource(id = R.color.font_500),
                            errorBorderColor = if (isOtpCorrect) Color.Green else colorResource(id = R.color.error_color),
                            cursorColor = Color.White
                        )
                    )
                    OutlinedTextField(
                        value = otp4,
                        onValueChange = {
                            if (it.length <= 1) {
                                otp4 = it
                                if (it.isNotEmpty()) {
                                    focusManager.moveFocus(FocusDirection.Next)
                                }
                                if (it.isEmpty()) {
                                    focusManager.moveFocus(FocusDirection.Previous)
                                }
                            }
                            setOtp(otp1 + otp2 + otp3 + otp4)
                        },
                        shape = RoundedCornerShape(10.sdp),
                        modifier = Modifier
                            .padding(top = 10.sdp)
                            .width(50.sdp),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Number
                        ),
                        textStyle = textStyleInter16Lh24Fw400().copy(textAlign = TextAlign.Start),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Next) }
                        ),
                        maxLines = 1,
                        isError = uiStates?.isOtpValid == false,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = if (isOtpCorrect) Color.Green else colorResource(id = R.color.coral),
                            unfocusedBorderColor = if (isOtpCorrect) Color.Green else colorResource(id = R.color.font_500),
                            errorBorderColor = if (isOtpCorrect) Color.Green else colorResource(id = R.color.error_color),
                            cursorColor = Color.White
                        )
                    )
                }
            }

            if (uiStates.isOtpValid == false) {
                Text(
                    text = uiStates.errorOtpMessage ?: "Invalid OTP",
                    color = colorResource(id = R.color.error_color),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            if (resendMessage.isNotEmpty()) {
                Text(
                    text = resendMessage,
                    color = Color.Green,
                    modifier = Modifier.padding(top = 8.dp),
                    style = textStyleInter16Lh24Fw400()
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier
                    .padding(vertical = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        if (otp.length == 4) {
                            onVerifyClick(otp)
                        }
                    },
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.coral))
                ) {
                    Text(text = "Verify", color = Color.Black, style = textStyleInter16Lh18Fw700())
                }

                Spacer(modifier = Modifier.padding(vertical = 16.dp))

                if (timer > 0) {
                    Text(
                        text = "Resend code in $timer s",
                        color = colorResource(id = R.color.font_500),
                        style = textStyleInter16Lh24Fw400()
                    )
                } else {
                    FlowRow(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .clickable {
                                onResendOtpClick()
                                resendMessage = "A new OTP has been sent to your email."
                                timer = 60 // Reset the timer value here
                            }
                    ) {

                        Text(
                            text = "Didn’t receive the code?",
                            color = colorResource(id = R.color.font_500),
                            style = textStyleInter16Lh24Fw400()
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "Resend",
                            color = colorResource(id = R.color.light_coral),
                            style = textStyleInter16Lh24Fw400()
                        )
                    }
                }
            }
        }
    }
}
@OrientationPreviews
@CompletePreviews
@Composable
fun PreviewOtpScreenResponsive() {
    OtpSignUpScreen (
        uiStates = OtpSignUpStates(),
        onOtpChange = {},
        onVerifyClick = {},
        onResendOtpClick = {},
        setOtp = {}
    )
}
