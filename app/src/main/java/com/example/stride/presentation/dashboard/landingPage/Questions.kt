package com.example.stride.presentation.dashboard.landingPage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.stride.R
import com.example.stride.utility.composeUtility.CompletePreviews
import com.example.stride.utility.composeUtility.OrientationPreviews
import com.example.stride.utility.composeUtility.sdp
import com.example.stride.utility.theme.textStyleInter16Lh24Fw400
import com.example.stride.utility.theme.textStyleInter24Lh28Fw600

@Composable
fun Questions(
    uiStates: LandingStates? = LandingStates(),
    setGender: (String) -> Unit = {},
    setAge: (Int) -> Unit = {},
    setWeight: (Float) -> Unit = {},
    setHeight: (Float) -> Unit = {},
    setDietType: (String) -> Unit = {},
    navController: NavHostController,
    questionSuccess: () -> Unit = {}
) {
    var gender by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var dietType by remember { mutableStateOf("") }

    val isFormValid = gender.isNotEmpty() &&
            age.toIntOrNull()?.let { it > 0 } == true &&
            weight.toFloatOrNull()?.let { it > 0 } == true &&
            height.toFloatOrNull()?.let { it > 0 } == true &&
            dietType.isNotEmpty()

    println("Gender: $gender")
    println("Age: $age")
    println("Weight: $weight")
    println("Height: $height")
    println("isFormValid: $isFormValid")

    if (uiStates?.questionState == true) {
        questionSuccess()
    }

    if (uiStates?.isLoading == true) {
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator(color = colorResource(id = R.color.primary0))
        }
    }

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
                .padding(horizontal = (screenWidth * 0.05f)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(if (isPortrait) 16.sdp else 8.sdp))

            Text(
                text = "Set Your Fitness Profile",
                color = Color.White,
                fontSize = (if (isPortrait) 20.sp else 16.sp),
                style = textStyleInter24Lh28Fw600(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(if (isPortrait) 16.sdp else 8.sdp))

            GenderDropdown(height = screenHeight * 0.12f) { selectedGender ->
                gender = selectedGender
                setGender(selectedGender)
            }
            Spacer(modifier = Modifier.height(if (isPortrait) 8.sdp else 4.sdp))

            TextFieldWithHint(height = screenHeight * 0.12f) { selectedAge ->
                age = selectedAge.toString()
                setAge(selectedAge.toInt())
            }
            Spacer(modifier = Modifier.height(if (isPortrait) 8.sdp else 4.sdp))

            WeightInput(height = screenHeight * 0.09f, width = screenWidth) { selectedWeight ->
                weight = selectedWeight.toString()
                setWeight(selectedWeight)
            }
            Spacer(modifier = Modifier.height(if (isPortrait) 16.sdp else 8.sdp))

            HeightInput(height = screenHeight * 0.09f, width = screenWidth) { selectedHeight ->
                height = selectedHeight.toString()
                setHeight(selectedHeight)
            }
            Spacer(modifier = Modifier.height(if (isPortrait) 8.sdp else 4.sdp))

            DietTypeDropDown(height = screenHeight * 0.12f) { selectedDietType ->
                dietType = selectedDietType
                setDietType(selectedDietType)
            }
            Spacer(modifier = Modifier.height(if (isPortrait) 16.sdp else 8.sdp))

            Button(
                onClick = {
                    if (isFormValid)
                        navController.navigate("goal_selection_screen")
                },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.primary0)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height((screenHeight * 0.09f).coerceAtMost(48.dp))
            ) {
                Text(
                    text = "Next",
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(if (isPortrait) 16.sdp else 8.sdp))
        }
    }
}

@Composable
fun GenderDropdown(height: Dp, setGender: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedGender by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedGender,
            label = {
                Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
                    Text(text = "Gender", color = colorResource(id = R.color.font_500), textAlign = TextAlign.Center)
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
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color.White)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    selectedGender = "Male"
                    setGender(selectedGender)
                    expanded = false
                },
                text = {
                    Text(text = "Male")
                }
            )
            DropdownMenuItem(
                onClick = {
                    selectedGender = "Female"
                    setGender(selectedGender)
                    expanded = false
                },
                text = {
                    Text(text = "Female")
                }
            )
            DropdownMenuItem(
                onClick = {
                    selectedGender = "Other"
                    setGender(selectedGender)
                    expanded = false
                },
                text = {
                    Text(text = "Other")
                }
            )
        }
    }
}

@Composable
fun DietTypeDropDown(height: Dp, setDietType: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedDietType by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedDietType,
            label = {
                Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
                    Text(text = "Diet Type", color = colorResource(id = R.color.font_500), textAlign = TextAlign.Center)
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
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color.White)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    selectedDietType = "Veg"
                    setDietType(selectedDietType)
                    expanded = false
                },
                text = {
                    Text(text = "Veg")
                }
            )
            DropdownMenuItem(
                onClick = {
                    selectedDietType = "Vegan"
                    setDietType(selectedDietType)
                    expanded = false
                },
                text = {
                    Text(text = "Vegan")
                }
            )
            DropdownMenuItem(
                onClick = {
                    selectedDietType = "Non_Veg"
                    setDietType(selectedDietType)
                    expanded = false
                },
                text = {
                    Text(text = "Non-Veg")
                }
            )
        }
    }
}

@Composable
fun TextFieldWithHint(height: Dp, setAge: (Int) -> Unit) {
    var age by remember { mutableStateOf("") }

    OutlinedTextField(
        value = age,
        onValueChange = {
            age = it
            val ageValue = it.toIntOrNull() ?: 0
            setAge(ageValue)
        },
        label = { Text(text = "Age", color = Color.White) },
        shape = MaterialTheme.shapes.medium,
        singleLine = true,
        textStyle = textStyleInter16Lh24Fw400().copy(textAlign = TextAlign.Start),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(id = R.color.purple),
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
    )
}


@Composable
fun WeightInput(height: Dp,width: Dp, setWeight: (Float) -> Unit) {
    var weight by remember { mutableStateOf("") }

    OutlinedTextField(
        value = weight,
        onValueChange = {
            weight = it
            val weightValue = it.toFloatOrNull() ?: 0f
            setWeight(weightValue)
        },
        label = {
            Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
                Text(text = "Your Weight", color = Color.White)
            }
        },
        trailingIcon = { Text(text = "kg", color = Color.White) },
        shape = MaterialTheme.shapes.medium,
        singleLine = true,
        textStyle = textStyleInter16Lh24Fw400().copy(textAlign = TextAlign.Start),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier
            .width(width)
            .height(height),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(id = R.color.purple),
        )
    )
}


@Composable
fun HeightInput(height: Dp,width: Dp, setHeight: (Float) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedHeight by remember { mutableStateOf("cm") }
    var heightValue by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = heightValue,
            onValueChange = {
                heightValue = it
                val heightParsed = it.toFloatOrNull() ?: 0f
                setHeight(heightParsed)
                            },
            label = {
                Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
                    Text(text = "Your Height", color = Color.White)
                }
            },
            shape = MaterialTheme.shapes.medium,
            textStyle = textStyleInter16Lh24Fw400().copy(textAlign = TextAlign.Start),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.purple),
            ),
            trailingIcon = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedHeight,
                        color = Color.White,
                    )
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            },
            modifier = Modifier
                .width(width)
                .height(height)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    selectedHeight = "in"
                    expanded = false
                },
                text = {
                    Text(text = "in")
                }
            )
            DropdownMenuItem(
                onClick = {
                    selectedHeight = "ft"
                    expanded = false
                },
                text = {
                    Text(text = "ft")
                }
            )
            DropdownMenuItem(
                onClick = {
                    selectedHeight = "cm"
                    expanded = false
                },
                text = {
                    Text(text = "cm")
                }
            )
        }
    }
}
@OrientationPreviews
@CompletePreviews
@Composable
fun QuestionsPreview() {
    Questions(
        uiStates = LandingStates(),
        setGender = {},
        setAge = {},
        setWeight = {},
        setHeight = {},
        navController = rememberNavController(),
        questionSuccess = {}
    )
}