package com.sociofit.app

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CircleButton(icon: String, onClick: () -> Unit) {
    Box(
        Modifier.size(40.dp).clip(CircleShape).background(Color.Black.copy(alpha = 0.06f)).clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) { Text(icon, fontSize = 16.sp) }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text,
        Modifier.padding(bottom = 8.dp),
        color = ink(0.75f),
        fontFamily = FontFamily.Monospace,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.5.sp,
    )
}

@Composable
fun Section(title: String, modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Column(modifier) {
        SectionTitle(title)
        content()
    }
}

@Composable
fun SegmentRow(
    options: List<String>,
    selection: String,
    locked: List<String> = emptyList(),
    onLockedTap: () -> Unit = {},
    onSelect: (String) -> Unit,
) {
    Row(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).background(Color.Black.copy(alpha = 0.08f)).padding(3.dp)
    ) {
        options.forEach { option ->
            val isLocked = option in locked
            val selected = option == selection
            Box(
                Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (selected) Theme.cardWhite else Color.Transparent)
                    .clickable { if (isLocked) onLockedTap() else onSelect(option) }
                    .padding(vertical = 11.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    option + if (isLocked) " 🔒" else "",
                    color = Theme.ink,
                    fontSize = 13.sp,
                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                )
            }
        }
    }
}

@Composable
fun Field(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    style: TextStyle = TextStyle(fontSize = 15.sp, color = Theme.ink),
    singleLine: Boolean = true,
) {
    BasicTextField(
        value, onChange, modifier,
        textStyle = style,
        singleLine = singleLine,
        cursorBrush = SolidColor(Theme.ink),
        decorationBox = { inner ->
            Box(propagateMinConstraints = true) {
                if (value.isEmpty()) Text(placeholder, style = style.copy(color = ink(0.4f)))
                inner()
            }
        },
    )
}
