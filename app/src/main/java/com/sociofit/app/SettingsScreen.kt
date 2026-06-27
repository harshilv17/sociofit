package com.sociofit.app

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val ABOUT_LINKS = listOf(
    "Official Website" to "https://sociofit.app",
    "Follow us on Instagram" to "https://instagram.com/sociofit.app",
    "Subscribe to Newsletter" to "https://sociofit.app/newsletter",
    "Submit Feedback" to "mailto:hello@sociofit.app?subject=Feedback",
    "Restore Purchases" to null,
    "Share App" to "https://sociofit.app",
    "Rate App" to "https://play.google.com/store",
    "Terms of Use" to "https://sociofit.app/terms",
    "Privacy Policy" to "https://sociofit.app/privacy",
    "Contact" to "mailto:hello@sociofit.app",
)

@Composable
fun SettingsScreen(onBack: () -> Unit, onPaywall: () -> Unit) {
    val uriHandler = LocalUriHandler.current

    Column(Modifier.fillMaxSize()) {
        Box(Modifier.padding(start = 16.dp, top = 8.dp)) { CircleButton("←", onBack) }
        Column(
            Modifier.verticalScroll(rememberScrollState()).padding(start = 16.dp, end = 16.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Text("Settings", Modifier.padding(top = 8.dp), color = Theme.ink, fontFamily = FontFamily.Serif, fontSize = 34.sp)

            Section("MEMBERSHIP") {
                Card {
                    Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text(if (Store.isPro) "Pro Plan" else "Free Plan", color = Theme.ink, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                            Text(
                                if (Store.isPro) "All activities, templates & colors unlocked." else "Limited activities, templates & colors.",
                                Modifier.padding(top = 4.dp), color = ink(0.5f), fontSize = 13.sp,
                            )
                        }
                        if (!Store.isPro) {
                            Text(
                                "Upgrade",
                                Modifier.clip(RoundedCornerShape(22.dp)).background(Theme.ink).clickable(onClick = onPaywall)
                                    .padding(horizontal = 18.dp, vertical = 10.dp),
                                color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold,
                            )
                        }
                    }
                }
            }

            Section("LOCATION") {
                Card {
                    Text(
                        "Uses your device's GPS to auto-fill City, Country, and coordinates on your stickers. " +
                            "Enable Location Services if these show as blank.",
                        Modifier.padding(16.dp), color = ink(0.5f), fontSize = 14.sp,
                    )
                }
            }

            Section("FONT FOR STICKERS") {
                Card {
                    FONTS.forEachIndexed { i, font ->
                        val locked = font.pro && !Store.isPro
                        Row(
                            Modifier.fillMaxWidth().divider(i < FONTS.lastIndex)
                                .clickable { if (locked) onPaywall() else Store.stickerFont = font }
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                font.name,
                                color = if (locked) ink(0.4f) else Theme.ink,
                                fontSize = 18.sp, fontFamily = font.family, fontWeight = font.weight,
                            )
                            Text(if (locked) "🔒" else if (Store.stickerFont == font) "✓" else "", fontSize = 13.sp)
                        }
                    }
                }
            }

            Section("ABOUT") {
                Card {
                    ABOUT_LINKS.forEachIndexed { i, (label, url) ->
                        Row(
                            Modifier.fillMaxWidth().divider(i < ABOUT_LINKS.lastIndex)
                                .clickable { url?.let { runCatching { uriHandler.openUri(it) } } }
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(label, color = Theme.ink, fontSize = 16.sp)
                            Text("›", color = ink(0.4f))
                        }
                    }
                }
            }

            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("🇦🇪 Made in Dubai", color = ink(0.5f), fontSize = 13.sp)
                Text("Version 1.0.0", Modifier.padding(top = 4.dp), color = ink(0.5f), fontSize = 13.sp)
            }
        }
    }
}
