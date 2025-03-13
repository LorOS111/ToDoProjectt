package com.example.todoproject.project
import android.util.MutableBoolean
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import java.time.LocalDate
data class Task(
    val id: String,
    var title: String,
    var description: String?,
    var deadline: String,
    val dataOfCreate: String,
    var subtasks: MutableList<Subtask>? = mutableListOf(),
    var progress: Int?
)
data class Subtask(
    val id: String,
    val title: String,
    var isCompleted: Boolean,
    var deadline: String
)