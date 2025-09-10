package com.barisguneri.earthquakeapp.data.mapper

import com.barisguneri.earthquakeapp.core.common.convertDateStringToLong
import com.barisguneri.earthquakeapp.data.local.model.EarthquakeEntity
import com.barisguneri.earthquakeapp.data.remote.dto.ResultDTO
import com.barisguneri.earthquakeapp.domain.model.EarthquakeInfo
import com.barisguneri.earthquakeapp.domain.model.Location

fun ResultDTO.toDomain() : EarthquakeInfo {
    return EarthquakeInfo(
        id = this.earthquakeId,
        location = Location(
            lat = this.geoJson.coordinates[1],
            lng = this.geoJson.coordinates[0]
        ),
        magnitude = this.mag,
        date = this.date,
        dateTime = convertDateStringToLong(this.dateTime),
        depthInfo = this.depth.toString(),
        title = this.title
    )
}

fun ResultDTO.toEntity(): EarthquakeEntity {

    return EarthquakeEntity(
        id = this.earthquakeId,
        magnitude = this.mag,
        title = this.title,
        location = Location(
            lat = this.geoJson.coordinates[1],
            lng = this.geoJson.coordinates[0]
        ),
        date = this.date,
        dateTime = convertDateStringToLong(this.dateTime),
        depthInfo = this.depth.toString()
    )
}

fun EarthquakeEntity.toDomainModel(): EarthquakeInfo {
    return EarthquakeInfo(
        id = this.id,
        location = this.location,
        magnitude = this.magnitude,
        date = this.date,
        dateTime = this.dateTime,
        depthInfo = this.depthInfo,
        title = this.title
    )
}