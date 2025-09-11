package com.barisguneri.earthquakeapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ResultDTO(
    @SerializedName("_id")
    val id: String,
    @SerializedName("created_at")
    val createdAt: Int,
    @SerializedName("date")
    val date: String,
    @SerializedName("date_time")
    val dateTime: String,
    @SerializedName("depth")
    val depth: Double,
    @SerializedName("earthquake_id")
    val earthquakeId: String,
    @SerializedName("geojson")
    val geoJson: GeojsonDTO,
    @SerializedName("location_properties")
    val locationProperties: LocationPropertiesDTO,
    @SerializedName("location_tz")
    val locationTz: String,
    @SerializedName("mag")
    val mag: Double,
    @SerializedName("provider")
    val provider: String,
    @SerializedName("rev")
    val rev: String,
    @SerializedName("title")
    val title: String
)