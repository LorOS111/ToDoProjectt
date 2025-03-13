package com.example.todoproject.project

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.runtime.mutableStateOf

import androidx.compose.ui.Alignment


import com.example.todoproject.R

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todoproject.maintext
import com.example.todoproject.ui.theme.CardNonActivity
import com.example.todoproject.ui.theme.Jost
import com.example.todoproject.ui.theme.NonActivityBackIndex
import com.example.todoproject.ui.theme.NonActivityCard
import com.example.todoproject.ui.theme.NonActivityText
import com.example.todoproject.ui.theme.customCardColors

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

var listActivityTasks = mutableListOf<Task>()
var listSubtasks =  mutableListOf<Subtask>()
var listTodayTasks = mutableListOf<Subtask>()
var listCompletedTasks = mutableListOf<Task>()

@Composable
fun TaskScreen(navController: NavController){
    BackGround()
    Box (modifier = Modifier.padding(top = 5.dp, start = 7.dp, end = 7.dp)) {
        LazyColumn(modifier = Modifier.padding(top= 45.dp)) {
          items(1) {
              ActivityTasks(listActivityTasks, navController)
              ActivityTasksJ(listTodayTasks)
              ComplitedTasks(listCompletedTasks, navController)
              Spacer(modifier = Modifier.height(75.dp))
          }
        }
    }
    plus(navController)
}
@Composable
fun ActivityTasks(listActivityTasks: List<Task>?, navController: NavController){
    maintext(30, 1, "Активные задачи")
    if (listActivityTasks.isNullOrEmpty()){
        maintext(25, 1, "У тебя нет активных задач")
        return
    }
    Column {
        listActivityTasks.forEach { task ->
            ActivityTask(task, navController)
        }
    }
}
@Composable
fun ActivityTask(task: Task, navController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding( vertical = 8.dp)
            .clickable {
                navController.navigate("task/${task.id}")
                Log.d("myLog", "task id: ${task.id}")
            },
        colors = customCardColors(),
        shape = RoundedCornerShape(8.dp),
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            maintext(20, 1, task.title)

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .height(25.dp)
                        .width(260.dp)
                        .background(Color.DarkGray, RoundedCornerShape(10.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(5.dp)
                            .fillMaxWidth((task.progress!!/100).toFloat())
                            .background(Color.LightGray, RoundedCornerShape(8.dp))
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Box {
                    maintext(18, 1, "${task.progress}%")
                }
            }
        }
    }
}
@SuppressLint("RememberReturnType")
@Composable
fun ActivityTasksJ(ActivityTasks: List<Subtask>) {
    val context = LocalContext.current
    maintext(30, 1, "Задачи на сегодня")
    //TODO хорошо бы запариться и сделать так, чтобы выполненная задача опускалась вниз, но пока и так сойдет

    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
    listSubtasks.forEach{ subtask: Subtask ->
        if (subtask.deadline == LocalDate.now().format(formatter)){
            if (!listTodayTasks.contains(subtask))
                 listTodayTasks.add(subtask)
        }
        else{ if(listTodayTasks.contains(subtask) && subtask.deadline == LocalDate.now().format(formatter)) {
            listTodayTasks.remove(subtask)
            saveData(context)
        }
        }
    }
    
    
    if (ActivityTasks.isNullOrEmpty()) {
        maintext(25, 1, "У тебя нет задач на сегодня")
        return
    }
    val sortedTasks = remember(ActivityTasks) {
        ActivityTasks.sortedBy { it.isCompleted }
    }

    Column {
        sortedTasks.forEach { task ->
            ActivityTaskJ(task, 1)
        }
    }
}


@SuppressLint("UnrememberedMutableState")
@Composable
fun ActivityTaskJ(subtask: Subtask, code: Int){
    var isCompleted = remember {   mutableStateOf(subtask.isCompleted) }
    if (!isCompleted.value) {
        Card(
            colors = customCardColors(),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
            .padding(vertical = 4.dp),

            ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CustomCheckbox(
                    checked = isCompleted.value,
                    onCheckedChange = { isCompleted.value= it
                    subtask.isCompleted = it}
                )
                maintext(22, 1, subtask.title)
                Spacer(Modifier.weight(1f))
                if (code==0) {
                    maintext(18, 1, subtask.deadline.toString())
                    Spacer(Modifier.width(5.dp))
                }
            }
        }
        saveData(context = LocalContext.current)
        return
    }
    Card(
        colors = CardNonActivity(),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
        .padding(vertical = 4.dp),

        ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            CustomCheckbox(
                checked = isCompleted.value,
                onCheckedChange = { isCompleted.value = it
                    subtask.isCompleted = it}
            )
            maintext(22, 0, subtask.title)
            if (code==0){
                Spacer(Modifier.weight(1f))
                maintext(18, 1, subtask.deadline)
                Spacer(Modifier.width(5.dp))
            }
        }
        saveData(context = LocalContext.current)
    }
}
@Composable
fun ComplitedTasks(ComplitedTask :List<Task>?, navController: NavController){
    maintext(30, 1, "Выполненные задачи")
    if (ComplitedTask.isNullOrEmpty()){
        maintext(25, 1, "У тебя нет выполненных задач")
        return
    }
    Column {
        ComplitedTask.forEach { task ->
            ComplitedTask(task, navController)
        }
    }
}
@Composable
fun ComplitedTask(task: Task, navController: NavController){
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding( vertical = 8.dp)
            .clickable {
                navController.navigate("task/${task.id}")
                Log.d("myLog", "task id: ${task.id}")
            },
        colors = CardNonActivity(),
        shape = RoundedCornerShape(8.dp),
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            maintext(20, 0, task.title)

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .height(25.dp)
                        .width(260.dp)
                        .background(NonActivityBackIndex, RoundedCornerShape(10.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(5.dp)
                            .fillMaxWidth()
                            .background(NonActivityCard, RoundedCornerShape(8.dp))
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(fontFamily = Jost, text = "100%", color = NonActivityText, fontSize = 18.sp)
            }
        }
    }
}
@Composable
fun plus(navController: NavController){
    Box(
        modifier = Modifier
            .padding(bottom = 70.dp, end = 15.dp)
            .fillMaxSize()// Отступы от края
    ) {
        Image(
            painter = painterResource(id = R.drawable.plus),
            contentDescription = "Bottom Right Image",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .align(Alignment.BottomEnd)
                .clickable {
                    navController.navigate("createTask")
                }
        )
    }
}
fun Progress(dataCreate: LocalDate, deadline: LocalDate): Float {
    val today = LocalDate.now()
    val totalDays = dataCreate.until(deadline, ChronoUnit.DAYS)

    if (totalDays == 0L) {
        return 1.0F // если дата создания и дедлайн совпадают, то задача завершена
    }

    val passedDays = dataCreate.until(today, ChronoUnit.DAYS)
    // Ограничиваем прогресс от 0 до 1
    return (passedDays.toFloat() / totalDays).coerceIn(0f, 1f)
}
fun addTask(context: Context, task: Task) {
    listActivityTasks.add(task)
    saveData(context)
}
fun addSubtask(context: Context, subtask: Subtask, task: Task){
    listSubtasks.add(subtask)
    task.subtasks!! .add(subtask)
    saveData(context)
}


