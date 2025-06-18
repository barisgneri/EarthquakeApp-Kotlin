package com.barisguneri.earthquakeapp.domain.useCase

import androidx.paging.PagingData
import androidx.paging.filter
import com.barisguneri.earthquakeapp.domain.model.EarthquakeInfo
import com.barisguneri.earthquakeapp.domain.repository.EarthquakeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetEarthquakesUseCase @Inject constructor(
    private val repository: EarthquakeRepository
) {
    operator fun invoke(minMagnitude: Double = 0.0): Flow<PagingData<EarthquakeInfo>> {
        // Sadece veriyi getirmekle kalmaz, aynı zamanda iş mantığı da uygulayabiliriz.

        val rawPagingDataFlow = repository.getEarthquakes()

        //iş mantığı: Sadece belirli bir büyüklükten yüksek depremleri filtrele
        if (minMagnitude > 0) {
            return rawPagingDataFlow.map { pagingData ->
                pagingData.filter { earthquake ->
                    earthquake.magnitude >= minMagnitude
                }
            }
        }

        // Eğer filtre yoksa, veriyi olduğu gibi döndür.
        return rawPagingDataFlow
    }
}