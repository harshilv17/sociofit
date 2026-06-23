package com.sociofit.app

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.util.Locale
import kotlin.coroutines.resume

// ponytail: in-memory app state; Pro isn't persisted across launches.
object Store {
    var isPro by mutableStateOf(false)
    var stickerFont by mutableStateOf(FONTS[1])
    var city by mutableStateOf("")
    var country by mutableStateOf("")
    var flag by mutableStateOf("")
    var latitude by mutableStateOf<Double?>(null)
    var longitude by mutableStateOf<Double?>(null)

    val coordsText: String?
        get() {
            val lat = latitude ?: return null
            val lon = longitude ?: return null
            return "${fmtLat(lat)} · ${fmtLon(lon)}"
        }

    /** Caller must hold a location permission. */
    @SuppressLint("MissingPermission")
    suspend fun locate(context: Context) {
        val lm = context.getSystemService(LocationManager::class.java)
        val provider = listOf(LocationManager.NETWORK_PROVIDER, LocationManager.GPS_PROVIDER)
            .firstOrNull { lm.isProviderEnabled(it) } ?: return
        val loc: Location = (if (Build.VERSION.SDK_INT >= 30) {
            suspendCancellableCoroutine<Location?> { cont ->
                lm.getCurrentLocation(provider, null, context.mainExecutor) { cont.resume(it) }
            }
        } else null) ?: lm.getLastKnownLocation(provider) ?: return

        latitude = loc.latitude
        longitude = loc.longitude

        val place = withContext(Dispatchers.IO) {
            @Suppress("DEPRECATION")
            Geocoder(context, Locale.getDefault()).getFromLocation(loc.latitude, loc.longitude, 1)?.firstOrNull()
        } ?: return
        city = place.locality ?: place.adminArea ?: ""
        country = place.countryName ?: ""
        flag = isoFlag(place.countryCode)
    }
}

/** "AE" -> 🇦🇪 */
fun isoFlag(code: String?): String {
    if (code == null || code.length != 2) return ""
    return buildString { code.uppercase().forEach { appendCodePoint(127397 + it.code) } }
}
