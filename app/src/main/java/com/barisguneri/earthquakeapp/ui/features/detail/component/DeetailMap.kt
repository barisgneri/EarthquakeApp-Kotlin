package com.barisguneri.earthquakeapp.ui.features.detail.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.barisguneri.earthquakeapp.core.common.Constants.zoomValue
import com.barisguneri.earthquakeapp.core.common.magIcon
import com.barisguneri.earthquakeapp.domain.model.MapMarkerData
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker

@Composable
fun DetailMap(
    modifier: Modifier = Modifier,
    markersData: MapMarkerData,
) {
    val context = LocalContext.current

    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { ctx ->
            org.osmdroid.views.MapView(ctx).apply {
                setMultiTouchControls(false)
                isClickable = false
                isFocusable = false
                isEnabled = false
            }
        },
        update = { view ->
            view.overlayManager.clear()
            view.controller.setZoom(zoomValue * 1.5)
            val markers = Marker(view).apply {
                view.controller.setCenter(
                    GeoPoint(
                        markersData.position.latitude + 0.1,
                        markersData.position.longitude
                    )
                )
                position = markersData.position
                icon = magIcon(
                    context = context,
                    mag = markersData.magnitude
                )
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                setOnMarkerClickListener { marker, mapView ->
                    true
                }
            }
            view.overlayManager.add(markers)
            view.invalidate()
        }
    )
}