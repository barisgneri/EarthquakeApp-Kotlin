package com.barisguneri.earthquakeapp.ui.features.map.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.barisguneri.earthquakeapp.R
import com.barisguneri.earthquakeapp.core.common.Constants
import com.barisguneri.earthquakeapp.core.common.Constants.defaultCenterLat
import com.barisguneri.earthquakeapp.core.common.Constants.defaultCenterLong
import com.barisguneri.earthquakeapp.core.common.magIcon
import com.barisguneri.earthquakeapp.core.presentation.map.CustomInfoWindow
import com.barisguneri.earthquakeapp.domain.model.MapMarkerData
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker

@Composable
fun MapView(
    modifier: Modifier = Modifier,
    markersData: List<MapMarkerData>,
    zoom: Double = Constants.zoomValue,
    onButtonClick: (String) -> Unit,
) {
    val context = LocalContext.current

    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { ctx ->
            org.osmdroid.views.MapView(ctx).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)
            }
        },
        update = { view ->
            view.overlayManager.clear()
            view.controller.setZoom(zoom)
            val markers = markersData.map { data ->
                Marker(view).apply {

                    val subDescriptionFormated = String.format(
                        context.getString(R.string.formated_sub_description),
                        data.dateTime,
                        data.magnitude.toString(),
                        data.depth
                    )

                    view.controller.setCenter(GeoPoint(defaultCenterLat, defaultCenterLong))
                    position = data.position
                    title = data.title
                    id = data.earthquakeId
                    subDescription = subDescriptionFormated
                    icon = magIcon(
                        context = context,
                        mag = data.magnitude
                    )
                    infoWindow = CustomInfoWindow(view) { earthquakeId ->
                        onButtonClick(earthquakeId)
                    }
                }
            }
            /*Burası kendi konumuna yıknlaştırmak için kullanılacak
                        if (myLocation) {
                            val locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), view)
                            locationOverlay.enableMyLocation()
                            view.overlays.add(locationOverlay)
                        }*/
            view.overlayManager.addAll(markers)
            view.invalidate()
        }
    )
}