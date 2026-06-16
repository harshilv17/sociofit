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
