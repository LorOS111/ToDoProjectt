package com.example.todoproject.project

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier  // Правильный импорт Modifier
import androidx.compose.ui.res.painterResource
import com.example.todoproject.R

@Composable
fun CustomCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Box(
        modifier = Modifier.padding(10.dp)
    ) {
        if (!checked){
            Image(
                painter = painterResource(id = R.drawable.icontasknotcomplited),
                contentDescription = null,
                modifier = Modifier.size(24.dp).clickable { onCheckedChange(!checked) }
            )
            return
        }
        Image(
            painter = painterResource(id = R.drawable.icontaskcomplited),
            contentDescription = null,
            modifier = Modifier.size(24.dp).clickable { onCheckedChange(!checked) }
        )
    }
}
