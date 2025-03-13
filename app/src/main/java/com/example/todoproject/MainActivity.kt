package com.example.todoproject

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoproject.project.BackGround
import com.example.todoproject.project.BotNavig
import com.example.todoproject.project.CalendarScreen
import com.example.todoproject.project.CreateTask
import com.example.todoproject.project.Task
import com.example.todoproject.project.TaskDetailsScreen
import com.example.todoproject.project.TaskScreen
import com.example.todoproject.project.TimeScreen
import com.example.todoproject.project.createSubtask
import com.example.todoproject.project.edit
import com.example.todoproject.project.listActivityTasks
import com.example.todoproject.project.listCompletedTasks
import com.example.todoproject.project.listSubtasks
import com.example.todoproject.project.listTodayTasks
import com.example.todoproject.project.loadData
import com.example.todoproject.project.saveData
import com.example.todoproject.ui.theme.Background
import com.example.todoproject.ui.theme.BotNavigation
import com.example.todoproject.ui.theme.Jost
import com.example.todoproject.ui.theme.NonActivityText
import com.example.todoproject.ui.theme.ToDoProjectTheme
import org.w3c.dom.Text
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        loadData(this)
        setContent {
            Navigation()

        }
    }

}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navigation(){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BotNavig(navController)}
    ) {
        NavHost(
            navController = navController,
            startDestination = "tasks"
        ) {
            composable("tasks") { TaskScreen(navController)}
            composable("calendar"){ CalendarScreen()}
            composable("time"){TimeScreen()}
            composable("createTask"){ CreateTask(navController) }
            composable("task/{taskId}"){ task ->
                val taskId = task.arguments?.getString("taskId")
                TaskDetailsScreen(taskId = taskId, navController)
            }
            composable("createSubtask/{taskId}"){ task ->
                val taskId = task.arguments?.getString("taskId")
                createSubtask(taskId = taskId, navController)
            }
            composable("editTask/{taskId}"){ task ->
                val taskId= task.arguments?.getString("taskId")
                edit(taskId = taskId, navController)
            }
        }
    }
}


@Composable
fun maintext(sp1: Int, activity: Int, text1: String, toppadding: Int = 0, endpadding: Int = 0, botpadding: Int = 0, startpadding: Int = 0){
    Text( text = "$text1",
        fontFamily = Jost,
        fontSize = sp1.sp,
        style = TextStyle(textDecoration = if (activity == 1) TextDecoration.None else  TextDecoration.LineThrough) ,
        color = if (activity == 1) Color.White else NonActivityText,
        modifier = Modifier.padding(
            top = toppadding.dp,
            end = endpadding.dp,
            bottom = botpadding.dp,
            start = startpadding.dp
        )
    )
}


fun goCompleted(task: Task){
    listActivityTasks.remove(task)
    listCompletedTasks.add(task)
}
fun goActivity(task: Task){
    listCompletedTasks.remove(task)
    listActivityTasks.add(task)
}
fun deleteTask(task: Task) {
    task.subtasks?.let { subtasks ->
        subtasks.forEach { subtask ->
            listSubtasks.remove(subtask)
            listTodayTasks.remove(subtask)
        }
    }
    if (listActivityTasks.contains(task)) {
        listActivityTasks.remove(task)
    } else {
        listCompletedTasks.remove(task)
    }
}
