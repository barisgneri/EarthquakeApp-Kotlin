package com.barisguneri.earthquakeapp.presentation.view

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.barisguneri.earthquakeapp.R
import com.barisguneri.earthquakeapp.common.Constants
import com.barisguneri.earthquakeapp.presentation.viewmodel.EarthquakeViewModel
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun MapViewScreen(navController: NavHostController, viewModel: EarthquakeViewModel){
    val context = LocalContext.current
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 30.dp),
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
            view.controller.setCenter(
                GeoPoint(
                    Constants.defaultCenterLat,
                    Constants.defaultCenterLong
                )
            )
            val markers = viewModel.earthquakeList.value.map { earthquake ->
                val latitude = earthquake.geoJson.coordinates[1]
                val longitude = earthquake.geoJson.coordinates[0]
                val title = earthquake.title
                val epiCenterName = earthquake.locationProperties.epiCenter.name
                val epiCenterPopulation = earthquake.locationProperties.epiCenter.population

                Marker(view).apply {
                    position = GeoPoint(latitude, longitude)
                    this.title = title
                    subDescription = "İl: $epiCenterName\nNüfus: $epiCenterPopulation\nBüyüklük: ${earthquake.mag}"
                    icon = magIcon(context = context, mag = earthquake.mag)
                    closeInfoWindow()
                }
            }

            val overlayManager = view.overlayManager
            overlayManager.addAll(markers)
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