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
    FitnessActivity("Water Intake", "💧", Color(0xFFCDB6EA), pro = true, fields = listOf(
        StatField("WATER", "3.2 L", primary = true),
        StatField("GOAL", "4.0 L"),
    )),
    FitnessActivity("Intermittent Fasting", "⏳", Color(0xFFF2C4A0), pro = true, fields = listOf(
        StatField("FASTED", "16 h 20 m", primary = true),
        StatField("FASTING TYPE", "16:8", options = listOf("16:8", "18:6", "20:4", "OMAD")),
    )),
    FitnessActivity("Red Light Therapy", "💡", Color(0xFFF0A8A8), pro = true, fields = listOf(
        StatField("DURATION", "20 min", primary = true),
        StatField("WAVELENGTH", "660 nm"),
    )),
    FitnessActivity("Pilates", "🤸", Color(0xFFF4C6D8), pro = true, fields = listOf(
        StatField("DURATION", "50 min", primary = true),
        StatField("CLASS TYPE", "Reformer", options = listOf("Reformer", "Mat", "Tower")),
    )),
    FitnessActivity("Contrast Therapy", "☯️", Color(0xFF8FDCD6), pro = true, fields = listOf(
        StatField("SAUNA TEMPERATURE", "85°C", primary = true),
        StatField("COLD PLUNGE TEMPERATURE", "10°C", primary = true),
        StatField("SAUNA DURATION", "15 min", primary = true),
        StatField("TOTAL TIME", "12 min", primary = true),
        StatField("CURRENT STREAK", "12 days"),
        StatField("FEELING", "Great", options = listOf("Great", "Good", "Okay", "Tough")),
        StatField("ROUNDS COMPLETED", "3"),
    )),
    FitnessActivity("Meditation", "🧘", Color(0xFFC3B8E8), pro = true, fields = listOf(
        StatField("DURATION", "20 min", primary = true),
        StatField("CURRENT STREAK", "9 days"),
    )),
    FitnessActivity("Yoga", "🪷", Color(0xFFB8E0A0), pro = true, fields = listOf(
        StatField("DURATION", "60 min", primary = true),
        StatField("STYLE", "Vinyasa", options = listOf("Vinyasa", "Hatha", "Yin", "Ashtanga")),
    )),
    FitnessActivity("Massage", "💆", Color(0xFFF0CBA8), pro = true, fields = listOf(
        StatField("DURATION", "60 min", primary = true),
        StatField("MASSAGE TYPE", "Deep Tissue", options = listOf("Deep Tissue", "Swedish", "Thai", "Sports")),
    )),
    FitnessActivity("Mobility", "🚶‍♂️", Color(0xFFA8BCE8), pro = true, fields = listOf(
        StatField("DURATION", "25 min", primary = true),
        StatField("FOCUS AREA", "Hips", options = listOf("Hips", "Shoulders", "Spine", "Ankles")),
    )),
    FitnessActivity("Breathwork", "🌬️", Color(0xFFA8E0C8), pro = true, fields = listOf(
        StatField("DURATION", "12 min", primary = true),
        StatField("METHOD", "Box", options = listOf("Box", "Wim Hof", "4-7-8")),
    )),
    FitnessActivity("Zumba", "🎵", Color(0xFFF29AC0), pro = true, fields = listOf(
        StatField("DURATION", "45 min", primary = true),
        StatField("CALORIES BURNED", "480 kcal"),
    )),
    FitnessActivity("Tennis", "🎾", Color(0xFFEEDD88), pro = true, fields = listOf(
        StatField("MATCH SCORE", "6-4, 7-5", primary = true),
        StatField("DURATION", "1 h 40 min"),
        StatField("MATCH TYPE", "Singles", options = listOf("Singles", "Doubles")),
    )),
    FitnessActivity("Padel", "🎾", Color(0xFF7FE0B0), pro = true, fields = listOf(
        StatField("MATCH SCORE", "6-3, 6-4", primary = true),
        StatField("DURATION", "1 h 10 min"),
        StatField("MATCH TYPE", "Doubles", options = listOf("Singles", "Doubles")),
        StatField("PARTNER", "Alex"),
        StatField("CALORIES BURNED", "410 kcal"),
        StatField("AVERAGE HEART RATE", "128 bpm"),
        StatField("PEAK HEART RATE", "155 bpm"),
        StatField("WINNERS", "6"),
    )),
    FitnessActivity("Skiing", "⛷️", Color(0xFFA0C0E0), pro = true, fields = listOf(
        StatField("TOP SPEED", "62 km/h", primary = true),
        StatField("RUNS", "14"),
        StatField("VERTICAL DROP", "3,200 m"),
    )),
    FitnessActivity("Rowing", "🚣", Color(0xFFE8B888), pro = true, fields = listOf(
        StatField("DISTANCE", "5,000 m", primary = true),
        StatField("DURATION", "22 min 40 s"),
        StatField("SPLIT", "2:16 /500m"),
    )),
    FitnessActivity("Swimming", "🏊", Color(0xFFA8D8E8), pro = true, fields = listOf(
        StatField("DISTANCE", "1,500 m", primary = true),
        StatField("DURATION", "38 min"),
        StatField("STROKE", "Freestyle", options = listOf("Freestyle", "Breaststroke", "Backstroke", "Butterfly")),
    )),
)

object Theme {
    val cream = Color(0xFFF4F0E6)
    val ink = Color(0xFF1A191C)
    val cardWhite = Color(0xFFFAF8F2)
}

/** Theme ink at the given opacity. */
fun ink(alpha: Float) = Theme.ink.copy(alpha = alpha)
