package com.barisguneri.earthquakeapp.domain.model

data class EarthquakeInfo(
    val id: String,
    val location: Location,
    val magnitude: Double,
    val date: String,
    val dateTime : Long,
    val depthInfo: String,
    val title: String,
)
