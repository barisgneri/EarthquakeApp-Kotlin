package com.barisguneri.earthquakeapp.model

data class ClosestCity(
    val cityCode: Int,
    val distance: Double,
    val name: String,
    val population: Int
)