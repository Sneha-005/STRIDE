package com.example.stride.presentation.dashboard.homePage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.stride.R
import com.example.stride.utility.composeUtility.CompletePreviews
import com.example.stride.utility.navigation.DietRouteScreen
import com.example.stride.utility.navigation.FitnessRouteScreen
import com.example.stride.utility.navigation.Graph
import com.example.stride.utility.theme.textStyleInter16Lh24Fw400
import com.example.stride.utility.theme.textStyleInter16Lh24Fw700
import com.example.stride.utility.theme.textStyleInter18Lh28Fw600
import com.example.stride.utility.theme.textStyleInter24Lh28Fw600
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    image: Int,
    title: String,
    description: String,
    count: String,
    buttonText: String,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    navController: NavController
) {
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(400.dp)
                .background(color = colorResource(id = R.color.background_color)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .requiredSize(size = 80.dp)
                    .clip(shape = CircleShape)
                    .background(color = colorResource(id = R.color.purple))
            ) {
                Image(painter = painterResource(image), contentDescription = null)
            }
            Text(text = title, style = textStyleInter24Lh28Fw600(), textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = count, style = textStyleInter18Lh28Fw600(), textAlign = TextAlign.Center)
            Text(text = description, style = textStyleInter16Lh24Fw400(), textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(16.dp))
            if (title != "Steps") {
                Button(onClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onDismiss()
                            when (title) {
                                "Calories" -> navController.navigate(DietRouteScreen.LogFoodScreen.route){
                                    popUpTo(Graph.MainGraph) { inclusive = true }
                                }
                                "Water Intake" -> {

                                }
                            }
                        }
                    }
                },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors( containerColor = colorResource(id = R.color.primary0))
                ) {
                    Text(text = buttonText, style = textStyleInter16Lh24Fw700(), textAlign = TextAlign.Center, color = colorResource(id = R.color.black))
                }
            }
            Spacer(modifier = Modifier.height(26.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@CompletePreviews
fun PreviewBottomSheet() {
    val sheetState = rememberModalBottomSheetState()
    BottomSheet(
        image = R.drawable.walk,
        title = "Calories",
        description = "Calories consumed today",
        count = "2000 kcal",
        buttonText = "Log Food",
        sheetState = sheetState,
        onDismiss = {},
        navController = rememberNavController()
    )
}
