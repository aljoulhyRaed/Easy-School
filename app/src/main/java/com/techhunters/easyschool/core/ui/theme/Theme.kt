package com.techhunters.easyschool.core.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = black100,
    onPrimary = black0,
//    primaryContainer = Green30,
//    onPrimaryContainer = Green90,
    inversePrimary = Green40,
    /*secondary = DarkGreen80,
    onSecondary = DarkGreen20,
    secondaryContainer = DarkGreen30,
    onSecondaryContainer = DarkGreen90,
    tertiary = Violet80,
    onTertiary = Violet20,
    tertiaryContainer = Violet30,
    onTertiaryContainer = Violet90,*/
    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,
    background = black0,
    onBackground = black100,
    surface = black30,// كرتات
    onSurface = black100,//  لون الكتابة الي فوق
    inverseSurface = black100,   // مضهر مختلف الكرت
    inverseOnSurface = black30, // مضهر مختلف فوق الكرت
    /* surfaceVariant = GreenGrey30,
     onSurfaceVariant = GreenGrey80,*/
    outline = black46  // حواف التكست بوكس
)

private val LightColorScheme = lightColorScheme(
    primary = black0,
    onPrimary = black100,
//    primaryContainer = Green90,
//    onPrimaryContainer = Green10,
    inversePrimary = Green80,
    /*secondary = DarkGreen40,
    onSecondary = Color.White,
    secondaryContainer = DarkGreen90,
    onSecondaryContainer = DarkGreen10,
    tertiary = Violet40,
    onTertiary = Color.White,
    tertiaryContainer = Violet90,
    onTertiaryContainer = Violet10,*/
    error = Red40,
    onError = Color.White,
    errorContainer = Red90,
    onErrorContainer = Red10,
    background = black100,
    onBackground = black0,
    surface = black93,
    onSurface = black0,
    inverseSurface = black0,
    inverseOnSurface = black93,
    /* surfaceVariant = GreenGrey90,
     onSurfaceVariant = GreenGrey30,*/
    outline = black62

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

@Composable
fun EasySchoolTheme(
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
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}