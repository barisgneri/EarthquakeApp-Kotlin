package com.barisguneri.earthquakeapp.data.api.model

import com.google.gson.annotations.SerializedName

data class AirportDTO(
    @SerializedName("code")
    val code: String,
    @SerializedName("coordinates")
    val coordinates: CoordinatesDTO,
    @SerializedName("distance")
    val distance: Double,
    @SerializedName("name")
    val name: String
)