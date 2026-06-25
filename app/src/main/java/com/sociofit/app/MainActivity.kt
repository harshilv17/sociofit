package com.sociofit.app

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { App() }
    }
}

@Composable
fun App() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var splash by remember { mutableStateOf(true) }
    var activity by remember { mutableStateOf<FitnessActivity?>(null) }
    var settings by remember { mutableStateOf(false) }
    var paywall by remember { mutableStateOf(false) }

    val askLocation = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
        if (result.values.any { it }) scope.launch { runCatching { Store.locate(context) } }
    }

    LaunchedEffect(Unit) {
        askLocation.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION))
        delay(1400)
        splash = false
    }

    Box(Modifier.fillMaxSize().background(Theme.cream)) {
        val current = activity
        when {
            splash -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Share your fitness wins.", color = ink(0.5f), fontSize = 16.sp)
            }
            current != null -> {
                BackHandler { activity = null }
                StickerEditorScreen(
                    current,
                    onBack = { activity = null },
                    onSettings = { settings = true },
                    onPaywall = { paywall = true },
                )
            }
            else -> HomeScreen(
                onOpen = { activity = it },
                onSettings = { settings = true },
                onPaywall = { paywall = true },
            )
        }
        // Later sheets draw on top, and their BackHandlers take priority.
        Sheet(settings, onClose = { settings = false }) {
            SettingsScreen(onBack = { settings = false }, onPaywall = { paywall = true })
        }
        Sheet(paywall, onClose = { paywall = false }) {
            PaywallScreen(onClose = { paywall = false })
        }
    }
}

/** Full-screen slide-up overlay for settings and paywall. */
@Composable
private fun Sheet(visible: Boolean, onClose: () -> Unit, content: @Composable () -> Unit) {
    if (visible) BackHandler(onBack = onClose)
    AnimatedVisibility(visible, enter = slideInVertically { it }, exit = slideOutVertically { it }) {
        // pointerInput blocks taps from reaching the screen underneath.
        Box(Modifier.fillMaxSize().background(Theme.cream).pointerInput(Unit) {}) { content() }
    }
}
