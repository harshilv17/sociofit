package com.sociofit.app

import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

private val ASPECTS = linkedMapOf("9:16" to 9f / 16f, "3:4" to 3f / 4f, "1:1" to 1f)

@Composable
fun StickerEditorScreen(activity: FitnessActivity, onBack: () -> Unit, onSettings: () -> Unit, onPaywall: () -> Unit) {
    var config by remember(activity) { mutableStateOf(defaultConfig(activity)) }
    var bgIndex by remember(activity) { mutableIntStateOf(0) }

    // ponytail: first two swatches free, rest pro-locked
    val swatches = listOf(
        activity.color to false,
        mix(activity.color, 0.45f) to false,
        Color(0xFFCBE44B) to true,
        Color(0xFF8FE08F) to true,
        Color(0xFFEE7040) to true,
        Color(0xFF4A5FD0) to true,
        Theme.cream to true,
    )

    fun setField(label: String, value: String) {
        config = config.copy(fields = config.fields.map { if (it.label == label) it.copy(value = value) else it })
    }

    Column(Modifier.fillMaxSize().background(config.background)) {
        Row(
            Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CircleButton("←", onBack)
            Text("${activity.emoji} ${activity.name}", color = Theme.ink, fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
            CircleButton("⚙️", onSettings)
        }

        Column(
            Modifier.verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Box(Modifier.fillMaxWidth().background(config.background).padding(24.dp)) {
                Box(Modifier.fillMaxWidth().aspectRatio(config.aspect), contentAlignment = Alignment.Center) {
                    StickerCard(activity, config)
                }
            }

            Section("ASPECT RATIO") {
                val label = ASPECTS.entries.firstOrNull { it.value == config.aspect }?.key ?: "9:16"
                SegmentRow(ASPECTS.keys.toList(), label) { config = config.copy(aspect = ASPECTS.getValue(it)) }
            }
            Section("BACKGROUND") {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    swatches.forEachIndexed { i, (color, _) ->
                        Box(
                            Modifier.size(36.dp).clip(CircleShape).background(color)
                                .border(1.dp, Color.Black.copy(alpha = 0.1f), CircleShape)
                                .clickable {
                                    bgIndex = i
                                    config = config.copy(background = color)
                                },
                            contentAlignment = Alignment.Center,
                        ) { Text(if (bgIndex == i) "✓" else "", fontSize = 12.sp) }
                    }
                }
            }
            Section("STATS COLOR") {
                SegmentRow(listOf("White", "Black"), if (config.statsBlack) "Black" else "White") {
                    config = config.copy(statsBlack = it == "Black")
                }
            }
            Section("ALIGNMENT") {
                SegmentRow(Align.entries.map { it.name }, config.alignment.name) {
                    config = config.copy(alignment = Align.valueOf(it))
                }
            }
            Section("DETAILS") {
                Card {
                    config.fields.forEach { field ->
                        Column(Modifier.fillMaxWidth().divider().padding(14.dp)) {
                            val options = field.options
                            if (options != null) {
                                Text(field.label, color = ink(0.5f), fontSize = 15.sp)
                                Spacer(Modifier.height(8.dp))
                                SegmentRow(options, field.value) { setField(field.label, it) }
                            } else {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(field.label, color = ink(0.5f), fontSize = 15.sp)
                                    Field(
                                        field.value, { setField(field.label, it) },
                                        Modifier.weight(1f),
                                        style = TextStyle(color = Theme.ink, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.End),
                                    )
                                }
                            }
                        }
                    }
                }
                Hint("Clear any field to remove it from the sticker. The layout rebalances automatically.")
            }

        }
    }
}

@Composable
private fun Hint(text: String) {
    Text(text, Modifier.padding(top = 8.dp), color = ink(0.55f), fontSize = 12.sp)
}
