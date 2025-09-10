package com.barisguneri.earthquakeapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LocationPropertiesDTO(
    @SerializedName("airports")
    val airport: List<AirportDTO>,
    @SerializedName("closestCities")
    val closestCities: List<ClosestCityDTO>,
    @SerializedName("closestCity")
    val closestCity: ClosestCityDTO,
    @SerializedName("epiCenter")
    val epiCenter: EpiCenterDTO
)