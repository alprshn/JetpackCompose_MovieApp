package com.example.movieapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    background = ThemeColors.DarkTheme.backgroundColor,
    surface = ThemeColors.DarkTheme.bottomBarColor,
    onPrimary = ThemeColors.DarkTheme.whiteColor,
    onSecondary = ThemeColors.DarkTheme.whiteColor,
    onTertiary = ThemeColors.DarkTheme.whiteColor,
    onBackground = ThemeColors.DarkTheme.searchTextColor,
    onSurface = ThemeColors.DarkTheme.bottomBarSelectedItemColor,
    primaryContainer = ThemeColors.DarkTheme.bottomBarUnSelectedItemColor,
    secondaryContainer = ThemeColors.DarkTheme.darkGreyColor,
    tertiaryContainer = ThemeColors.DarkTheme.starColor,
    surfaceTint = ThemeColors.DarkTheme.buttonColor,
    inverseSurface = ThemeColors.DarkTheme.colorBlack,
    inversePrimary = ThemeColors.DarkTheme.releaseDateColor
)

// Light Color Scheme
private val LightColorScheme = lightColorScheme(
    background = ThemeColors.LightTheme.backgroundColor,
    surface = ThemeColors.LightTheme.bottomBarColor,
    onPrimary = ThemeColors.LightTheme.whiteColor,
    onSecondary = ThemeColors.LightTheme.whiteColor,
    onTertiary = ThemeColors.LightTheme.whiteColor,
    onBackground = ThemeColors.LightTheme.searchTextColor,
    onSurface = ThemeColors.LightTheme.bottomBarSelectedItemColor,
    primaryContainer = ThemeColors.LightTheme.bottomBarUnSelectedItemColor,
    secondaryContainer = ThemeColors.LightTheme.darkGreyColor,
    tertiaryContainer = ThemeColors.LightTheme.starColor,
    surfaceTint = ThemeColors.LightTheme.buttonColor,
    inverseSurface = ThemeColors.LightTheme.colorBlack,
    inversePrimary = ThemeColors.LightTheme.releaseDateColor
)

@Composable
fun MovieAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    content: @Composable () -> Unit
) {
    val colorScheme = if(darkTheme){
        DarkColorScheme
    }else{
        LightColorScheme
    }
    MaterialTheme(
      colorScheme = colorScheme,
      typography = Typography,
      content = content
    )
}