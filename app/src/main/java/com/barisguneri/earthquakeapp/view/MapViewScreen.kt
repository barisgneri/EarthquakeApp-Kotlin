package com.barisguneri.earthquakeapp.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.barisguneri.earthquakeapp.R
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.infowindow.InfoWindow

@Composable
fun OsmdroidMapView() {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            MapView(context).apply {
                setTileSource(TileSourceFactory.USGS_TOPO)
                this.setTileSource(TileSourceFactory.MAPNIK)
                this.setBuiltInZoomControls(true)
                this.setMultiTouchControls(true)
                setOnClickListener {
                }
            }
        },
        update = { view ->
            view.controller.setZoom(9.0)
            view.controller.setCenter(GeoPoint(41.0719461, 28.200803))
            val marker = Marker(
                view
            )
            marker.position = GeoPoint(41.0719461, 28.200803)
            marker.title = "Test 6tgsf"
            marker.subDescription = "fgjmksdöfl ıjvmkfvmık trvmmırmvrtvfv ver er erferfefr"

            val overlayManager = view.getOverlayManager()
            overlayManager.add(marker)
        }
    )
}