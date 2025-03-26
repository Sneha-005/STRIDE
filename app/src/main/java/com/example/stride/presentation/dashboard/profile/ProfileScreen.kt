package com.example.stride.presentation.dashboard.profile

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.stride.R
import com.example.stride.presentation.dashboard.homePage.HomeScreenViewModel
import com.example.stride.utility.navigation.Graph
import com.example.stride.utility.navigation.ProfileRouteScreen
import com.example.stride.utility.theme.textStyleInter14Lh16Fw700
import com.example.stride.utility.theme.textStyleInter14Lh20Fw400
import com.example.stride.utility.theme.textStyleInter16Lh24Fw400
import com.example.stride.utility.theme.textStyleInter18Lh28Fw600

@Composable
fun ProfileScreen(
    navHostController: NavHostController,
    homeScreenViewModel: HomeScreenViewModel,
    updateProfileScreenViewModel: UpdateProfileScreenViewModel
){

    val context = LocalContext.current
    val activity = context as? Activity
    BackHandler {
        activity?.finish()
    }

    val uiState by homeScreenViewModel.uiStates.collectAsState()
    val configuration = LocalConfiguration.current
    val spacing = when {
        configuration.screenWidthDp >= 600 -> 16.dp
        else -> 10.dp
    }
    var showLogoutDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        homeScreenViewModel.getUserData()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background_color))
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.Top),
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Top),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(13.dp, Alignment.Start),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.icon_google),
                                contentDescription = "Ellipse",
                                modifier = Modifier
                                    .requiredSize(size = 50.dp))
                            Column(
                            ) {
                                Text(
                                    text = "${uiState.name}",
                                    color = colorResource(id = R.color.white),
                                    style = textStyleInter18Lh28Fw600())
                                Text(
                                    text = "Badge",
                                    color = colorResource(id = R.color.white),
                                    style = textStyleInter14Lh20Fw400())
                            }
                        }
                        Button(
                            onClick = {
                                navHostController.navigate(ProfileRouteScreen.UpdateProfileScreen.route){
                                    popUpTo(ProfileRouteScreen.ProfileScreen.route) { inclusive = false }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.primary0)),

                        ) {
                            Text(
                                text = "Edit",
                                color = colorResource(id = R.color.black),
                                style = textStyleInter14Lh16Fw700()
                            )

                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(spacing),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(color = Color(0xff333333))
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "${uiState.height}cm",
                                color = colorResource(id = R.color.light_purple),
                                style = textStyleInter16Lh24Fw400())
                            Text(
                                text = "Height",
                                color = colorResource(id = R.color.white),
                                style = textStyleInter16Lh24Fw400())
                        }
                        Column(
                            verticalArrangement = Arrangement.spacedBy(spacing),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(color = Color(0xff333333))
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                                Text(
                                    text = "${uiState.weight}kg",
                                    color = colorResource(id = R.color.light_purple),
                                    style = textStyleInter16Lh24Fw400())
                                Text(
                                    text = "Weight",
                                    color = colorResource(id = R.color.white),
                                    style = textStyleInter16Lh24Fw400())
                        }
                        Column(
                            verticalArrangement = Arrangement.spacedBy(spacing),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(color = Color(0xff333333))
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "${uiState.age}yo",
                                color = colorResource(id = R.color.light_purple),
                                style = textStyleInter16Lh24Fw400()
                            )
                            Text(
                                text = "Age",
                                color = colorResource(id = R.color.white),
                                style = textStyleInter16Lh24Fw400()
                            )

                        }
                    }
                }
            }
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(12.dp))
                        .background(color = Color(0xff333333))
                        .padding(all = 16.dp)
                ) {
                    Text(
                        text = "Account",
                        color = colorResource(id = R.color.white),
                        style = textStyleInter18Lh28Fw600()
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navHostController.navigate(ProfileRouteScreen.GoalUpdateScreen.route){
                                        popUpTo(ProfileRouteScreen.ProfileScreen.route) { inclusive = false }
                                    }
                                }

                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.mage_goals),
                                    contentDescription = "mage:goals",
                                    colorFilter = ColorFilter.tint(color = colorResource(id = R.color.light_purple)),
                                    modifier = Modifier
                                        .requiredSize(size = 20.dp))
                                Text(
                                    text = "Goals",
                                    color = colorResource(id = R.color.white),
                                    style = textStyleInter16Lh24Fw400())
                            }

                                IconlyLightArrowRight2()

                        }
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navHostController.navigate(ProfileRouteScreen.LevelUpdateScreen.route){
                                        popUpTo(ProfileRouteScreen.ProfileScreen.route) { inclusive = false }
                                    }
                                }
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(9.dp, Alignment.Start),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .requiredSize(size = 20.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.graph),
                                        contentDescription = "Iconly/Light/Document",
                                        tint = colorResource(id = R.color.light_purple),
                                        modifier = Modifier
                                            .requiredSize(size = 20.dp))
                                }
                                Text(
                                    text = "Activity Level",
                                    color = colorResource(id = R.color.white),
                                    style = textStyleInter16Lh24Fw400())
                            }
                            Box(
                                modifier = Modifier
                                    .requiredSize(size = 18.dp)
                            ) {
                                IconlyLightArrowRight2()
                            }
                        }
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navHostController.navigate(ProfileRouteScreen.AchievementsScreen.route){
                                        popUpTo(ProfileRouteScreen.ProfileScreen.route) { inclusive = false }
                                    }
                                }
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .requiredSize(size = 20.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_achievement),
                                        tint = colorResource(id = R.color.light_purple),
                                        contentDescription = "Iconly/Light/Graph",
                                        modifier = Modifier
                                            .requiredSize(size = 20.dp))
                                }
                                Text(
                                    text = "Achievement",
                                    color = colorResource(id = R.color.white),
                                    style = textStyleInter16Lh24Fw400())
                            }
                            Box(
                                modifier = Modifier
                                    .requiredSize(size = 18.dp)
                            ) {
                                IconlyLightArrowRight2()
                            }
                        }
                    }
                }
            }
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(12.dp))
                        .background(color = Color(0xff333333))
                        .padding(all = 16.dp)
                ) {
                    Text(
                        text = "Notification",
                        color = colorResource(id = R.color.white),
                        style = textStyleInter18Lh28Fw600()
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .requiredSize(size = 20.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.notification),
                                    tint = colorResource(id = R.color.light_purple),
                                    contentDescription = "Iconly/Light/Notification",
                                    modifier = Modifier
                                        .requiredSize(size = 20.dp))
                            }
                            Text(
                                text = "Pop-up Notification",
                                color = colorResource(id = R.color.white),
                                style = textStyleInter16Lh24Fw400()
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Switch(
                                checked = true,
                                onCheckedChange = { },
                                modifier = Modifier
                                    .requiredWidth(60.dp)
                                    .requiredHeight(20.dp),
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = colorResource(id = R.color.black),
                                    checkedTrackColor = colorResource(id = R.color.primary5),
                                    uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
                                    uncheckedTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                                )
                            )
                        }
                    }
                }
            }
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(12.dp))
                        .background(color = Color(0xff333333))
                        .padding(all = 16.dp)
                ) {
                    Text(
                        text = "Other",
                        color = colorResource(id = R.color.white),
                        style = textStyleInter18Lh28Fw600())
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .requiredSize(size = 20.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.message),
                                        tint = colorResource(id = R.color.light_purple),
                                        contentDescription = "Iconly/Light/Message",
                                        modifier = Modifier
                                            .requiredSize(size = 20.dp))
                                }
                                    Text(
                                        text = "Contact Us",
                                        color = colorResource(id = R.color.white),
                                        style = textStyleInter16Lh24Fw400())

                            }
                            Box(
                                modifier = Modifier
                                    .requiredSize(size = 18.dp)
                            ) {
                                IconlyLightArrowRight2()
                            }
                        }
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Box(
                                    modifier = Modifier
                                        .requiredSize(size = 20.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_privacy),
                                        tint = colorResource(id = R.color.light_purple),
                                        contentDescription = "Iconly/Light/Shield Done",
                                        modifier = Modifier
                                            .requiredSize(size = 20.dp))
                                }

                                    Text(
                                        text = "Privacy Policy",
                                        color = colorResource(id = R.color.white),
                                        style = textStyleInter16Lh24Fw400())
                            }
                            Box(
                                modifier = Modifier
                                    .requiredSize(size = 18.dp)
                            ) {
                                IconlyLightArrowRight2()
                            }
                        }
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    showLogoutDialog = true
                                }
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .requiredSize(size = 20.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.logout),
                                        tint = colorResource(id = R.color.light_purple),
                                        contentDescription = "Iconly/Light/Setting",
                                        modifier = Modifier
                                            .requiredSize(size = 20.dp))
                                }
                                    Text(
                                        text = "Log Out",
                                        color = colorResource(id = R.color.white),
                                        style = textStyleInter16Lh24Fw400())
                            }
                            Box(
                                modifier = Modifier
                                    .requiredSize(size = 18.dp)
                            ) {
                                IconlyLightArrowRight2()
                            }
                        }
                    }
                }
            }
        }
    }
    if (showLogoutDialog) {
        LogoutDialog(
            onDismiss = { showLogoutDialog = false },
            onConfirm = {
                showLogoutDialog = false
                updateProfileScreenViewModel.onConfirm(navHostController)
            }
        )
    }
}

@Composable
fun IconlyLightArrowRight2(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .requiredSize(size = 18.dp)

    ) {
        Image(
            painter = painterResource(id = R.drawable.arrow_right),
            contentDescription = "Iconly/Light/Arrow - Right 2",
            modifier = Modifier
                .fillMaxSize())
    }
}

