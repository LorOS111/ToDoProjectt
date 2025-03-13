package com.example.todoproject.project

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.todoproject.R
import com.example.todoproject.ui.theme.Jost
import kotlin.math.log

@Composable
fun edit(taskId: String? ,navController: NavController){
    BackGround()
    var task = listActivityTasks.find { it.id == taskId }
    Log.d("myLog", "task: $task, id: $taskId")
    if (task == null)
        return
    var title by remember { mutableStateOf(task.title) }
    var content by remember { mutableStateOf(task.description) }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 36.dp)) {
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = null,
                modifier = Modifier
                    .size(18.dp)
                    .clickable {
                        navController.navigate("task/$taskId")
                    }
            )
            Spacer(Modifier.width(7.dp))
            BasicTextField(
                value = title,
                onValueChange = { title = it
                    task.title = it},
                modifier = Modifier
                    .fillMaxWidth(),
                textStyle = TextStyle(
                    fontSize = 30.sp,
                    color = Color.White,
                    fontFamily = Jost
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                cursorBrush = SolidColor(Color.White)
            )
        }

        BasicTextField(
            value = content!!,
            onValueChange = {
                content = it
                task.description = it
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            textStyle = TextStyle(
                fontSize = 20.sp,
                fontFamily = Jost,
                color = Color.White

            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Default
            ),
            cursorBrush = SolidColor(Color.White)
        )
    }
}
