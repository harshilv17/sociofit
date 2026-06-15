package com.sociofit.app

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.abs

data class StatField(
    val label: String,
    val value: String,
    val primary: Boolean = false,
    val options: List<String>? = null,
)

data class FitnessActivity(
    val name: String,
    val emoji: String,
    val color: Color,
    val pro: Boolean = false,
    val fields: List<StatField>,
)

val ACTIVITIES = listOf(
    FitnessActivity("Cold Plunge", "❄️", Color(0xFFF7DF3A), fields = listOf(
        StatField("WATER TEMPERATURE", "4°C", primary = true),
        StatField("DURATION", "3 min 12 s"),
        StatField("RECOVERY SCORE", "92 / 100"),
        StatField("PLUNGE TYPE", "Ice Bath", options = listOf("Ice Bath", "Lake", "Ocean", "None")),
    )),
    FitnessActivity("Sauna", "♨️", Color(0xFF4FD24E), fields = listOf(
        StatField("TEMPERATURE", "85°C", primary = true),
        StatField("DURATION", "45 min"),
        StatField("SAUNA TYPE", "Traditional", options = listOf("Traditional", "Infrared", "Steam")),
        StatField("ROUNDS COMPLETED", "3"),
    )),
    FitnessActivity("Sleep", "🌙", Color(0xFF7DD3E8), fields = listOf(
        StatField("TIME ASLEEP", "7 h 42 m", primary = true),
        StatField("SLEEP SCORE", "88 / 100"),
        StatField("BEDTIME", "10:45 PM"),
        StatField("WAKE UP", "6:30 AM"),
    )),
    FitnessActivity("Steps", "🚶", Color(0xFFD549D5), fields = listOf(
        StatField("STEPS", "12,480", primary = true),
        StatField("DISTANCE", "9.2 km"),
        StatField("CALORIES BURNED", "410 kcal"),
    )),
    FitnessActivity("Workout", "🏋️", Color(0xFFE85B5B), fields = listOf(
        StatField("DURATION", "1 h 05 min", primary = true),
        StatField("WORKOUT TYPE", "Push", options = listOf("Push", "Pull", "Legs", "Full Body")),
        StatField("CALORIES BURNED", "520 kcal"),
        StatField("AVERAGE HEART RATE", "132 bpm"),
    )),
    FitnessActivity("Running", "🏃", Color(0xFFF5A04C), fields = listOf(
        StatField("DISTANCE", "10.0 km", primary = true),
        StatField("DURATION", "52 min 30 s"),
        StatField("PACE", "5:15 /km"),
        StatField("AVERAGE HEART RATE", "156 bpm"),
    )),
    FitnessActivity("Cycling", "🚴", Color(0xFFBCE55C), fields = listOf(
        StatField("DISTANCE", "32.4 km", primary = true),
        StatField("DURATION", "1 h 18 min"),
        StatField("AVERAGE SPEED", "24.9 km/h"),
        StatField("ELEVATION GAIN", "340 m"),
    )),
)

object Theme {
    val cream = Color(0xFFF4F0E6)
    val ink = Color(0xFF1A191C)
    val cardWhite = Color(0xFFFAF8F2)
}

/** Theme ink at the given opacity. */
fun ink(alpha: Float) = Theme.ink.copy(alpha = alpha)
