package com.barisguneri.earthquakeapp.domain.repository

import androidx.paging.PagingData
import com.barisguneri.earthquakeapp.core.data.Resource
import com.barisguneri.earthquakeapp.domain.model.EarthquakeDetail
import com.barisguneri.earthquakeapp.domain.model.EarthquakeInfo
import com.barisguneri.earthquakeapp.domain.model.FilterState
import kotlinx.coroutines.flow.Flow

interface EarthquakeRepository {

    fun getPagingEarthquakes(filters: FilterState): Flow<PagingData<EarthquakeInfo>>

    fun getEarthquakeDetail(id: String): Flow<Resource<EarthquakeDetail>>

    fun getMapEarthquakes(filters: FilterState): Flow<List<EarthquakeInfo>>
}