package com.example.stride.presentation.auth.splashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.example.stride.R
import com.example.stride.utility.composeUtility.sdp
import com.example.stride.utility.theme.textStyleInter16Lh28Fw500
import com.example.stride.utility.theme.textStyleInter16Lh28Fw600
import com.example.stride.utility.theme.textStyleInter16Lh28Fw700
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SplashScreen(
    onGetStartedClick:  () -> Unit
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_color))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.sdp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            HorizontalPager(
                count = 3,
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) { page ->
                SplashPageContent(page = page)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.sdp, bottom = 52.sdp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(3) { index ->
                    Dot(isSelected = pagerState.currentPage == index)
                    Spacer(modifier = Modifier.width(8.sdp))
                }
            }

            Button(
                onClick = {
                    if (pagerState.currentPage < 2) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        onGetStartedClick()
                    }
                },
                colors = ButtonDefaults.buttonColors(colorResource(id =  R.color.coral)),
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier
                    .height(48.sdp)
                    .width(350.sdp)

            ) {
                Text(
                    text = if (pagerState.currentPage == 2) "Get Started" else "Next",
                    style = textStyleInter16Lh28Fw700(),
                    color = Color.Black
                )
            }
        }
        if (pagerState.currentPage != 2) {
            Text(
                text = "Skip",
                style = textStyleInter16Lh28Fw700(),
                modifier = Modifier
                    .padding(top = 16.sdp, end = 30.sdp)
                    .align(Alignment.TopEnd)
                    .clickable {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(2)
                        }
                    }
            )
        }
    }
}

@Composable
fun SplashPageContent(page: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (page) {
            0 -> SplashContent(
                imageRes = R.drawable.splash_1,
                title = "Track Your Fitness Journey",
                description = "Monitor your workouts, steps, and\n progress—all in one place."
            )
            1 -> SplashContent(
                imageRes = R.drawable.splash_2,
                title = "Stay Motivated",
                description = "Set goals, earn achievements, and\n celebrate your wins daily."
            )
            2 -> SplashContent(
                imageRes = R.drawable.splash_3,
                title = "Achieve Your Best Self",
                description = "Get personalized insights and stay on track\n to reach your goals."
            )
        }
    }
}

@Composable
fun SplashContent(
    imageRes: Int,
    title: String,
    description: String
) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = title,
        modifier = Modifier
            .size(390.sdp)
    )
    Text(
        text = title,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = 40.sdp),
        style = textStyleInter16Lh28Fw600()
    )
    Text(
        text = description,
        textAlign = TextAlign.Center,
        style = textStyleInter16Lh28Fw500(),
        modifier = Modifier.padding(top = 26.sdp),
    )
}

@Composable
fun Dot(isSelected: Boolean) {
    Box(
        modifier = Modifier
            .height(8.sdp)
            .width(if (isSelected) 24.sdp else 8.sdp)
            .background(
                color = if (isSelected) colorResource(id =  R.color.coral) else Color.Gray,
                shape = CircleShape
            )
    )
}
