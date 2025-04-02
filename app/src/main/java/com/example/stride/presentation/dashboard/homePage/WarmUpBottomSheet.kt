package com.example.stride.presentation.dashboard.homePage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.stride.R
import com.example.stride.utility.theme.textStyleInter16Lh24Fw400
import com.example.stride.utility.theme.textStyleInter16Lh24Fw600
import com.example.stride.utility.theme.textStyleInter16Lh24Fw700
import com.example.stride.utility.theme.textStyleInter24Lh28Fw600
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WarmUpBottomSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    exercises: List<String>,
    duration: String,
    selectedImage: String,
    selectedName: String,
    onStartClick: () -> Unit,
    navHostController: NavHostController
) {
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = { onDismiss()},
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.sheet_color))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(selectedImage),
                    contentDescription = "Warm Up",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = selectedName,
                        style = textStyleInter24Lh28Fw600(),
                        color = Color.White
                    )
                    Text(
                        text = "${exercises.size} Exercises • ${duration} mins",
                        style = textStyleInter16Lh24Fw600(),
                        color = Color.Gray
                    )
                }
            }
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Exercises",
                    style = textStyleInter24Lh28Fw600(),
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                exercises.forEach { exercise ->
                    Text(
                        text = "• $exercise",
                        style = textStyleInter16Lh24Fw400(),
                        color = Color.White,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
            }
            Button(
                onClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onDismiss()
                            onStartClick()
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB2FF59)),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            ) {
                Text(
                    text = "Start Exercise",
                    color = Color.Black,
                    style = textStyleInter16Lh24Fw700()
                )
            }
        }
    }
}




