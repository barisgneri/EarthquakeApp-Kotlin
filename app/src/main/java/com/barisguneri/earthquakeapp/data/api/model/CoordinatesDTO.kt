package com.barisguneri.earthquakeapp.data.api.model

import com.google.gson.annotations.SerializedName

data class CoordinatesDTO(
    @SerializedName("coordinates")
    val coordinates: List<Double>,
    @SerializedName("type")
    val type: String
)