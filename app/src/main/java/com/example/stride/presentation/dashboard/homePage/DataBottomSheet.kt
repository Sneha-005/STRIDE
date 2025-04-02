package com.example.stride.presentation.dashboard.homePage

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stride.R
import com.example.stride.utility.composeUtility.CircularProgressBar
import com.example.stride.utility.composeUtility.CompletePreviews
import com.example.stride.utility.theme.textStyleInter16Lh24Fw600
import com.example.stride.utility.theme.textStyleInter16Lh24Fw700
import com.example.stride.utility.theme.textStyleInter24Lh28Fw600
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataBottomSheet(
    image: Int,
    title: String,
    value: Float,
    buttonText: String,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    viewModel: HomeScreenViewModel
) {
    val scope = rememberCoroutineScope()
    val uiStates by viewModel.uiStates.collectAsState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp)
                .background(colorResource(id = R.color.sheet_color)),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = image),
                contentDescription = "Walk Icon",
                tint = Color(0xFF9B5DF4),
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(color = colorResource(id = R.color.purple))
                    .padding(12.dp)
            )
            Text(text = title, style = textStyleInter24Lh28Fw600())
            Text(
                text = "$value kCal",
                style = textStyleInter16Lh24Fw600(),
                color = colorResource(id = R.color.font_500)
            )

            var selectedTab by remember { mutableStateOf("Daily") }

            Tab(selectedTab = selectedTab, onTabSelected = { selectedTab = it })
            if (title != "Water Intake") {
                CircularProgressBar(
                    progress = uiStates.caloriesConsumed.toString(),
                    maxProgress = 2000f,
                    size = 100.dp,
                    cal = true
                )
            }else{
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.DarkGray)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(value)
                            .height(12.dp)
                            .background(colorResource(id = R.color.purple))
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(34.dp, Alignment.Top),
                modifier = Modifier
                    .requiredWidth(width = 312.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(7.dp, Alignment.Top),
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    if (selectedTab == "Daily") {
                        Image(
                            painter = painterResource(id = R.drawable.walk),
                            contentDescription = "lucid:edit",
                            modifier = Modifier
                                .requiredSize(size = 24.dp)
                        )
                    }
                    when (title) {
                        "Steps" -> {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .requiredHeight(height = 97.dp)
                                    .clip(shape = RoundedCornerShape(16.dp))
                                    .border(
                                        border = BorderStroke(1.dp, Color(0xff909091)),
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .padding(
                                        horizontal = 15.dp,
                                        vertical = 7.dp
                                    )
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(
                                        10.dp,
                                        Alignment.CenterVertically
                                    ),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Current Steps Taken",
                                        style = textStyleInter16Lh24Fw600()
                                    )
                                    Text(
                                        text = "$value Steps",
                                        style = textStyleInter16Lh24Fw600()
                                    )
                                }
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(
                                        10.dp,
                                        Alignment.CenterVertically
                                    ),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Daily Goal",
                                        style = textStyleInter16Lh24Fw600()

                                    )
                                    Text(
                                        text = "${uiStates.stepGoal} Steps",
                                        style = textStyleInter16Lh24Fw600()

                                    )
                                }
                            }
                        }
                        "Water Intake" -> {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .requiredHeight(height = 97.dp)
                                    .clip(shape = RoundedCornerShape(16.dp))
                                    .border(
                                        border = BorderStroke(1.dp, Color(0xff909091)),
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .padding(
                                        horizontal = 15.dp,
                                        vertical = 7.dp
                                    )
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(
                                        10.dp,
                                        Alignment.CenterVertically
                                    ),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Water Intake",
                                        style = textStyleInter16Lh24Fw600()
                                    )
                                    Text(
                                        text = "$value ml",
                                        style = textStyleInter16Lh24Fw600()
                                    )
                                }
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(
                                        10.dp,
                                        Alignment.CenterVertically
                                    ),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Daily Goal",
                                        style = textStyleInter16Lh24Fw600()

                                    )
                                    Text(
                                        text = "${uiStates.waterGoal} ml",
                                        style = textStyleInter16Lh24Fw600()

                                    )
                                }
                            }
                            OutlinedTextField(
                                value = "",
                                onValueChange = {},
                                label = { Text("Add Amount") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                trailingIcon = {
                                    AddAmountCard()
                                }
                            )
                        }
                        else -> {
                            LaunchedEffect(selectedTab) {
                                when (selectedTab) {
                                    "Daily" -> {
                                        viewModel.getCalorieConsumed(0)
                                        viewModel.getCalorieBurnt(0)
                                    }

                                    "Weekly" -> {
                                        viewModel.getCalorieConsumed(1)
                                        viewModel.getCalorieBurnt(1)
                                    }

                                    "Monthly" -> {
                                        viewModel.getCalorieConsumed(2)
                                        viewModel.getCalorieBurnt(2)
                                    }
                                }
                            }
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .requiredHeight(height = 97.dp)
                                    .clip(shape = RoundedCornerShape(16.dp))
                                    .border(
                                        border = BorderStroke(1.dp, Color(0xff909091)),
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .padding(
                                        horizontal = 15.dp,
                                        vertical = 7.dp
                                    )
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(
                                        10.dp,
                                        Alignment.CenterVertically
                                    ),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Goals",
                                        style = textStyleInter16Lh24Fw600()

                                    )
                                    Text(
                                        text = "${uiStates.calorieGoal} kcal",
                                        style = textStyleInter16Lh24Fw600()

                                    )
                                }
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(
                                        10.dp,
                                        Alignment.CenterVertically
                                    ),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Intake",
                                        style = textStyleInter16Lh24Fw600()

                                    )
                                    Text(
                                        text = "${uiStates.caloriesConsumed} kcal",
                                        style = textStyleInter16Lh24Fw600()

                                    )
                                }
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(
                                        10.dp,
                                        Alignment.CenterVertically
                                    ),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Burnt",
                                        style = textStyleInter16Lh24Fw600()

                                    )
                                    Text(
                                        text = "${uiStates.caloriesBurnt} kcal",
                                        style = textStyleInter16Lh24Fw600()

                                    )
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            if ( title != "Steps") {
                if (selectedTab == "Daily") {
                    Button(
                        onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    onDismiss()
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDAF156)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(text = buttonText, color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }
            Spacer(modifier = Modifier.height(26.dp))
        }
    }
}

@Composable
fun Tab(selectedTab: String, onTabSelected: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(horizontal = 20.dp)
            .clip(CircleShape)
            .background(colorResource(id = R.color.blue_back)),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        listOf("Daily", "Weekly", "Monthly").forEach { title ->
            TabItem(title, selectedTab, onTabSelected, Modifier.weight(1f))
        }
    }
}

@Composable
fun TabItem(title: String, selectedTab: String, onTabSelected: (String) -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clickable { onTabSelected(title) }
            .clip(CircleShape)
            .background(if (selectedTab == title) Color.White else Color.Transparent)
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = textStyleInter16Lh24Fw600(),
            color = if (selectedTab == title) Color.Black else Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun AddAmountCard() {
    var amount by remember { mutableIntStateOf(100) }
    Card(
        modifier = Modifier
            .width(124.dp)
            .height(33.dp)
            .clip(shape = RoundedCornerShape(16.dp))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .background(colorResource(id = R.color.purple))
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { amount += 100 }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_round_plus),
                    contentDescription = "Increase amount",
                    colorFilter = ColorFilter.tint(Color.Black),
                    modifier = Modifier.requiredSize(20.dp)
                )
            }
            Text(
                text = "$amount ml",
                color = Color(0xff2f2f32),
                textAlign = TextAlign.Center,
                style = textStyleInter16Lh24Fw700(),
            )

            IconButton(onClick = { if (amount > 0) amount -= 100 }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_round_minus),
                    contentDescription = "Decrease amount",
                    colorFilter = ColorFilter.tint(Color.Black),
                    modifier = Modifier.requiredSize(24.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@CompletePreviews
fun DataBottomSheetPreview() {
    val sheetState = rememberModalBottomSheetState()

    DataBottomSheet(
        image = R.drawable.walk,
        title = "Steps",
        value = 1000f,
        buttonText = "Add Steps",
        sheetState = sheetState,
        onDismiss = {},
        viewModel = hiltViewModel()
    )
}

