package com.barisguneri.earthquakeapp.data.mapper

import com.barisguneri.earthquakeapp.core.common.convertDateStringToLong
import com.barisguneri.earthquakeapp.data.remote.dto.EpiCenterDTO
import com.barisguneri.earthquakeapp.data.remote.dto.ResultDTO
import com.barisguneri.earthquakeapp.domain.model.Airports
import com.barisguneri.earthquakeapp.domain.model.ClosestCities
import com.barisguneri.earthquakeapp.domain.model.EarthquakeDetail
import com.barisguneri.earthquakeapp.domain.model.EpiCenter
import com.barisguneri.earthquakeapp.domain.model.Location

fun ResultDTO.toDetailModel() : EarthquakeDetail{
    val coordinates = Location(this.geoJson.coordinates[1], this.geoJson.coordinates[0])
    return EarthquakeDetail(
        id = this.earthquakeId,
        location = coordinates,
        magnitude = this.mag,
        date = this.date,
        dateTime = convertDateStringToLong(this.dateTime),
        depthInfo = this.depth.toString(),
        title = this.title,
        airports = this.toAirportsModel(),
        epiCenter = this.locationProperties.epiCenter.toDomain(),
        closestCities = this.toClosestCities()
    )
}

fun ResultDTO.toAirportsModel() : List<Airports>{
    return this.locationProperties.airport.map {
            Airports(
                code = it.code,
                coordinates = Location(it.coordinates.coordinates[1], it.coordinates.coordinates[0]),
                distance = it.distance,
                name = it.name
            )
    }
}

fun EpiCenterDTO.toDomain() : EpiCenter {
    return EpiCenter(
        name = this.name,
        population = this.population,
        code = this.cityCode
    )
}

fun ResultDTO.toClosestCities() : List<ClosestCities>{
    return this.locationProperties.closestCities.map {
            ClosestCities(
                name = it.name,
                distance = it.distance,
                population = it.population,
                cityCode = it.cityCode
            )
    }
}