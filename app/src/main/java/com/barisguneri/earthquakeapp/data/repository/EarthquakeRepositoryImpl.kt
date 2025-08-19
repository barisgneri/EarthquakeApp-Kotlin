package com.barisguneri.earthquakeapp.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.barisguneri.earthquakeapp.common.ErrorType
import com.barisguneri.earthquakeapp.common.NetworkConstants.PAGE_SIZE
import com.barisguneri.earthquakeapp.common.Resource
import com.barisguneri.earthquakeapp.data.api.KandilliApiService
import com.barisguneri.earthquakeapp.data.mapper.toDetailModel
import com.barisguneri.earthquakeapp.data.paging.EarthquakePagingSource
import com.barisguneri.earthquakeapp.domain.model.EarthquakeDetail
import com.barisguneri.earthquakeapp.domain.model.EarthquakeInfo
import com.barisguneri.earthquakeapp.domain.repository.EarthquakeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException

class EarthquakeRepositoryImpl(private val apiService: KandilliApiService) : EarthquakeRepository {
    override fun getEarthquakes(): Flow<PagingData<EarthquakeInfo>> {
        return Pager(
            // 1. (Configuration)
            config = PagingConfig(
                prefetchDistance = 3,
                pageSize = PAGE_SIZE, // Her sayfada kaç eleman olacağını belirtir.
                initialLoadSize = PAGE_SIZE * 2, // İlk yüklemede daha fazla veri çekerek ekranın hızlı dolmasını sağlar.
                enablePlaceholders = false // Ağdan gelen verilerde genellikle false kullanılır.
            ),
            // 2. (PagingSource Factory)
            // Bu, Paging kütüphanesine "Veriyi nereden ve nasıl alacaksın?" sorusunun cevabıdır.
            // Bir lambda olarak verilir çünkü her veri yenilemesinde (invalidation)
            // yeni ve taze bir PagingSource oluşturulması gerekir.
            pagingSourceFactory = {
                EarthquakePagingSource(apiService)
            }
        ).flow
    }

    override fun getEarthquakeDetail(id: String): Flow<Resource<EarthquakeDetail>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiService.getEarthquakeDetail(id)
            if (response.isSuccessful) {
                val detailDto = response.body()?.result
                if (detailDto != null) {
                    val domainModel = detailDto.toDetailModel()
                    emit(Resource.Success(domainModel))
                } else {
                    emit(Resource.Error(ErrorType.ApiError(null, "Veri bulunamadı.")))
                }
            } else {
                emit(Resource.Error(ErrorType.HttpError(response.code(), response.message())))
            }
        } catch (e: Exception) {
            val errorType = when(e) {
                is IOException -> ErrorType.NoInternetConnection
                else -> ErrorType.Unknown(e)
            }
            emit(Resource.Error(errorType))
        }
    }
}