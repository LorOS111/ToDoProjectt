package com.example.todoproject.project

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoproject.maintext
import com.example.todoproject.ui.theme.CardColor
import com.example.todoproject.ui.theme.SpecialNonThisMotnhText
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale

@Composable
fun CalendarScreen() {
    var currentDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedDate by remember { mutableStateOf<LocalDate>(currentDate) }

    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
    val tasksMap = remember {
        mutableStateOf(
            listSubtasks.groupBy { LocalDate.parse(it.deadline, formatter) }
                .mapValues { entry -> entry.value }
        )
    }

    BackGround()
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 40.dp, start = 16.dp, end = 16.dp)
    ) {
        CalendarHeader(
            currentDate = currentDate,
            onPreviousMonth = { currentDate = currentDate.minusMonths(1) },
            onNextMonth = { currentDate = currentDate.plusMonths(1) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        CalendarView(
            currentDate = currentDate,
            selectedDate = selectedDate,
            onDaySelected = { selectedDate = it }
        )
        Spacer(modifier = Modifier.height(16.dp))

        val tasksForSelectedDate = tasksMap.value[selectedDate] ?: emptyList()
        LazyColumn {
            items(tasksForSelectedDate) { subtask ->
                ActivityTaskJ(subtask, 1)
            }
        }

    }
}



@Composable
fun CalendarHeader(currentDate: LocalDate, onPreviousMonth: () -> Unit, onNextMonth: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "<",
                modifier = Modifier
                    .clickable { onPreviousMonth() }
                    .padding(horizontal = 8.dp),
                fontSize = 24.sp,
                color = Color.White
            )
            maintext(30, 1, "${currentDate.month.getDisplayName(TextStyle.FULL, Locale.ENGLISH)} ${currentDate.year}", startpadding = 2)
            Text(
                text = ">",
                modifier = Modifier
                    .clickable { onNextMonth() }
                    .padding(horizontal = 8.dp),
                fontSize = 24.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun CalendarView(
    currentDate: LocalDate,
    selectedDate: LocalDate?,
    onDaySelected: (LocalDate) -> Unit
) {
    val daysInMonth = getDaysInMonth(currentDate)

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(daysInMonth.size) { index ->
            val day = daysInMonth[index]
            DayView(
                day = day,
                isCurrentMonth = day.month == currentDate.month,
                isSelected = day == selectedDate,
                onDayClick = { onDaySelected(day) }
            )
        }
    }
}

@Composable
fun DayView(day: LocalDate, isCurrentMonth: Boolean, isSelected: Boolean, onDayClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .padding(4.dp)
            .clickable { onDayClick() }
            .background(
                color = if (isSelected) CardColor else Color.Transparent,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.dayOfMonth.toString(),
            color = if (isCurrentMonth) Color.White else SpecialNonThisMotnhText,
            fontSize = 18.sp
        )
    }
}

@Composable
fun TaskList(tasks: List<String>) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(tasks.size) { index ->
            TaskItem(task = tasks[index])
        }
    }
}

@Composable
fun TaskItem(task: String) {
    var isChecked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.LightGray),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { isChecked = it }
        )
        Text(
            text = task,
            modifier = Modifier.padding(start = 8.dp),
            fontSize = 16.sp
        )
    }
}

fun getDaysInMonth(date: LocalDate): List<LocalDate> {
    val yearMonth = YearMonth.of(date.year, date.monthValue)
    val firstDayOfMonth = yearMonth.atDay(1)
    val lastDayOfMonth = yearMonth.atEndOfMonth()

    val days = mutableListOf<LocalDate>()
    var current = firstDayOfMonth.minusDays((firstDayOfMonth.dayOfWeek.value - 1).toLong())

    while (current <= lastDayOfMonth.plusDays(7 - lastDayOfMonth.dayOfWeek.value % 7L)) {
        days.add(current)
        current = current.plusDays(1)
    }
    return days
}