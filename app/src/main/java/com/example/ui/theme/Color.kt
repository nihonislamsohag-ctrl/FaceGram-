package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// FaceGram Theme Palette
val FaceGramBlue = Color(0xFF0095F6)
val FaceGramBlueDark = Color(0xFF1877F2)
val FaceGramDarkBg = Color(0xFF0D111D)
val FaceGramDarkCard = Color(0xFF161E2E)
val FaceGramDarkInput = Color(0xFF1F293D)
val FaceGramDarkBorder = Color(0xFF2E3A52)

val FaceGramGradientColors = listOf(
    Color(0xFF833AB4),
    Color(0xFFC13584),
    Color(0xFFE1306C),
    Color(0xFFFD1D1D),
    Color(0xFFF77737),
    Color(0xFFFCAF45)
)

val FaceGramStoryBrush = Brush.linearGradient(FaceGramGradientColors)
val FaceGramLogoBrush = Brush.horizontalGradient(
    listOf(Color(0xFFE1306C), Color(0xFF833AB4), Color(0xFF405DE6))
)
