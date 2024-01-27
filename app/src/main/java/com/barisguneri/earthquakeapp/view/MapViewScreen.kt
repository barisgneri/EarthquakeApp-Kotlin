package com.barisguneri.earthquakeapp.view

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
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
                val latitude = earthquake.geojson.coordinates[1]
                val longitude = earthquake.geojson.coordinates[0]
                val title = earthquake.title
                val epiCenterName = earthquake.location_properties.epiCenter.name
                val epiCenterPopulation = earthquake.location_properties.epiCenter.population

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