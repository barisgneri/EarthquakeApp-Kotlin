package com.barisguneri.earthquakeapp.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.barisguneri.earthquakeapp.R
import com.barisguneri.earthquakeapp.util.Constants
import com.barisguneri.earthquakeapp.viewmodel.EarthquakeViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.infowindow.InfoWindow

@Composable
fun MapViewScreen(navController: NavHostController, viewModel: EarthquakeViewModel) {
    val latitudeDouble = viewModel.earthquake?.value?.get(0)?.geojson?.coordinates?.get(1) ?: 0.0
    val longitudeDouble = viewModel.earthquake?.value?.get(0)?.geojson?.coordinates?.get(0) ?: 0.0
    val title = viewModel.earthquake?.value?.get(0)?.title
    val epiCenterName = viewModel.earthquake?.value?.get(0)?.location_properties?.epiCenter?.name
    val epiCenterPopulation = viewModel.earthquake?.value?.get(0)?.location_properties?.epiCenter?.population

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
            marker.title = title
            marker.subDescription = "İl: $epiCenterName\nNüfus: $epiCenterPopulation"

            val overlayManager = view.getOverlayManager()
            overlayManager.add(marker)
        }
    )
}