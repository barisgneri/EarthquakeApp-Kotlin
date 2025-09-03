package com.barisguneri.earthquakeapp.core.common

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
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
fun calculateNowAndDateTimeBetween(context: Context, date: String): String {
    //2025-09-03 00:31:47
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    val pastTime: LocalDateTime
    try {
        pastTime = LocalDateTime.parse(date, formatter)
    } catch (e: Exception) {
        return context.getString(R.string.invalidDateFormat)
    }

    val now = LocalDateTime.now()
    val period = Period.between(pastTime.toLocalDate(), now.toLocalDate())
    val duration = Duration.between(pastTime, now)
    return when {
        period.years > 0 -> "${period.years} ${context.getString(R.string.yearsAgo)}"
        period.months > 0 -> "${period.months} ${context.getString(R.string.monthAgo)}"
        period.days >= 7 -> "${period.days / 7} ${context.getString(R.string.weekAgo)}"
        period.days > 0 -> "${period.days} ${context.getString(R.string.daysAgo)}"
        duration.toHours() > 0 -> "${duration.toHours()} ${context.getString(R.string.hoursAgo)}"
        duration.toMinutes() > 0 -> "${duration.toMinutes()} ${context.getString(R.string.minutesAgo)}"
        else -> context.getString(R.string.fewSecondsAgo)
    }
}