package com.barisguneri.earthquakeapp.core.common

import com.barisguneri.earthquakeapp.domain.model.Airports
import com.barisguneri.earthquakeapp.domain.model.ClosestCities
import com.barisguneri.earthquakeapp.domain.model.EarthquakeDetail
import com.barisguneri.earthquakeapp.domain.model.EarthquakeInfo
import com.barisguneri.earthquakeapp.domain.model.EpiCenter
import com.barisguneri.earthquakeapp.domain.model.Location

object PreviewMockData {
    val earthquakeInfoModel = listOf(
        EarthquakeInfo(
            id = "ZwBwDlDghVOd1",
            location = Location(lat = 39.2065, lng = 28.1677),
            magnitude = 1.8,
            date = "2025.08.21",
            dateTime = 1,
            depthInfo = "11.1 km",
            title = "YAYLACIK-SINDIRGI (BALIKESIR)"
        ),
        EarthquakeInfo(
            id = "x8BJjS2jR2K9j",
            location = Location(lat = 38.3732, lng = 38.7665),
            magnitude = 2.3,
            date = "2025.08.21",
            dateTime = 1,
            depthInfo = "6.2 km",
            title = "TEPEKOY-KALE (MALATYA)"
        ),
        EarthquakeInfo(
            id = "64VmLJRdaFclM",
            location = Location(lat = 39.2412, lng = 28.1048),
            magnitude = 2.1,
            date = "2025.08.21",
            dateTime = 1,
            depthInfo = "10.2 km",
            title = "KOZLU-SINDIRGI (BALIKESIR)"
        ),
        EarthquakeInfo(
            id = "eA3yZa95qljbO",
            location = Location(lat = 39.223, lng = 28.173),
            magnitude = 2.6,
            date = "2025.08.21",
            dateTime = 1,
            depthInfo = "8.3 km",
            title = "YAYLACIK-SINDIRGI (BALIKESIR)"
        ),
        EarthquakeInfo(
            id = "eMXIz43nkH1mc",
            location = Location(lat = 39.2435, lng = 28.0605),
            magnitude = 1.6,
            date = "2025.08.21",
            dateTime = 1,
            depthInfo = "13.6 km",
            title = "ALAKIR-SINDIRGI (BALIKESIR)"
        )
    )

    val mockEarthquakeInfo = EarthquakeDetail(
        id = "ZwBwDlDghVOd1",
        location = Location(lat = 39.2065, lng = 28.1677),
        magnitude = 1.8,
        date = "2025.08.21",
        dateTime = 1,
        depthInfo = "11.1 km",
        title = "YAYLACIK-SINDIRGI (BALIKESIR)",
        airports = listOf(
            Airports(
                code = "BZI",
                coordinates = Location(lat = 39.6193, lng = 27.926),
                distance = 50379.27,
                name = "Balıkesir Merkez Havalimanı"
            ),
            Airports(
                code = "EDO",
                coordinates = Location(lat = 39.5546, lng = 27.0138),
                distance = 106460.29,
                name = "Balıkesir Koca Seyit Havalimanı"
            ),
            Airports(
                code = "BDM",
                coordinates = Location(lat = 40.318, lng = 27.9777),
                distance = 124655.68,
                name = "Bandırma Havalimanı"
            )
        ),
        epiCenter = EpiCenter(
            name = "Balıkesir",
            population = 1257590,
            code = 10
        ),
        closestCities = listOf(
            ClosestCities("Manisa", 50595.55, 1468279, 45),
            ClosestCities("İzmir", 99381.96, 4462056, 35),
            ClosestCities("Kütahya", 120270.38, 580701, 43),
            ClosestCities("Bursa", 122003.02, 3194720, 16),
            ClosestCities("Uşak", 127001.56, 375454, 64)
        )
    )
}

