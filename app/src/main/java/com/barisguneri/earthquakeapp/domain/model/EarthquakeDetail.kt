package com.barisguneri.earthquakeapp.domain.model


data class EarthquakeDetail(
    val id: String,
    val location: Location,
    val magnitude: Double,
    val date: String,
    val dateTime : String,
    val depthInfo: String,
    val title: String,
    val airports: List<Airports>,
    val epiCenter: EpiCenter,
    val closestCities: List<ClosestCities>
)