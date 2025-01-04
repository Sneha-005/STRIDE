package com.example.stride.presentation.auth.splashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.example.stride.R
import com.example.stride.presentation.auth.Screen
import com.example.stride.utility.composeUtility.CompletePreviews
import com.example.stride.utility.composeUtility.OrientationPreviews
import com.example.stride.utility.theme.textStyleInter16Lh18Fw700
import com.example.stride.utility.theme.textStyleInter16Lh24Fw500
import com.example.stride.utility.theme.textStyleInter24Lh28Fw600
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SplashScreen(
    navHostController: NavHostController
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_color))
    ) {
        val screenWidth = maxWidth
        val screenHeight = maxHeight

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .padding(top = (screenHeight * 0.05f), bottom = (screenHeight * 0.1f))
                    .weight(0.5f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
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
                        SplashPageContent(page = page, screenWidth = screenWidth)
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = (screenHeight * 0.03f)),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(3) { index ->
                            Dot(
                                isSelected = pagerState.currentPage == index,
                                size = screenWidth * 0.02f
                            )
                            Spacer(modifier = Modifier.width(screenWidth * 0.02f))
                        }
                    }

                    Spacer(modifier = Modifier.height(screenHeight * 0.02f))

                    Button(
                        onClick = {
                            if (pagerState.currentPage < 2) {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            } else {
                                navHostController.navigate(Screen.GetStartedScreen.route)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.coral)),
                        shape = MaterialTheme.shapes.extraLarge,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .height(screenHeight * 0.07f)
                            .width(screenWidth)
                    ) {
                        Text(
                            text = if (pagerState.currentPage == 2) "Get Started" else "Next",
                            style = textStyleInter16Lh18Fw700(),
                            color = Color.Black
                        )
                    }
                }
                if (pagerState.currentPage != 2) {
                    Text(
                        text = "Skip",
                        style = textStyleInter16Lh18Fw700(),
                        modifier = Modifier
                            .padding(top = (screenHeight * 0.01f), end = (screenWidth * 0.05f))
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
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SplashScreenLandscape(
    navHostController: NavHostController
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_color))
    ) {
        val screenWidth = maxWidth
        val screenHeight = maxHeight

        Row(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .padding(top = (screenHeight * 0.05f), bottom = (screenHeight * 0.02f))
                    .weight(0.5f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {

                    HorizontalPager(
                        count = 3,
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(0.5f)
                    ) { page ->
                        SplashPageContent(page = page, screenWidth = screenWidth)
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(3) { index ->
                            Dot(
                                isSelected = pagerState.currentPage == index,
                                size = screenWidth * 0.02f
                            )
                            Spacer(modifier = Modifier.width(screenWidth * 0.01f))
                        }
                    }


                    Button(
                        onClick = {
                            if (pagerState.currentPage < 2) {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            } else {
                                navHostController.navigate(Screen.GetStartedScreen.route)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.coral)),
                        shape = MaterialTheme.shapes.extraLarge,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .height(screenHeight * 0.07f)
                            .width(screenWidth * 0.8f)
                    ) {
                        Text(
                            text = if (pagerState.currentPage == 2) "Get Started" else "Next",
                            style = textStyleInter16Lh18Fw700(),
                            color = Color.Black
                        )
                    }
                }

                if (pagerState.currentPage != 2) {
                    Text(
                        text = "Skip",
                        style = textStyleInter16Lh18Fw700(),
                        modifier = Modifier
                            .padding(top = (screenHeight * 0.01f), end = (screenWidth * 0.05f))
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
    }
}

@Composable
fun SplashPageContent(page: Int, screenWidth: androidx.compose.ui.unit.Dp) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (page) {
            0 -> SplashContent(
                imageRes = R.drawable.splash_1,
                title = "Track Your Fitness Journey",
                description = "Monitor your workouts, steps, and\n progress—all in one place.",
                imageSize = screenWidth
            )
            1 -> SplashContent(
                imageRes = R.drawable.splash_2,
                title = "Stay Motivated",
                description = "Set goals, earn achievements, and\n celebrate your wins daily.",
                imageSize = screenWidth
            )
            2 -> SplashContent(
                imageRes = R.drawable.splash_3,
                title = "Achieve Your Best Self",
                description = "Get personalized insights and stay on track\n to reach your goals.",
                imageSize = screenWidth
            )
        }
    }
}

@Composable
fun SplashContent(
    imageRes: Int,
    title: String,
    description: String,
    imageSize: androidx.compose.ui.unit.Dp
) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = title,
        modifier = Modifier.size(imageSize)
    )
    Text(
        text = title,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = 16.dp),
        style = textStyleInter24Lh28Fw600()
    )
    Text(
        text = description,
        textAlign = TextAlign.Center,
        style = textStyleInter16Lh24Fw500(),
        modifier = Modifier.padding(top = 8.dp)
    )
}

@Composable
fun Dot(isSelected: Boolean, size: androidx.compose.ui.unit.Dp) {
    Box(
        modifier = Modifier
            .height(size)
            .width(if (isSelected) size * 3 else size)
            .background(
                color = if (isSelected) colorResource(id = R.color.coral) else Color.Gray,
                shape = CircleShape
            )
    )
}

@Composable
@OrientationPreviews
fun PreviewSplashScreenLandscape() {
    SplashScreenLandscape(
        navHostController = rememberNavController()
    )
}
@CompletePreviews
@Composable
fun PreviewSplashScreen() {
    SplashScreen(
        navHostController = rememberNavController()
    )
}
