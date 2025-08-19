package com.barisguneri.earthquakeapp.domain.repository

import androidx.paging.PagingData
import com.barisguneri.earthquakeapp.common.Resource
import com.barisguneri.earthquakeapp.domain.model.EarthquakeDetail
import com.barisguneri.earthquakeapp.domain.model.EarthquakeInfo
import kotlinx.coroutines.flow.Flow

interface EarthquakeRepository {

    fun getEarthquakes(): Flow<PagingData<EarthquakeInfo>>

    fun getEarthquakeDetail(id: String): Flow<Resource<EarthquakeDetail>>
}