package com.example.stride.presentation.dashboard.profile

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.stride.R
import com.example.stride.presentation.dashboard.landingPage.*
import com.example.stride.utility.composeUtility.CompletePreviews
import com.example.stride.utility.composeUtility.OrientationPreviews
import com.example.stride.utility.composeUtility.sdp
import com.example.stride.utility.navigation.Graph
import com.example.stride.utility.navigation.ProfileRouteScreen
import com.example.stride.utility.navigation.TopBar
import com.example.stride.utility.theme.textStyleInter16Lh24Fw400

@Composable
fun UpdateProfileScreen(
    navController: NavHostController,
    viewModel: UpdateProfileScreenViewModel
) {
    BackHandler {
        val canNavigateBack = navController.previousBackStackEntry != null
        if (canNavigateBack) {
            navController.popBackStack()
        } else {
            navController.navigate(ProfileRouteScreen.ProfileScreen.route) {
                popUpTo(ProfileRouteScreen.ProfileScreen.route) { inclusive = false }
            }
        }
    }

    var name by remember { mutableStateOf("") }
    var age by remember { mutableIntStateOf(0) }
    var weight by remember { mutableFloatStateOf(0f) }
    var height by remember { mutableFloatStateOf(0f) }
    var gender by remember { mutableStateOf("") }
    var dietType by remember { mutableStateOf("") }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_color))
    ) {
        val screenWidth = maxWidth
        val screenHeight = maxHeight
        val isPortrait = screenHeight > screenWidth

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(top = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(if (isPortrait) 16.sdp else 8.sdp))

            Box(
                modifier = Modifier
                    .requiredWidth(65.dp)
                    .requiredHeight(68.dp)
            ) {
                Box(
                    modifier = Modifier
                        .requiredSize(80.dp)
                        .clip(CircleShape)
                        .background(colorResource(id = R.color.primary_coral))
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_google),
                    contentDescription = "Edit Profile",
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(x = 40.dp, y = 44.dp)
                        .clip(RoundedCornerShape(16.8.dp))
                        .padding(4.8.dp)
                )
            }

            Spacer(modifier = Modifier.height(if (isPortrait) 16.sdp else 8.sdp))

            NameTextField(height = if (isPortrait) screenHeight * 0.09f else 46.dp) { name = it }
            Spacer(modifier = Modifier.height(if (isPortrait) 8.sdp else 4.sdp))

            GenderDropdown(height = if (isPortrait) screenHeight * 0.09f else 46.dp) { gender = it }
            Spacer(modifier = Modifier.height(if (isPortrait) 8.sdp else 4.sdp))

            TextFieldWithHint(height = if (isPortrait) screenHeight * 0.09f else 46.dp) { age = it }
            Spacer(modifier = Modifier.height(if (isPortrait) 8.sdp else 4.sdp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                WeightInput(height = if (isPortrait) screenHeight * 0.09f else 46.dp, width = screenWidth / 2 - 25.dp) { weight = it }
                HeightInput(height = if (isPortrait) screenHeight * 0.09f else 46.dp, width = screenWidth / 2 - 25.dp) { height = it }
            }
            Spacer(modifier = Modifier.height(if (isPortrait) 8.sdp else 4.sdp))

            DietTypeDropDown(height = if (isPortrait) screenHeight * 0.09f else 46.dp) { dietType = it }

            Spacer(modifier = Modifier.height(if (isPortrait) 16.sdp else 8.sdp))

            val context = LocalContext.current

            Button(
                onClick = {
                    viewModel.updateUserProfile(context)
                },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.primary0)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height((screenHeight * 0.09f).coerceAtMost(48.dp))
            ) {
                Text(text = "Save", color = Color.Black)
            }


            Spacer(modifier = Modifier.height(if (isPortrait) 16.sdp else 8.sdp))
        }
    }
}

@Composable
fun NameTextField(height: Dp, setName: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            setName(it)
        },
        label = {
            Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
                Text(text = "Your Name", color = Color.White)
            }
        },
        shape = MaterialTheme.shapes.medium,
        textStyle = textStyleInter16Lh24Fw400().copy(textAlign = TextAlign.Start),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(id = R.color.purple),
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
    )
}

@OrientationPreviews
@CompletePreviews
@Composable
fun UpdateProfileScreenPreview() {
    UpdateProfileScreen(
        navController = rememberNavController(),
        viewModel = hiltViewModel()
    )
}
