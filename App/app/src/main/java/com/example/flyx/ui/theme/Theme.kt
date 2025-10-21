package com.example.flyx.ui.theme

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

// Light theme color scheme for university club platform
private val LightColorScheme = lightColorScheme(
    primary = UniBlue,
    onPrimary = UniGray50,
    primaryContainer = UniBlueLight,
    onPrimaryContainer = UniGray900,
    
    secondary = UniPurple,
    onSecondary = UniGray50,
    secondaryContainer = UniPurple.copy(alpha = 0.1f),
    onSecondaryContainer = UniPurple,
    
    tertiary = UniGreen,
    onTertiary = UniGray50,
    tertiaryContainer = UniGreen.copy(alpha = 0.1f),
    onTertiaryContainer = UniGreen,
    
    error = ErrorRed,
    onError = UniGray50,
    errorContainer = ErrorRed.copy(alpha = 0.1f),
    onErrorContainer = ErrorRed,
    
    background = UniGray50,
    onBackground = UniGray900,
    surface = UniGray50,
    onSurface = UniGray900,
    surfaceVariant = UniGray100,
    onSurfaceVariant = UniGray700,
    
    outline = UniGray300,
    outlineVariant = UniGray200,
    
    scrim = UniGray900.copy(alpha = 0.5f),
    
    // Custom colors for university platform
    surfaceContainerHighest = UniGray200,
    surfaceContainerHigh = UniGray100,
    surfaceContainer = UniGray50,
    surfaceContainerLow = UniGray50,
    surfaceContainerLowest = UniGray50,
    
    inverseSurface = UniGray800,
    inverseOnSurface = UniGray100,
    inversePrimary = UniBlueLight
)

// Dark theme color scheme for university club platform
private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkBackground,
    primaryContainer = UniBlueDark,
    onPrimaryContainer = UniBlueLight,
    
    secondary = DarkSecondary,
    onSecondary = DarkBackground,
    secondaryContainer = UniPurple.copy(alpha = 0.2f),
    onSecondaryContainer = DarkSecondary,
    
    tertiary = UniGreen,
    onTertiary = DarkBackground,
    tertiaryContainer = UniGreen.copy(alpha = 0.2f),
    onTertiaryContainer = UniGreen,
    
    error = ErrorRed,
    onError = DarkBackground,
    errorContainer = ErrorRed.copy(alpha = 0.2f),
    onErrorContainer = ErrorRed,
    
    background = DarkBackground,
    onBackground = UniGray100,
    surface = DarkSurface,
    onSurface = UniGray100,
    surfaceVariant = UniGray700,
    onSurfaceVariant = UniGray300,
    
    outline = UniGray600,
    outlineVariant = UniGray700,
    
    scrim = UniGray900.copy(alpha = 0.5f),
    
    // Custom colors for dark theme
    surfaceContainerHighest = UniGray700,
    surfaceContainerHigh = UniGray800,
    surfaceContainer = DarkSurface,
    surfaceContainerLow = DarkSurface,
    surfaceContainerLowest = DarkBackground,
    
    inverseSurface = UniGray100,
    inverseOnSurface = UniGray800,
    inversePrimary = UniBlue
)

@Composable
fun FlyxTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Disabled to use our custom colors
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
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

// Additional theme composables for specific use cases
@Composable
fun UniConnectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    FlyxTheme(darkTheme = darkTheme, content = content)
}

// Theme extensions for easy access to custom colors
object UniConnectColors {
    val primary = UniBlue
    val secondary = UniPurple
    val tertiary = UniGreen
    val accent = UniOrange
    val success = SuccessGreen
    val warning = WarningYellow
    val error = ErrorRed
    val info = InfoBlue
    
    // Event category colors
    val academic = AcademicColor
    val cultural = CulturalColor
    val sports = SportsColor
    val social = SocialColor
    val tech = TechColor
    val workshop = WorkshopColor
    
    // Neutral colors
    val background = UniGray50
    val surface = UniGray100
    val textPrimary = UniGray900
    val textSecondary = UniGray600
    val textMuted = UniGray500
    val border = UniGray300
    val divider = UniGray200
}