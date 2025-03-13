package com.example.todoproject.project

import android.annotation.SuppressLint
import android.content.Context
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.todoproject.R
import com.example.todoproject.maintext
import com.example.todoproject.ui.theme.ActivityWindow
import com.example.todoproject.ui.theme.BotNavigation
import com.example.todoproject.ui.theme.NonActivityWindow
import com.example.todoproject.ui.theme.customCardColors

@Composable
fun BotNavig(navController: NavController) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route
    Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.BottomCenter) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(BotNavigation)
                .padding(bottom = 15.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Первая иконка
            Image(
                painter = painterResource(id = R.drawable.icontask),
                contentDescription = "im1",
                colorFilter = ColorFilter.tint(
                    if (currentRoute == "tasks") ActivityWindow else NonActivityWindow
                ),
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        navController.navigate("tasks")
                    }
                    .padding(10.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.iconcalendar),
                contentDescription = "im2",
                colorFilter = ColorFilter.tint(
                    if (currentRoute == "calendar") ActivityWindow else NonActivityWindow
                ),
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        navController.navigate("Calendar")
                    }
                    .padding(10.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.icontime),
                contentDescription = "im3",
                colorFilter = ColorFilter.tint(
                    if (currentRoute == "time") ActivityWindow else NonActivityWindow
                ),
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        navController.navigate("time")
                    }
                    .padding(10.dp)
            )
        }
    }
}


