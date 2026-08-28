package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val AjrakDarkColorScheme =
  darkColorScheme(
    primary = AjrakMaroonDark,
    onPrimary = AjrakDarkMaroon,
    primaryContainer = AjrakLightMaroon,
    onPrimaryContainer = Color.White,
    secondary = AjrakIndigoDark,
    onSecondary = AjrakDeepBlue,
    secondaryContainer = AjrakLightIndigo,
    onSecondaryContainer = Color.White,
    tertiary = AjrakLightGold,
    onTertiary = AjrakDarkMaroon,
    tertiaryContainer = AjrakGold,
    onTertiaryContainer = Color.Black,
    background = AjrakSurfaceDark,
    onBackground = Color(0xFFEDE0DE),
    surface = AjrakCardSurfaceDark,
    onSurface = Color(0xFFEDE0DE),
    surfaceVariant = AjrakSurfaceVariantDark,
    onSurfaceVariant = Color(0xFFD8C2C0),
  )

private val AjrakLightColorScheme =
  lightColorScheme(
    primary = AjrakMaroon,
    onPrimary = Color.White,
    primaryContainer = AjrakMaroonContainer,
    onPrimaryContainer = AjrakOnMaroonContainer,
    secondary = AjrakIndigo,
    onSecondary = Color.White,
    secondaryContainer = AjrakIndigoContainer,
    onSecondaryContainer = AjrakOnIndigoContainer,
    tertiary = AjrakGold,
    onTertiary = Color.Black,
    tertiaryContainer = AjrakGoldContainer,
    onTertiaryContainer = Color(0xFF422B00),
    background = AjrakSand,
    onBackground = AjrakOnSurface,
    surface = AjrakCardSurface,
    onSurface = AjrakOnSurface,
    surfaceVariant = AjrakSurfaceVariant,
    onSurfaceVariant = AjrakOnSurfaceVariant,
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Keep authentic Ajrak branding
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> AjrakDarkColorScheme
      else -> AjrakLightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}

