package com.example.todoproject.project

import android.content.Context
import androidx.core.i18n.DateTimeFormatter
import androidx.privacysandbox.tools.core.model.Type
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.google.gson.reflect.TypeToken
import java.io.File
import java.time.LocalDate

fun saveTasks(context: Context, tasks: List<Task>, filename: String) {
    val gson = Gson()
    val json = gson.toJson(tasks)
    val file = File(context.filesDir, filename)
    file.writeText(json)
}

fun loadTasks(context: Context, filename: String): List<Task> {
    val file = File(context.filesDir, filename)
    if (!file.exists()) return emptyList()

    val json = file.readText()
    val gson = Gson()
    val type = object : TypeToken<List<Task>>() {}.type
    return gson.fromJson(json, type)
}
fun loadData(context: Context) {
    // Загружаем задачи
    listActivityTasks.addAll(loadTasks(context, "activityTasks.json"))
    // Загружаем подзадачи
    listSubtasks.addAll(loadSubtasks(context, "subtasks.json"))
    // Загружаем задачи на сегодня
    listTodayTasks.addAll(loadSubtasks(context, "todayTasks.json"))
    // Загружаем выполненные задачи
    listCompletedTasks.addAll(loadTasks(context, "completedtasks.json"))
}

fun saveData(context: Context) {
    saveTasks(context, listActivityTasks, "activityTasks.json")
    saveSubtasks(context, listSubtasks, "subtasks.json")
    saveSubtasks(context, listTodayTasks, "todayTasks.json")
    saveTasks(context, listCompletedTasks, "completedtasks.json")
}
fun saveSubtasks(context: Context, subtasks: List<Subtask>, filename: String) {
    val gson = Gson()
    val json = gson.toJson(subtasks)
    val file = File(context.filesDir, filename)
    file.writeText(json)
}
fun loadSubtasks(context: Context, filename: String): List<Subtask> {
    val file = File(context.filesDir, filename)
    if (!file.exists()) return emptyList()

    val json = file.readText()
    val gson = Gson()
    val type = object : TypeToken<List<Subtask>>() {}.type
    return gson.fromJson(json, type)
}
