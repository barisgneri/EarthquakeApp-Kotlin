package com.barisguneri.earthquakeapp.domain.model

data class ClosestCities(
    val name: String,
    val distance: Double,
    val population: Int,
    val cityCode: Int
)
