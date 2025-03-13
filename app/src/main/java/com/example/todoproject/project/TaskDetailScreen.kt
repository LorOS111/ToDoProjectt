package com.example.todoproject.project
import android.adservices.adid.AdId
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Checkbox
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.buildSpannedString
import androidx.navigation.NavController
import com.example.todoproject.maintext
import com.example.todoproject.R
import com.example.todoproject.deleteTask
import com.example.todoproject.goActivity
import com.example.todoproject.goCompleted
import com.example.todoproject.ui.theme.Jost
import com.example.todoproject.ui.theme.NonActivityProcent
import com.example.todoproject.ui.theme.NonActivityText

@Composable
fun TaskDetailsScreen(taskId: String?, navController: NavController) {
    val context = LocalContext.current

    BackGround()
    var isComplited: Boolean
    val task :Task
    if (listActivityTasks.find { it.id == taskId } == null) {
        if (listCompletedTasks.find { it.id == taskId } == null){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            maintext(40, 1, "Задача не найдена")
        }
        return
        }
        else {
            task = listCompletedTasks.find { it.id == taskId }!!
            isComplited=true
        }
    }
    else {
        task = listActivityTasks.find { it.id == taskId }!!
        isComplited=false
    }

    Box(modifier = Modifier.padding(top = 50.dp, start = 7.dp, end = 7.dp)) {

        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = null,
                    modifier = Modifier
                        .size(18.dp)
                        .clickable {
                            navController.navigate("tasks")
                        }
                )
                Spacer(modifier = Modifier.width(10.dp))

                var title by remember { mutableStateOf(task.title) }

                Box(
                    modifier = Modifier.width(IntrinsicSize.Min)
                ) {
                    BasicTextField(
                        value = title,
                        onValueChange = {
                            if (it==""){
                                task.title="Задача"
                                title=it
                                return@BasicTextField
                            }
                            title=it
                            task.title=it

                        },
                        textStyle = TextStyle(
                            color = Color.White,
                            fontSize = 22.sp,
                            fontFamily = Jost
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                        ),
                        cursorBrush = SolidColor(Color.White),
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }



            }



            Spacer(Modifier.height(8.dp))
            Row {
                Box(
                    modifier = Modifier
                        .height(25.dp)
                        .fillMaxWidth(0.85f)
                        .background(Color.DarkGray, RoundedCornerShape(10.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(5.dp)
                            .fillMaxWidth((task.progress!! / 100).toFloat())
                            .background(Color.LightGray, RoundedCornerShape(8.dp))
                    )
                }
                Spacer(Modifier.weight(1f))
                Text(
                    text = "${task.progress}%",
                    color = NonActivityProcent,
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            Spacer(Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                maintext(30, 1, "Подзадачи")
                Spacer(Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.anotherplus),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            navController.navigate("createSubtask/$taskId")
                        }
                )
            }

            LazyColumn {
                if (task.subtasks.isNullOrEmpty()) {
                    items(1) {
                        maintext(18, 1, "У тебя нет подзадач")
                    }
                    return@LazyColumn
                } else {
                    items(1) {
                        task.subtasks!!.forEach { subtask ->
                            ActivityTaskJ(subtask, 2)
                        }
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 2.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.iconcalendar),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(7.dp))
                maintext(22, 1, "Дедлайн")
                Spacer(Modifier.weight(1f))
                maintext(22, 1, task.deadline)
            }
            Spacer(Modifier.height(5.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 2.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.description),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(7.dp))
                maintext(22, 1, "Описание")
                Spacer(Modifier.weight(1f))
                Text(text = "Изменить", color = Color.White, fontSize = 22.sp,
                    modifier = Modifier.clickable {
                        navController.navigate("editTask/$taskId")
                    })
            }
            Spacer(Modifier.height(2.dp))
            Log.d("myLog", "task.description:${task.description}")
            if (task.description == "" || task.description.isNullOrEmpty() || task.description == null || task.description== " ")
                maintext(22, 1, "Нет описания")
            else
                maintext(22, 1, task.description!!)
        }
    }
    if (task.progress==100 && !isComplited){
        Box(
            modifier = Modifier
                .padding(bottom = 70.dp, end = 15.dp)
                .fillMaxSize()// Отступы от края
        ) {
            Image(
                painter = painterResource(id = R.drawable.icontaskcomplited),
                contentDescription = "Bottom Right Image",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .align(Alignment.BottomEnd)
                    .clickable {
                        navController.navigate("tasks")
                        goCompleted(task)
                    }
            )
        }
    }
    if (task.progress==100 && isComplited){
        Box(
            modifier = Modifier
                .padding(bottom = 70.dp, end = 15.dp)
                .fillMaxSize()// Отступы от края
        ) {
            Image(
                painter = painterResource(id = R.drawable.timer),
                contentDescription = "Bottom Right Image",
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .align(Alignment.BottomEnd)
                    .clickable {
                        navController.navigate("tasks")
                        goActivity(task)
                    }
            )
        }

    }
    Box(
        modifier = Modifier
            .padding(bottom = 70.dp, end = 15.dp)
            .fillMaxSize()// Отступы от края
    ) {
        Image(
            painter = painterResource(id = R.drawable.delete),
            contentDescription = "Bottom Right Image",
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .align(Alignment.BottomStart)
                .clickable {
                    deleteTask(task)
                    saveData(context)
                    navController.navigate("tasks")

               })
    }
}

