package com.example.stride.presentation.auth.otpLoginScreen

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import com.example.stride.R
import com.example.stride.utility.composeUtility.CompletePreviews
import com.example.stride.utility.composeUtility.OrientationPreviews
import com.example.stride.utility.composeUtility.sdp
import com.example.stride.utility.theme.textStyleInter12Lh18Fw400
import com.example.stride.utility.theme.textStyleInter14Lh20Fw600
import com.example.stride.utility.theme.textStyleInter16Lh18Fw700
import com.example.stride.utility.theme.textStyleInter16Lh24Fw400
import com.example.stride.utility.theme.textStyleInter24Lh28Fw600


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OtpScreenResponsive(
    uiStates: OtpStates,
    onOtpChange: (String) -> Unit,
    onOtpClick: (String) -> Unit,
    onResendOtpClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val focusManager = LocalFocusManager.current
    var otp1 by remember { mutableStateOf("") }
    var otp2 by remember { mutableStateOf("") }
    var otp3 by remember { mutableStateOf("") }
    var otp4 by remember { mutableStateOf("") }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_color))
    ) {
        val maxWidth = maxWidth
        val maxHeight = maxHeight
        val horizontalPadding = if (maxWidth < 400.dp) 8.dp else 16.dp
        val otpFieldWidth = maxWidth / 8
        val otpFieldHeight = maxHeight / 14

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = horizontalPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = if (isLandscape) screenWidth / 15 else screenWidth / 10,
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = screenWidth / 25),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf(otp1, otp2, otp3, otp4).forEachIndexed { index, otp ->
                    OutlinedTextField(
                        value = otp,
                        onValueChange = {
                            when (index) {
                                0 -> otp1 = it
                                1 -> otp2 = it
                                2 -> otp3 = it
                                3 -> otp4 = it
                            }
                            if (it.length == 1) focusManager.moveFocus(FocusDirection.Next)
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = if (index == 3) ImeAction.Done else ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Next) },
                            onDone = { onOtpClick(otp1 + otp2 + otp3 + otp4) }
                        ),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .width(otpFieldWidth)
                            .height(otpFieldHeight)
                            .padding(horizontal = 4.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(id = R.color.coral),
                            unfocusedBorderColor = colorResource(id = R.color.font_500)
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

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { onOtpClick(otp1 + otp2 + otp3 + otp4) },
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.coral))
            ) {
                Text(text = "Verify", color = Color.Black, style = textStyleInter16Lh18Fw700())
            }

            FlowRow(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = maxHeight / 20)
            ) {
                Text(
                    text = "Didn’t receive the code?",
                    color = colorResource(id = R.color.font_500),
                    style = textStyleInter12Lh18Fw400()
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Resend",
                    color = colorResource(id = R.color.light_coral),
                    style = textStyleInter14Lh20Fw600()
                )
            }
        }
    }
}
@OrientationPreviews
@CompletePreviews
@Composable
fun PreviewOtpScreenResponsive() {
    OtpScreenResponsive(
        uiStates = OtpStates(),
        onOtpChange = {},
        onOtpClick = {},
        onResendOtpClick = {}
    )
}
