package com.barisguneri.earthquakeapp.model

data class Airport(
    val code: String,
    val coordinates: Coordinates,
    val distance: Double,
    val name: String
)