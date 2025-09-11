package com.barisguneri.earthquakeapp.ui.features.map.ui.component

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.barisguneri.earthquakeapp.R
import com.barisguneri.earthquakeapp.core.common.Constants
import com.barisguneri.earthquakeapp.core.common.convertLongToDateString
import com.barisguneri.earthquakeapp.core.common.magIcon
import com.barisguneri.earthquakeapp.ui.components.map.CustomInfoWindow
import com.barisguneri.earthquakeapp.domain.model.MapMarkerData
import com.barisguneri.earthquakeapp.ui.features.earthquakeList.viewmodel.EarthquakeListContract
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun MapView(
    modifier: Modifier = Modifier,
    markersData: List<MapMarkerData>,
    onButtonClick: (String) -> Unit,
    onAction: (EarthquakeListContract.UiAction) -> Unit,
) {
    val iconCache = remember { mutableMapOf<Double, Drawable>() }

    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { context ->
            createAndConfigureMapView(context)
        },
        update = { mapView ->
            val mapListener = object : MapListener {
                override fun onScroll(event: ScrollEvent?): Boolean {
                    event?.source?.boundingBox?.let {
                        onAction(EarthquakeListContract.UiAction.UpdateMapBounds(it))
                    }
                    return true
                }
                override fun onZoom(event: ZoomEvent?): Boolean {
                    event?.source?.boundingBox?.let {
                        onAction(EarthquakeListContract.UiAction.UpdateMapBounds(it))
                    }
                    return true
                }
            }
            mapView.setMapListener(mapListener)

            mapView.overlays.removeAll { it is Marker }

            markersData.forEach { data ->
                val subDescriptionFormatted = String.format(
                    mapView.context.getString(R.string.formated_sub_description),
                    data.title,
                    convertLongToDateString(data.dateTime)
                )

                val markerIcon = iconCache.getOrPut(data.magnitude) {
                    magIcon(context = mapView.context, mag = data.magnitude) ?: mapView.context.getDrawable(R.drawable.marker_icon_small_earthquake)!!
                }

                val marker = Marker(mapView).apply {
                    position = data.position
                    title = "${mapView.context.getString(R.string.magnitude)} ${data.magnitude}"
                    id = data.earthquakeId
                    subDescription = subDescriptionFormatted
                    icon = markerIcon
                    infoWindow = CustomInfoWindow(mapView) { earthquakeId ->
                        onButtonClick(earthquakeId)
                    }
                }
                mapView.overlays.add(marker)
            }

            mapView.invalidate()
        }
    )
}

private fun createAndConfigureMapView(context: Context): MapView {
    return MapView(context).apply {
        setTileSource(TileSourceFactory.MAPNIK)
        setMultiTouchControls(true)

        controller.setZoom(Constants.zoomValue * 1.4)
        controller.setCenter(GeoPoint(Constants.defaultCenterLat, Constants.defaultCenterLong))
    }
}

