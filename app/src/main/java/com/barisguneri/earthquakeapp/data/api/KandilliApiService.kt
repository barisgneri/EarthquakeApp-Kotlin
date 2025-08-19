package com.barisguneri.earthquakeapp.data.api

import com.barisguneri.earthquakeapp.common.NetworkConstants.EARTHQUAKE_DETAIL_ENDPOINT
import com.barisguneri.earthquakeapp.common.NetworkConstants.LIVE_EARTHQUAKE_ENDPOINT
import com.barisguneri.earthquakeapp.data.api.model.EarthquakeDTO
import com.barisguneri.earthquakeapp.data.api.model.EarthquakeDetailDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface KandilliApiService {
    @GET(LIVE_EARTHQUAKE_ENDPOINT)
    suspend fun getEarthquakes(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): Response<EarthquakeDTO>

    @GET(EARTHQUAKE_DETAIL_ENDPOINT)
    suspend fun getEarthquakeDetail(
        @Query("earthquake_id") earthquakeId: String
    ): Response<EarthquakeDetailDTO>
}


