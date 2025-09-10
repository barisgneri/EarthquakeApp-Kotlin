package com.barisguneri.earthquakeapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class EarthquakeDTO(
    @SerializedName("desc")
    val desc: String,
    @SerializedName("httpStatus")
    val httpStatus: Int,
    @SerializedName("metadata")
    val metadata: MetadataDTO,
    @SerializedName("result")
    val result: List<ResultDTO>,
    @SerializedName("serverloadms")
    val serverloadms: Int,
    @SerializedName("status")
    val status: Boolean
)