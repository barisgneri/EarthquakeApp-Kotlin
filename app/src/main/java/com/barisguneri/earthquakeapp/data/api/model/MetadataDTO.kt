package com.barisguneri.earthquakeapp.data.api.model

import com.google.gson.annotations.SerializedName

data class MetadataDTO(
    @SerializedName("date_ends")
    val dateEnds: String,
    @SerializedName("date_starts")
    val dateStarts: String,
    @SerializedName("total")
    val total: Int
)