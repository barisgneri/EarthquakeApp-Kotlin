package com.barisguneri.earthquakeapp.core.presentation

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.barisguneri.earthquakeapp.R
import com.barisguneri.earthquakeapp.common.Constants
import com.barisguneri.earthquakeapp.domain.model.MapMarkerData
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun MapView(modifier: Modifier = Modifier,
            markersData: List<MapMarkerData>,
            zoom: Double = Constants.zoomValue
) {
    val context = LocalContext.current

    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { ctx ->
            MapView(ctx).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)
            }
        },
        update = { view ->
            view.overlayManager.clear()
            view.controller.setZoom(zoom)
            val markers = markersData.map { data ->
                Marker(view).apply {
                    view.controller.setCenter(GeoPoint(data.position.latitude, data.position.longitude))
                    position = data.position
                    title = data.title
                    subDescription = data.subDescription
                    icon = magIcon(
                        context = context,
                        mag = data.magnitude
                    )
                    closeInfoWindow()
                }
            }

            view.overlayManager.addAll(markers)
            view.invalidate()
        }
    )
}

private fun magIcon(context: Context, mag: Double) : Drawable? {
    return if (mag < 3.0){
        context.getDrawable(R.drawable.marker_icon_small_earthquake)
    }else if(mag > 3.0 && mag < 5.0){
        context.getDrawable(R.drawable.marker_icon_medium_earthquake)
    }else{
        context.getDrawable(R.drawable.marker_icon_big_earthquake)
    }
}