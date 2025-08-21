package com.barisguneri.earthquakeapp.domain.model

import org.osmdroid.util.GeoPoint

data class MapMarkerData(
    val earthquakeId: String,
    val position: GeoPoint,
    val title: String,
    val dateTime: String,
    val depth: String,
    val magnitude: Double
)
