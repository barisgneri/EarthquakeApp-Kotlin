package com.barisguneri.earthquakeapp.domain.useCase

import com.barisguneri.earthquakeapp.domain.model.EarthquakeInfo
import com.barisguneri.earthquakeapp.domain.model.FilterState
import com.barisguneri.earthquakeapp.domain.repository.EarthquakeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMapEarthquakesUseCase @Inject constructor(
    private val repository: EarthquakeRepository
) {
    operator fun invoke(filters: FilterState): Flow<List<EarthquakeInfo>> {
        return repository.getMapEarthquakes(filters)
    }
}