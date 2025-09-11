package com.barisguneri.earthquakeapp.data.mapper

import com.barisguneri.earthquakeapp.data.api.model.EarthquakeDTO
import com.barisguneri.earthquakeapp.data.api.model.ResultDTO
import com.barisguneri.earthquakeapp.domain.model.EarthquakeInfo
import com.barisguneri.earthquakeapp.domain.model.Location

fun ResultDTO.toDomain() : EarthquakeInfo {
    return EarthquakeInfo(
        id = this.id,
        location = Location(
            lat = this.geoJson.coordinates[1],
            long = this.geoJson.coordinates[0]
        ),
        magnitude = this.mag,
        date = this.date,
        dateTime = this.dateTime,
        depthInfo = this.depth.toString(),
        title = this.title
    )
}

// ANA MAPPER FONKSİYONU: Zarf DTO'yu alır ve domain listesi döndürür.
// Bu fonksiyonun tek görevi: Zarfı aç, içindeki listeyi al ve her elemanı dönüştür.
fun EarthquakeDTO.toDomainList(): List<EarthquakeInfo> {
    // 1. Gelen `EarthquakeDTO`'nun içindeki `result` listesine eriş.
    // 2. Bu listenin her bir elemanı (`ResultDTO`) için yukarıdaki `toDomain()` fonksiyonunu çağır.
    return this.result.map { resultDto ->
        resultDto.toDomain()
    }
}
