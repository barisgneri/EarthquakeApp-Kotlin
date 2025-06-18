package com.barisguneri.earthquakeapp.data.api

import com.barisguneri.earthquakeapp.common.NetworkConstants.LIVE_EARTHQUAKE_ENDPOINT
import com.barisguneri.earthquakeapp.data.api.model.EarthquakeDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface KandilliApiService {
    @GET(LIVE_EARTHQUAKE_ENDPOINT)
    suspend fun getEarthquakes(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): Response<EarthquakeDTO>
}


