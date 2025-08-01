package com.example.stride.presentation.auth.getStarted

import android.app.Activity
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.stride.R
import com.example.stride.utility.composeUtility.CompletePreviews
import com.example.stride.utility.composeUtility.OrientationPreviews
import androidx.compose.ui.window.Dialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.ImeAction
import com.example.stride.utility.composeUtility.sdp
import com.example.stride.utility.theme.textStyleInter12Lh18Fw500
import com.example.stride.utility.theme.textStyleInter16Lh18Fw700
import com.example.stride.utility.theme.textStyleInter16Lh24Fw400
import com.example.stride.utility.theme.textStyleInter24Lh28Fw600

@Composable
fun GetStartedScreen(
    uiStates: GetStartedStates? = GetStartedStates(),
    onContinueClick: (String) -> Unit = {},
    onContinueWithGoogleClick:  () -> Unit = {},
    setEmail: (String) -> Unit = {},
    navController: NavHostController
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    if (uiStates?.isLoading == true) {
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator(color = colorResource(id = R.color.primary0))
        }
    }

    BackHandler {
        (navController.context as? Activity)?.finishAffinity()
    }
    BoxWithConstraints {
        val maxHeight = maxHeight
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.background_color))
                .verticalScroll(rememberScrollState())
                .padding(vertical = 50.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .width(screenWidth * 0.3f)
                    .height(80.dp)

            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Get Started",
                textAlign = TextAlign.Left,
                style = textStyleInter24Lh28Fw600(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = uiStates?.email ?: "",
                onValueChange = { setEmail(it) },
                label = {
                    Text(
                        "Email",
                        color = colorResource(id = R.color.font_500)
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.purple),
                    focusedLabelColor = colorResource(id = R.color.purple),
                    focusedTextColor = colorResource(id = R.color.white),
                    errorBorderColor = colorResource(id = R.color.error_color),
                    cursorColor = Color.White
                ),
                isError = uiStates?.isEmailValid == false,
                singleLine = true,
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
                    .height(maxHeight * 0.1f)
                    .padding(horizontal = 24.dp)

            )
            if (uiStates?.isEmailValid == false)
                Text(
                    text = uiStates.errorEmailMessage,
                    color = colorResource(id = R.color.error_color),
                    style = textStyleInter12Lh18Fw500(),
                    modifier = Modifier
                        .padding(start = 20.sdp, top = 8.sdp)
                        .align(Alignment.Start)
                        .padding(horizontal = 24.dp)

                )

            Spacer(modifier = Modifier.height(46.dp))

            Button(
                onClick = { onContinueClick(uiStates?.email ?: "") },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.primary0)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 24.dp)

            ) {
                Text(
                    text = "Continue",
                    color = colorResource(id = R.color.black),
                    style = textStyleInter16Lh18Fw700()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
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
                onClick = onContinueWithGoogleClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                border = BorderStroke(1.dp, colorResource(id = R.color.primary0)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 24.dp)

            ) {
                if (uiStates != null) {
                    if (uiStates.isLoading) {
                        CircularProgressIndicator(
                            color = colorResource(id = R.color.primary0),
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.icon_google),
                                contentDescription = "Google Logo",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Continue with Google",
                                color = colorResource(id = R.color.primary0),
                                style = textStyleInter16Lh18Fw700()
                            )
                        }
                    }
                }
            }
        }
    }
}
@OrientationPreviews
@CompletePreviews
@Composable
fun PreviewGetStartedScreen() {
    GetStartedScreen(
        uiStates = GetStartedStates(),
        onContinueClick = {},
        onContinueWithGoogleClick = {},
        navController = rememberNavController()
    )
}

