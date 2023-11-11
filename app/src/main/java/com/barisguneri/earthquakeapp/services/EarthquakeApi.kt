package com.barisguneri.earthquakeapp.services

import com.barisguneri.earthquakeapp.model.Earthquake
import com.barisguneri.earthquakeapp.util.Resource
import retrofit2.http.GET

interface EarthquakeApi {

    @GET("live")
    suspend fun getEarthquakeList() : Earthquake
}