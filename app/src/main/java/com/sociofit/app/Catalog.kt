package com.sociofit.app

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.abs

object Theme {
    val cream = Color(0xFFF4F0E6)
    val ink = Color(0xFF1A191C)
    val cardWhite = Color(0xFFFAF8F2)
}

/** Theme ink at the given opacity. */
fun ink(alpha: Float) = Theme.ink.copy(alpha = alpha)
