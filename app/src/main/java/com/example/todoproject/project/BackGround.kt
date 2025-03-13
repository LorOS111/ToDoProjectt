package com.example.todoproject.project

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.todoproject.ui.theme.Background

@Composable
fun BackGround() {
    Box(modifier = Modifier.fillMaxSize().background(Background))
}