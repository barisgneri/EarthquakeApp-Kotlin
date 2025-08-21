package com.barisguneri.earthquakeapp.core.presentation.map

import android.widget.Button
import android.widget.TextView
import com.barisguneri.earthquakeapp.R
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Marker.OnMarkerClickListener
import org.osmdroid.views.overlay.infowindow.InfoWindow

class CustomInfoWindow(mapView: MapView, private val onButtonClick: (String) -> Unit ) : InfoWindow(R.layout.custom_info_window, mapView) {
    override fun onOpen(item: Any?) {
        closeAllInfoWindowsOn(mapView)
        val marker = item as? Marker ?: return
        val titleView = mView.findViewById<TextView>(R.id.title)
        val descriptionView = mView.findViewById<TextView>(R.id.subdescription)
        val buttonView = mView.findViewById<Button>(R.id.detail_button)

        titleView.text = marker.title
        descriptionView.text = marker.subDescription
        val earthquakeId = marker.id ?: marker.title ?: "unknown"

        buttonView.setOnClickListener {
            onButtonClick(earthquakeId)
            close()
        }
    }

    override fun onClose() {
        close()
    }
}