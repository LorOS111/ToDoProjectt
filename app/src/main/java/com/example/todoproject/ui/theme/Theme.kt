package com.example.todoproject.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)
object CardColorPalette {
    val Background = CardColor
    val BacgroundUn = NonActivityCard
}

@Composable
fun customCardColors(): CardColors {
    return CardDefaults.cardColors(
        containerColor = CardColorPalette.Background,
        disabledContainerColor = CardColorPalette.BacgroundUn
    )
}
object CardNonActivity {
    val Background = NonActivityCard
    val BacgroundUn = NonActivityCard
}

@Composable
fun CardNonActivity(): CardColors {
    return CardDefaults.cardColors(
        containerColor = CardNonActivity.Background,
        disabledContainerColor = CardColorPalette.BacgroundUn
    )
}
object CardInCard {
    val Background = BackgroundIndex
    val BacgroundUn = NonActivityCard
}

@Composable
fun cardindex(): CardColors {
    return CardDefaults.cardColors(
        containerColor = CardInCard.Background,
        disabledContainerColor = CardColorPalette.BacgroundUn
    )
}




@Composable
fun ToDoProjectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}