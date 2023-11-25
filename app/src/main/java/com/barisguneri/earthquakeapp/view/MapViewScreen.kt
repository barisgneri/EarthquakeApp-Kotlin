package com.barisguneri.earthquakeapp.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.barisguneri.earthquakeapp.R
import com.barisguneri.earthquakeapp.util.Constants
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.infowindow.InfoWindow

@Composable
fun MapViewScreen(latitude: String, longitude: String, navController: NavController) {
    var latitudeDouble = latitude.toDouble()
    var longitudeDouble = longitude.toDouble()

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            MapView(context).apply {
                setTileSource(TileSourceFactory.CLOUDMADESMALLTILES)
                this.setTileSource(TileSourceFactory.MAPNIK)
                this.setBuiltInZoomControls(true)
                this.setMultiTouchControls(true)
                setOnClickListener {
                }
            }
        },
        update = { view ->
            view.controller.setZoom(Constants.zoomValue)
            view.controller.setCenter(GeoPoint(latitudeDouble, longitudeDouble))
            val marker = Marker(
                view
            )
            marker.position = GeoPoint(latitudeDouble, longitudeDouble)
            marker.title = "Test 6tgsf"
            marker.subDescription = "fgjmksdöfl ıjvmkfvmık trvmmırmvrtvfv ver er erferfefr"

            val overlayManager = view.getOverlayManager()
            overlayManager.add(marker)
        }
    )
}