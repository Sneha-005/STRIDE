package com.example.stride.presentation.dashboard.diet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.stride.R
import com.example.stride.utility.composeUtility.CompletePreviews
import com.example.stride.utility.composeUtility.sdp
import com.example.stride.utility.theme.textStyleInter16Lh24Fw400
import com.example.stride.utility.theme.textStyleInter16Lh24Fw600

@Composable
fun LogFoodScreen(
    setMealType: (String) -> Unit = {},
    setFoodName: (String) -> Unit = {},
    setQuantity: (Int) -> Unit = {},
){
    var mealType by remember { mutableStateOf("") }
    var foodName by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }

    val isFormValid = mealType.isNotEmpty() &&
            quantity.toIntOrNull()?.let { it > 0 } == true &&
            foodName.isNotEmpty()

    var expanded by remember { mutableStateOf(false) }
    var selectedGender by remember { mutableStateOf("") }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val screenHeight = maxHeight
        val screenWidth = maxWidth

        val isPortrait = screenHeight > screenWidth

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = (screenWidth * 0.05f)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colorResource(id = R.color.topBar))
                    .padding(horizontal = 24.dp,
                        vertical = 12.dp)
            ) {
                IconButton (
                    onClick = {}
                ){
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Back"
                    )
                }
                Text(
                    text = "Log Food",
                    style = textStyleInter16Lh24Fw600()
                    )
            }
            Spacer(modifier = Modifier.height(if (isPortrait) 16.sdp else 8.sdp))

            OutlinedTextField(
                value = selectedGender,
                label = {
                    Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Meal Type",
                            color = colorResource(id = R.color.font_500),
                            textAlign = TextAlign.Center
                        )
                    }
                },
                shape = MaterialTheme.shapes.medium,
                textStyle = textStyleInter16Lh24Fw400().copy(textAlign = TextAlign.Start),
                onValueChange = { },
                readOnly = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.purple),
                ),
                trailingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight * 0.12f)
            )
            DropdownMenu(
                modifier = Modifier.fillMaxWidth().background(color = colorResource(id = R.color.background_color)),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    onClick = {
                        selectedGender = "Breakfast"
                        setMealType(selectedGender)
                        expanded = false
                    },
                    text = {
                        Text(text = "Breakfast", color = colorResource(id = R.color.white), style = textStyleInter16Lh24Fw400())
                    }
                )
                DropdownMenuItem(
                    onClick = {
                        selectedGender = "Lunch"
                        setMealType(selectedGender)
                        expanded = false
                    },
                    text = {
                        Text(text = "Lunch", color = colorResource(id = R.color.white), style = textStyleInter16Lh24Fw400())
                    }
                )
                DropdownMenuItem(
                    onClick = {
                        selectedGender = "Snacks"
                        setMealType(selectedGender)
                        expanded = false
                    },
                    text = {
                        Text(text = "Snacks", color = colorResource(id = R.color.white), style = textStyleInter16Lh24Fw400())
                    }
                )
                DropdownMenuItem(
                    onClick = {
                        selectedGender = "Dinner"
                        setMealType(selectedGender)
                        expanded = false
                    },
                    text = {
                        Text(text = "Dinner", color = colorResource(id = R.color.white), style = textStyleInter16Lh24Fw400())
                    }
                )
            }
            OutlinedTextField(
                value = foodName,
                onValueChange = {
                    foodName = it
                    setFoodName(foodName)
                },
                label = { Text(text = "Food Name", color = colorResource(id = R.color.font_500)) },
                shape = MaterialTheme.shapes.medium,
                singleLine = true,
                textStyle = textStyleInter16Lh24Fw400().copy(textAlign = TextAlign.Start),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.purple),
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight * 0.12f)
            )
            OutlinedTextField(
                value = quantity,
                onValueChange = {
                    quantity = it
                    val qValue = it.toIntOrNull() ?: 0
                    setQuantity(qValue)
                },
                label = { Text(text = "Quantity", color = colorResource(id = R.color.font_500)) },
                shape = MaterialTheme.shapes.medium,
                singleLine = true,
                textStyle = textStyleInter16Lh24Fw400().copy(textAlign = TextAlign.Start),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.purple),
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight * 0.12f)
            )
            Button(
                onClick = {
                    if (isFormValid) {

                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.primary0)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height((screenHeight * 0.09f).coerceAtMost(48.dp))
            ) {
                Text(
                    text = "Log Food",
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
@CompletePreviews
fun LogFoodScreenPreview(){
    LogFoodScreen()
}