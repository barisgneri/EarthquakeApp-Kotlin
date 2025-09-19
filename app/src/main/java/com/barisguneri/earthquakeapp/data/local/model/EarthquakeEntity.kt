package com.barisguneri.earthquakeapp.data.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.barisguneri.earthquakeapp.domain.model.Location

@Entity(tableName = "earthquakes")
data class EarthquakeEntity(
    @PrimaryKey()
    val id: String = "",
    val magnitude: Double,
    val title: String,
    @Embedded
    val location: Location,
    val date: String,
    val dateTime: Long,
    val depthInfo: String,
    )