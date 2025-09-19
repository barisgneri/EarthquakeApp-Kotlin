package com.barisguneri.earthquakeapp.domain.model

data class Airports(
    val code: String,
    val coordinates: Location,
    val distance: Double,
    val name: String
)
