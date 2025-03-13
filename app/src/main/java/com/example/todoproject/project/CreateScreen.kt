package com.example.todoproject.project

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todoproject.R
import com.example.todoproject.maintext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.UUID

@Composable
fun CreateTask(navController: NavController) {
    val context = LocalContext.current
    BackGround()
    var title by remember { mutableStateOf("") }
    var titleError by remember { mutableStateOf(false) }
    var secondString by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var dateError by remember { mutableStateOf(false) }
    var isDatePickerOpen by remember { mutableStateOf(false) }

    if (isDatePickerOpen) {
        val currentDate = LocalDate.now()
        val datePickerDialog = android.app.DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                isDatePickerOpen = false
            },
            currentDate.year,
            currentDate.monthValue - 1,
            currentDate.dayOfMonth
        )
        LaunchedEffect(isDatePickerOpen) {
            datePickerDialog.show()
        }
    }
    Box(Modifier.padding(top=20.dp,)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = null,
                    Modifier.size(18.dp)
                        .clickable {
                            navController.navigate("tasks")
                        }
                )
                Spacer(Modifier.width(7.dp))
                maintext(22,1,"Cоздание задачи",)
                Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(Modifier.height(7.dp))
            TextField(
                value = title,
                onValueChange = {
                    title = it
                    titleError = false // Сбрасываем ошибку при изменении текста
                },
                label = { Text("Название задачи") },
                isError = titleError, // Устанавливаем ошибку для TextField
                modifier = Modifier.fillMaxWidth()
            )
            if (titleError) {
                Text(
                    text = "Title cannot be empty",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = secondString,
                onValueChange = { secondString = it },
                label = { Text("Описание (необязательно)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                maintext(
                    18, 1,
                    "Дата: ${selectedDate?.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")) ?: "Не выбрана"}"
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = {
                    isDatePickerOpen = true
                    dateError = false // Сбрасываем ошибку при выборе даты
                }) {
                    maintext(16, 1, "Выбрать дату")
                }
            }
            if (dateError) {
                Text(
                    text = "Date must be selected",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    var hasError = false

                    if (title.isBlank()) {
                        titleError = true
                        hasError = true
                    }

                    if (selectedDate == null) {
                        dateError = true
                        hasError = true
                    }

                    if (!hasError) {
                        val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("ru"))
                        addTask(
                            context, Task(
                                title = title,
                                dataOfCreate = LocalDate.now().toString(),
                                deadline = selectedDate!!.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")),
                                description = secondString,
                                subtasks = mutableListOf(),
                                id = UUID.randomUUID().toString(),
                                progress = (Progress(
                                    LocalDate.now(),
                                    LocalDate.parse(
                                        selectedDate?.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")),
                                        formatter
                                    )
                                ) * 100).toInt()
                            )
                        )
                        navController.navigate("tasks")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                maintext(18, 1, "Создать задачу")
            }
        }
    }
}
@Composable
fun createSubtask(taskId: String?, navController: NavController) {
    if (listActivityTasks.find { it.id==taskId } == null)
        return
    val task = listActivityTasks.find { it.id==taskId }
    val context = LocalContext.current
    BackGround()
    var title by remember { mutableStateOf("") }
    var titleError by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var dateError by remember { mutableStateOf(false) }
    var isDatePickerOpen by remember { mutableStateOf(false) }

    if (isDatePickerOpen) {
        val currentDate = LocalDate.now()
        val datePickerDialog = android.app.DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                isDatePickerOpen = false
            },
            currentDate.year,
            currentDate.monthValue - 1,
            currentDate.dayOfMonth
        )
        LaunchedEffect(isDatePickerOpen) {
            datePickerDialog.show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 36.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = null,
                Modifier.size(15.dp)
                    .clickable {
                        navController.navigate("task/$taskId")
                    }
            )
            Spacer(Modifier.width(7.dp))
            maintext(22,1,"Cоздание подзадачи",)
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(Modifier.height(7.dp))
        TextField(
            value = title,
            onValueChange = {
                title = it
                titleError = false // Сбрасываем ошибку при изменении текста
            },
            label = { Text("Название подзадачи") },
            isError = titleError, // Устанавливаем ошибку для TextField
            modifier = Modifier.fillMaxWidth()
        )
        if (titleError) {
            Text(
                text = "Title cannot be empty",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))



        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            maintext(
                18, 1,
                "Дата: ${selectedDate?.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")) ?: "Не выбрана"}"
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = {
                isDatePickerOpen = true
                dateError = false // Сбрасываем ошибку при выборе даты
            }) {
                maintext(16, 1, "Выбрать дату")
            }
        }
        if (dateError) {
            Text(
                text = "Date must be selected",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                var hasError = false

                if (title.isBlank()) {
                    titleError = true
                    hasError = true
                }

                if (selectedDate == null) {
                    dateError = true
                    hasError = true
                }

                if (!hasError) {
                    addSubtask(
                        context, Subtask(
                            id = UUID.randomUUID().toString(),
                            title = title,
                            isCompleted = false,
                            deadline = selectedDate!!.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
                        ), task!!
                    )
                navController.navigate("task/$taskId")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            maintext(18, 1, "Создать подзадачу")
        }
    }
}

