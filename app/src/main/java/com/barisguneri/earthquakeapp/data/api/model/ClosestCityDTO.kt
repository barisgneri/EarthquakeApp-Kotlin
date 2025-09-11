package com.barisguneri.earthquakeapp.data.api.model

import com.google.gson.annotations.SerializedName

data class ClosestCityDTO(
    @SerializedName("cityCode")
    val cityCode: Int,
    @SerializedName("distance")
    val distance: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("population")
    val population: Int
)