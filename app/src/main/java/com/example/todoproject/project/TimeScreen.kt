package com.example.todoproject.project

import android.os.CountDownTimer
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoproject.ui.theme.Jost

@Composable
fun TimeScreen() {
    BackGround()
    var timeLeft by remember { mutableStateOf(25 * 60 * 1000L) } // 25 минут
    var isRunning by remember { mutableStateOf(false) }
    var timer: CountDownTimer? by remember { mutableStateOf(null) }

    // Запуск таймера
    fun startTimer() {
        isRunning = true
        timer = object : CountDownTimer(timeLeft, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
            }

            override fun onFinish() {
                isRunning = false
                timeLeft = 25 * 60 * 1000L // Сбросить время
            }
        }.start()
    }

    // Остановка таймера
    fun stopTimer() {
        timer?.cancel()
        isRunning = false
    }

    // Форматирование времени
    val minutes = (timeLeft / 1000) / 60
    val seconds = (timeLeft / 1000) % 60
    val timeFormatted = String.format("%02d:%02d", minutes, seconds)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = timeFormatted,
            fontSize = 48.sp,
            fontFamily = Jost,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(onClick = {
                if (!isRunning) startTimer()
            }) {
                Text(text = "Старт")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { stopTimer() }) {
                Text(text = "Стоп")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                stopTimer()
                timeLeft = 25 * 60 * 1000L // Сброс времени
            }) {
                Text(text = "Сброс")
            }
        }
    }
}
