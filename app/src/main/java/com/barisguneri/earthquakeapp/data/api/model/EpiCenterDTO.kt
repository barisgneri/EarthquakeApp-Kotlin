package com.barisguneri.earthquakeapp.data.api.model

import com.google.gson.annotations.SerializedName

data class EpiCenterDTO(
    @SerializedName("coordinates")
    val cityCode: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("population")
    val population: Int
)