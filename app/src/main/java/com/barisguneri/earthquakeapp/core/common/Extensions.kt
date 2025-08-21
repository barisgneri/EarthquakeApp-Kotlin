package com.barisguneri.earthquakeapp.core.common

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.barisguneri.earthquakeapp.R
import kotlinx.coroutines.flow.Flow

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