package com.barisguneri.earthquakeapp.ui.features.detail.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.barisguneri.earthquakeapp.core.common.Constants.zoomValue
import com.barisguneri.earthquakeapp.core.common.magIcon
import com.barisguneri.earthquakeapp.domain.model.MapMarkerData
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.dimens
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.padding
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker

@Composable
fun DetailMap(
    modifier: Modifier = Modifier,
    markersData: MapMarkerData,
) {
    val context = LocalContext.current

    Card(
        modifier = modifier.padding(padding.dimension12),
        shape = RoundedCornerShape(
            dimens.dp16
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = dimens.dp12),
    ) {

        AndroidView(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            factory = { ctx ->
                org.osmdroid.views.MapView(ctx).apply {
                    setMultiTouchControls(false)
                    isClickable = false
                    isFocusable = false
                    isEnabled = false
                    setBuiltInZoomControls(false)
                }
            },
            update = { view ->
                view.overlayManager.clear()
                view.controller.setZoom(zoomValue * 1.5)
                val markers = Marker(view).apply {
                    view.controller.setCenter(
                        GeoPoint(
                            markersData.position.latitude,
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
}