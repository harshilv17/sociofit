package com.sociofit.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class Align { Left, Center, Right }

data class StickerConfig(
    val fields: List<StatField>,
    val background: Color,
    val statsBlack: Boolean = true,
    val alignment: Align = Align.Left,
    val showCoordinates: Boolean = true,
    val aspect: Float = 9f / 16f,
    val notes: String = "",
)

fun defaultConfig(activity: FitnessActivity) = StickerConfig(activity.fields, activity.color)

@Composable
fun StickerCard(activity: FitnessActivity, config: StickerConfig) {
    val fg = if (config.statsBlack) Theme.ink else Color.White
    val dim = { a: Float -> fg.copy(alpha = a) }
    val primaries = config.fields.filter { it.primary && it.value.isNotEmpty() }
    val secondaries = config.fields.filter { !it.primary && it.value.isNotEmpty() }
    val (align, textAlign) = when (config.alignment) {
        Align.Left -> Alignment.Start to TextAlign.Left
        Align.Center -> Alignment.CenterHorizontally to TextAlign.Center
        Align.Right -> Alignment.End to TextAlign.Right
    }
    val font = Store.stickerFont
    // Plain sans (Serene) shows numbers in monospace; other fonts use their own family.
    val numberFamily = if (font.family == FontFamily.SansSerif && font.weight == FontWeight.Normal) FontFamily.Monospace else font.family

    Column(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(22.dp)).background(Theme.cardWhite).padding(22.dp),
        horizontalAlignment = align,
    ) {
        Text(
            "${activity.emoji}  ${activity.name.uppercase()}",
            color = fg, textAlign = textAlign,
            fontFamily = FontFamily.Monospace, fontSize = 13.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp,
        )
        if (Store.city.isNotEmpty() || Store.country.isNotEmpty()) {
            Text(
                "${Store.flag} ${listOf(Store.city, Store.country).filter { it.isNotEmpty() }.joinToString(" · ")}",
                Modifier.padding(top = 4.dp),
                color = fg, textAlign = textAlign,
                fontFamily = FontFamily.Monospace, fontSize = 10.sp, fontWeight = FontWeight.SemiBold,
            )
        }
        val coords = Store.coordsText
        if (config.showCoordinates && coords != null) Meta(coords, dim(0.55f), textAlign)
        Meta(dateLine(), dim(0.55f), textAlign)

        Spacer(Modifier.height(22.dp))

        if (primaries.size > 1) {
            primaries.chunked(2).forEach { pair ->
                Row(Modifier.fillMaxWidth().padding(bottom = 14.dp)) {
                    pair.forEach { Primary(it, 26.sp, fg, dim(0.5f), numberFamily, align, Modifier.weight(1f)) }
                    if (pair.size == 1) Spacer(Modifier.weight(1f))
                }
            }
        } else {
            primaries.forEach { Primary(it, 40.sp, fg, dim(0.5f), numberFamily, align) }
        }

        Spacer(Modifier.height(22.dp))

        Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            secondaries.forEach { field ->
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                    Meta(field.label, dim(0.5f))
                    Text(field.value, color = fg, fontFamily = FontFamily.Monospace, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        if (config.notes.isNotEmpty()) {
            Text(
                config.notes, Modifier.padding(top = 10.dp),
                color = dim(0.7f), fontSize = 12.sp, fontFamily = font.family, fontWeight = font.weight,
            )
        }
    }
}

@Composable
private fun Primary(
    field: StatField,
    size: TextUnit,
    color: Color,
    labelColor: Color,
    family: FontFamily,
    align: Alignment.Horizontal,
    modifier: Modifier = Modifier,
) {
    Column(modifier, horizontalAlignment = align) {
        Text(field.value, maxLines = 1, color = color, fontSize = size, fontWeight = FontWeight.Bold, fontFamily = family)
        Meta(field.label, labelColor)
    }
}

@Composable
private fun Meta(text: String, color: Color, textAlign: TextAlign? = null) {
    Text(
        text, Modifier.padding(top = 3.dp),
        color = color, textAlign = textAlign,
        fontFamily = FontFamily.Monospace, fontSize = 9.sp, letterSpacing = 1.sp,
    )
}
