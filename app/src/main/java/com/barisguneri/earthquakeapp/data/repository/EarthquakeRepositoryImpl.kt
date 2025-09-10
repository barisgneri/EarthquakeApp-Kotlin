package com.barisguneri.earthquakeapp.data.repository


import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.barisguneri.earthquakeapp.core.common.ErrorType
import com.barisguneri.earthquakeapp.core.common.NetworkConstants.PAGE_SIZE
import com.barisguneri.earthquakeapp.core.common.Resource
import com.barisguneri.earthquakeapp.data.local.AppDatabase
import com.barisguneri.earthquakeapp.data.remote.api.KandilliApiService
import com.barisguneri.earthquakeapp.data.mapper.toDetailModel
import com.barisguneri.earthquakeapp.data.mapper.toDomain
import com.barisguneri.earthquakeapp.data.mapper.toDomainModel
import com.barisguneri.earthquakeapp.data.remote.paging.EarthquakeRemoteMediator
import com.barisguneri.earthquakeapp.domain.model.EarthquakeDetail
import com.barisguneri.earthquakeapp.domain.model.EarthquakeInfo
import com.barisguneri.earthquakeapp.domain.model.FilterState
import com.barisguneri.earthquakeapp.domain.repository.EarthquakeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class EarthquakeRepositoryImpl @Inject constructor(
    private val apiService: KandilliApiService,
    private val appDatabase: AppDatabase
) : EarthquakeRepository {

    private val earthquakeDao = appDatabase.earthquakeDao()

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagingEarthquakes(filters: FilterState): Flow<PagingData<EarthquakeInfo>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
            ),

            remoteMediator = EarthquakeRemoteMediator(
                apiService = apiService,
                appDatabase = appDatabase
            ),

            pagingSourceFactory = {
                val startTimeStamp = System.currentTimeMillis() - filters.timeRange.duration.inWholeMilliseconds

                earthquakeDao.getFilteredAndSortedEarthquakes(
                    searchQuery = filters.searchQuery,
                    minMagnitude = filters.minMagnitude.toDouble(),
                    startTimeStamp = startTimeStamp,
                    sortBy = filters.sortBy.name
                )
            }
        ).flow
            .map { pagingData ->
                pagingData.map { entity -> entity.toDomainModel() }
            }
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
            val errorType = when (e) {
                is IOException -> ErrorType.NoInternetConnection
                else -> ErrorType.Unknown(
                    apiCode = 404,
                    message = e.localizedMessage ?: "Unknown error"
                )
            }
            emit(Resource.Error(errorType))
        }
    }

    override fun getEarthquakes(): Flow<Resource<List<EarthquakeInfo>>> = flow {
        emit(Resource.Loading())
        try {
            val apiResponse = apiService.getEarthquakes(skip = 0, limit = 0)
            if (apiResponse.isSuccessful) {
                val resultDtoList = apiResponse.body()?.result ?: emptyList()
                val domainList = resultDtoList.map { it.toDomain() }
                emit(Resource.Success(domainList))
            } else {
                emit(
                    Resource.Error(
                        ErrorType.HttpError(
                            apiResponse.code(),
                            apiResponse.errorBody().toString()
                        )
                    )
                )
            }
        } catch (e: IOException) {
            emit(Resource.Error(ErrorType.HttpError(404, e.localizedMessage)))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val statusCode = e.code()
            val apiError = ErrorType.HttpError(statusCode, errorBody)
            emit(Resource.Error(apiError))
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    ErrorType.Unknown(
                        apiCode = 404,
                        message = e.localizedMessage ?: "Unknown error"
                    )
                )
            )
        }
    }.flowOn(Dispatchers.IO)
}