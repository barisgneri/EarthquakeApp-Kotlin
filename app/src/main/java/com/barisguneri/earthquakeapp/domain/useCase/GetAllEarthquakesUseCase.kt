package com.barisguneri.earthquakeapp.domain.useCase

import com.barisguneri.earthquakeapp.core.common.Resource
import com.barisguneri.earthquakeapp.domain.model.EarthquakeInfo
import com.barisguneri.earthquakeapp.domain.repository.EarthquakeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllEarthquakesUseCase @Inject constructor(private val repository: EarthquakeRepository) {
    operator fun invoke() : Flow<Resource<List<EarthquakeInfo>>> = repository.getEarthquakes()
}