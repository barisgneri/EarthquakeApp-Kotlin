package com.barisguneri.earthquakeapp.core.common

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.barisguneri.earthquakeapp.R
import kotlinx.coroutines.flow.Flow
import java.time.Duration
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter
import kotlin.math.abs

@Composable
fun <T> Flow<T>.CollectWithLifecycle(
    collect: suspend (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(this, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            this@CollectWithLifecycle.collect { effect ->
                collect(effect)
            }
        }
    }
}

fun magIcon(context: Context, mag: Double) : Drawable? {
    return if (mag < 3.0){
        context.getDrawable(R.drawable.marker_icon_small_earthquake)
    }else if(mag > 3.0 && mag < 5.0){
        context.getDrawable(R.drawable.marker_icon_medium_earthquake)
    }else{
        context.getDrawable(R.drawable.marker_icon_big_earthquake)
    }
}

fun Double.toKmFormattedString(): String {
    val km = this / 1000.0
    val formated = String.format("%.2f", km)
    return formated + "km"
}

fun Int.toReadableString(): String {
    return "%,d".format(this).replace(',', '.')
}

@RequiresApi(Build.VERSION_CODES.O)
fun calculateNowAndDateTimeBetween(date: String): String {
    //2025-09-03 00:31:47
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    val pastTime: LocalDateTime
    try {
        pastTime = LocalDateTime.parse(date, formatter)
    } catch (e: Exception) {
        return "Geçersiz tarih formatı"
    }

    val now = LocalDateTime.now()
    if (pastTime.isAfter(now)) {
        return "Henüz gerçekleşmedi"
    }
    val period = Period.between(pastTime.toLocalDate(), now.toLocalDate())
    val duration = Duration.between(pastTime, now)

    return when {
        period.years > 0 -> "${period.years} yıl önce"
        period.months > 0 -> "${period.months} ay önce"
        period.days >= 7 -> "${period.days / 7} hafta önce"
        period.days > 0 -> "${period.days} gün önce"
        duration.toHours() > 0 -> "${duration.toHours()} saat önce"
        duration.toMinutes() > 0 -> "${duration.toMinutes()} dakika önce"
        else -> "birkaç saniye önce"
    }
}