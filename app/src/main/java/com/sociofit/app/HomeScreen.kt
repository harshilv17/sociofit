package com.sociofit.app

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(onOpen: (FitnessActivity) -> Unit, onSettings: () -> Unit, onPaywall: () -> Unit) {
    var searching by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }
    val results = if (searching && query.isNotEmpty()) ACTIVITIES.filter { it.name.contains(query, ignoreCase = true) } else ACTIVITIES

    Column(Modifier.fillMaxSize()) {
        Row(
            Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            CircleButton("🔍") { searching = !searching; query = "" }
            CircleButton("⚙️", onSettings)
        }
        LazyColumn(
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                if (searching) {
                    val focus = remember { FocusRequester() }
                    LaunchedEffect(Unit) { focus.requestFocus() }
                    Row(
                        Modifier.padding(top = 12.dp).fillMaxWidth().clip(RoundedCornerShape(14.dp))
                            .background(Color.Black.copy(alpha = 0.06f)).padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text("🔍", fontSize = 15.sp)
                        Field(
                            query, { query = it },
                            Modifier.weight(1f).padding(vertical = 12.dp).focusRequester(focus),
                            placeholder = "Search activities",
                        )
                    }
                } else {
                    Text(
                        "Share your fitness wins.",
                        Modifier.fillMaxWidth().padding(top = 20.dp, bottom = 8.dp),
                        color = Theme.ink,
                        fontFamily = FontFamily.Serif,
                        fontSize = 28.sp,
                        textAlign = TextAlign.Center,
                    )
                }
            }
            items(results, key = { it.name }) { activity ->
                Row(
                    Modifier.fillMaxWidth().clip(RoundedCornerShape(18.dp)).background(activity.color)
                        .clickable { onOpen(activity) }
                        .padding(horizontal = 18.dp, vertical = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Text(activity.emoji, fontSize = 16.sp)
                    Text(activity.name, Modifier.weight(1f), color = Theme.ink, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
