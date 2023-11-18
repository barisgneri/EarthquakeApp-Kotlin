package com.barisguneri.earthquakeapp.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

@Composable
fun OsmdroidMapView() {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            MapView(context).apply {
                setTileSource(TileSourceFactory.USGS_TOPO)
                setOnClickListener {
                }
            }
        },
        update = { view ->
            // Set zoom level and center coordinates
            view.controller.setZoom(9.0)
            view.controller.setCenter(GeoPoint(41.0719461, 28.200803))
        }
    )
}