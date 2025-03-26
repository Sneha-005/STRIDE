package com.example.stride.presentation.dashboard.fitness

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.stride.R
import com.example.stride.utility.composeUtility.CompletePreviews
import com.example.stride.utility.navigation.FitnessRouteScreen
import com.example.stride.utility.navigation.Graph

@Composable
fun CategoryScreen(navHostController: NavHostController) {
    val context = LocalContext.current
    val activity = context as? Activity
    BackHandler {
        activity?.finish()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp)
    ) {
        Text(
            text = "Categories",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        CategoryItem(
            title = "Strength Training",
            subtitle = "Build Power & Gain Muscle",
            imageRes = R.drawable.category_1,
            onClick = {
                navHostController.navigate(FitnessRouteScreen.StrengthTrainingScreen.route){
                    popUpTo(FitnessRouteScreen.CategoryScreen.route) { inclusive = false }
                }
            }
        )

        CategoryItem(
            title = "Cardio",
            subtitle = "Boost Endurance & Burn Fat",
            imageRes = R.drawable.category_2,
            onClick = {
                navHostController.navigate(FitnessRouteScreen.CardioScreen.route){
                    popUpTo(FitnessRouteScreen.CategoryScreen.route) { inclusive = false }
                }
            }
        )

        CategoryItem(
            title = "Yoga & Meditation",
            subtitle = "Find Balance & Inner Peace",
            imageRes = R.drawable.category_3,
            onClick = {
                navHostController.navigate(FitnessRouteScreen.YogaScreen.route){
                    popUpTo(FitnessRouteScreen.CategoryScreen.route) { inclusive = false }
                }
            }
        )
    }
}

@Composable
fun CategoryItem(title: String, subtitle: String, imageRes: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(111.dp)
            .padding(bottom = 16.dp)
            .background(colorResource(id = R.color.darker_gray), shape = RoundedCornerShape(12.dp))
            .clickable {onClick()}
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.Bottom)
                    .padding(8.dp)
            ) {
                Text(text = title, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = subtitle, color = Color.Gray, fontSize = 12.sp)
            }
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(150.dp)
            )
        }
    }
}

@Composable
@CompletePreviews
fun CategoryScreenPreview() {
    CategoryScreen(navHostController = rememberNavController())
}
