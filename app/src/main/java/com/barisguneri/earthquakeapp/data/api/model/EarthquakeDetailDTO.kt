package com.barisguneri.earthquakeapp.data.api.model

import com.google.gson.annotations.SerializedName

data class EarthquakeDetailDTO(
    @SerializedName("desc")
    val desc: String,
    @SerializedName("httpStatus")
    val httpStatus: Int,
    @SerializedName("metadata")
    val metadata: MetadataDTO,
    @SerializedName("result")
    val result: ResultDTO,
    @SerializedName("serverloadms")
    val serverloadms: Int,
    @SerializedName("status")
    val status: Boolean
)
