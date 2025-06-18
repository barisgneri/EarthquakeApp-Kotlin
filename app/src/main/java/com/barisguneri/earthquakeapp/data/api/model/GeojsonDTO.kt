package com.barisguneri.earthquakeapp.data.api.model

import com.google.gson.annotations.SerializedName

data class GeojsonDTO(
    @SerializedName("coordinates")
    val coordinates: List<Double>,
    @SerializedName("type")
    val type: String
)