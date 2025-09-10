package com.barisguneri.earthquakeapp.domain.useCase

import androidx.paging.PagingData
import androidx.paging.filter
import com.barisguneri.earthquakeapp.domain.model.EarthquakeInfo
import com.barisguneri.earthquakeapp.domain.model.FilterState
import com.barisguneri.earthquakeapp.domain.repository.EarthquakeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetEarthquakesUseCase @Inject constructor(
    private val repository: EarthquakeRepository
) {
    operator fun invoke(filters: FilterState): Flow<PagingData<EarthquakeInfo>> {
        return repository.getPagingEarthquakes(filters)
    }
}