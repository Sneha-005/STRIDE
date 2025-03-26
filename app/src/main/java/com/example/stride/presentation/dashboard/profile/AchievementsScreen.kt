package com.example.stride.presentation.dashboard.profile

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.stride.R
import com.example.stride.presentation.dashboard.homePage.HomeScreenViewModel
import com.example.stride.utility.composeUtility.CompletePreviews
import com.example.stride.utility.navigation.Graph
import com.example.stride.utility.navigation.ProfileRouteScreen
import com.example.stride.utility.navigation.TopBar
import com.example.stride.utility.theme.textStyleInter14Lh20Fw500
import org.burnoutcrew.reorderable.*
import java.io.Serializable

data class Achievement(
    val imageRes: Int,
    val cost: Int,
    val unlocked: Boolean
) : Serializable

@Composable
fun AchievementsScreen(
    navController: NavHostController,
    viewModel: HomeScreenViewModel
) {
    BackHandler {
        val canNavigateBack = navController.previousBackStackEntry != null
        if (canNavigateBack) {
            navController.popBackStack()
        } else {
            navController.navigate(ProfileRouteScreen.ProfileScreen.route) {
                popUpTo(ProfileRouteScreen.ProfileScreen.route) { inclusive = true }
            }
        }
    }

    val uiState by viewModel.uiStates.collectAsState()

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val isWideScreen = screenWidth > 600.dp

    val achievements = remember {
        mutableStateOf(
            listOf(
                Achievement(R.drawable.badge_1, 20, true),
                Achievement(R.drawable.badge_2, 40, true),
                Achievement(R.drawable.badge_3, 80, true),
                Achievement(R.drawable.badge_4, 100, true),
                Achievement(R.drawable.badge_5, 150, false),
                Achievement(R.drawable.badge_6, 200, false),
                Achievement(R.drawable.badge_7, 250, false),
                Achievement(R.drawable.badge_8, 300, false),
                Achievement(R.drawable.badge_9, 350, false)
            )
        )
    }

    val reorderState = rememberReorderableLazyGridState(
        onMove = { from, to ->
            val updatedList = achievements.value.toMutableList()
            updatedList.add(to.index, updatedList.removeAt(from.index))
            achievements.value = updatedList
        }
    )

    val bestAvailableBadge = achievements.value
        .filter { it.unlocked }
        .maxByOrNull { it.cost }

    val nextBadge = achievements.value
        .firstOrNull { it.cost > (bestAvailableBadge?.cost ?: 0) }

    val progress = (uiState.coins?.toFloat() ?: 0f) / (nextBadge?.cost?.toFloat() ?: 1f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = progress,
                    color = Color(0xFF6A5ACD),
                    modifier = Modifier.size(80.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.walk),
                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = bestAvailableBadge?.imageRes ?: R.drawable.badge_1),
                    contentDescription = "Current Badge",
                    modifier = Modifier.size(30.dp)
                )
                Text(
                    text = "Goal Getter",
                    fontSize = if (isWideScreen) 18.sp else 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.more_coins),
                    contentDescription = "Coins",
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = uiState.coins?.toString() ?: "0",
                    style = textStyleInter14Lh20Fw500(),
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))

            nextBadge?.let {
                Text(
                    text = "Next Badge Cost: ${it.cost} coins",
                    style = textStyleInter14Lh20Fw500(),
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(if (isWideScreen) 4 else 3),
                modifier = Modifier
                    .fillMaxSize()
                    .reorderable(reorderState)
                    .detectReorderAfterLongPress(reorderState),
                state = reorderState.gridState
            ) {
                items(achievements.value, key = { it.imageRes }) { item ->
                    ReorderableItem(reorderState, key = item.imageRes) { isDragging ->
                        val isFaded = item.cost > (uiState.coins ?: 0)
                        Box(
                            modifier = Modifier
                                .padding(12.dp)
                                .aspectRatio(1f)
                                .background(if (isDragging) Color.LightGray else colorResource(id = R.color.darker_gray))
                                .size(84.dp)
                                .alpha(if (isFaded) 0.5f else 1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Image(
                                    painter = painterResource(id = item.imageRes),
                                    contentDescription = "Badge",
                                    modifier = Modifier
                                        .fillMaxSize(0.7f)
                                        .aspectRatio(1f)
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.coins),
                                        contentDescription = "Coins",
                                        modifier = Modifier
                                            .fillMaxSize(0.12f)
                                            .aspectRatio(1f)
                                    )
                                    Text(
                                        text = "${item.cost}",
                                        style = textStyleInter14Lh20Fw500(),
                                        modifier = Modifier.padding(start = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}