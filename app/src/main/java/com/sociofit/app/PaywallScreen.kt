package com.sociofit.app

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val PERKS = listOf(
    "Unlock 100+ sticker templates",
    "Unlock all designer fonts",
    "Unlock all colors",
    "Unlock all template styles",
    "Unlock 16 more activities",
    "Remove app credits",
)
private const val ANNUAL = "₹2,850.00/year"
private const val MONTHLY = "₹280.00/month"

@Composable
fun PaywallScreen(onClose: () -> Unit) {
    var annual by remember { mutableStateOf(true) }

    Column(Modifier.fillMaxSize().padding(24.dp)) {
        Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
            CircleButton("✕", onClose)
            Text("Skip", Modifier.clickable(onClick = onClose), color = ink(0.5f), fontSize = 16.sp)
        }

        Text(
            "Unlock Everything,\nCancel Anytime",
            Modifier.padding(vertical = 24.dp),
            color = Theme.ink,
            fontFamily = FontFamily.Serif,
            fontSize = 32.sp,
        )

        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            PERKS.forEach { perk ->
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Box(Modifier.size(22.dp).clip(CircleShape).background(Theme.ink), contentAlignment = Alignment.Center) {
                        Text("✓", color = Color.White, fontSize = 10.sp)
                    }
                    Text(perk, color = Theme.ink, fontSize = 16.sp)
                }
            }
        }

        Spacer(Modifier.weight(1f))

        Plan("Annual Plan", ANNUAL, annual) { annual = true }
        Spacer(Modifier.height(12.dp))
        Plan("Monthly Plan", MONTHLY, !annual) { annual = false }

        Spacer(Modifier.height(20.dp))
        // ponytail: fake purchase. Wire Google Play Billing here before release.
        PillButton("Continue – ${if (annual) ANNUAL else MONTHLY}", Theme.ink, Color.White) {
            Store.isPro = true
            onClose()
        }
        Text(
            "Restore Purchases",
            Modifier.fillMaxWidth().padding(top = 14.dp),
            color = ink(0.5f),
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun Plan(title: String, price: String, selected: Boolean, onClick: () -> Unit) {
    val shape = RoundedCornerShape(18.dp)
    Row(
        Modifier.fillMaxWidth().clip(shape).background(Color.White)
            .border(1.5.dp, if (selected) Theme.ink else Color.Transparent, shape)
            .clickable(onClick = onClick).padding(18.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(Modifier.weight(1f)) {
            Text(title, color = Theme.ink, fontSize = 17.sp, fontWeight = FontWeight.Bold)
            Text(price, Modifier.padding(top = 4.dp), color = ink(0.5f), fontSize = 13.sp)
        }
        Box(
            Modifier.size(28.dp).clip(CircleShape)
                .background(if (selected) Theme.ink else Color.Transparent)
                .border(1.5.dp, if (selected) Theme.ink else ink(0.4f), CircleShape),
            contentAlignment = Alignment.Center,
        ) { if (selected) Text("✓", color = Color.White, fontSize = 12.sp) }
    }
}
