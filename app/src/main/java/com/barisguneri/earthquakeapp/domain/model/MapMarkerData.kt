package com.barisguneri.earthquakeapp.domain.model

import org.osmdroid.util.GeoPoint

data class MapMarkerData(
    val position: GeoPoint,
    val title: String,
    val subDescription: String,
    val magnitude: Double
)
