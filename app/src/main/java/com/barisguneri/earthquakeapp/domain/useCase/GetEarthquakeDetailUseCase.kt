package com.barisguneri.earthquakeapp.domain.useCase

import com.barisguneri.earthquakeapp.core.common.Resource
import com.barisguneri.earthquakeapp.domain.model.EarthquakeDetail
import com.barisguneri.earthquakeapp.domain.repository.EarthquakeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEarthquakeDetailUseCase @Inject constructor(
    private val repository: EarthquakeRepository
) {
    operator fun invoke(id: String): Flow<Resource<EarthquakeDetail>> {
        return repository.getEarthquakeDetail(id)
    }
}