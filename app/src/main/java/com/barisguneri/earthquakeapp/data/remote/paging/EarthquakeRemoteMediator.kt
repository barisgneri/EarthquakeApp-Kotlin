package com.barisguneri.earthquakeapp.data.remote.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState.Loading.endOfPaginationReached
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.barisguneri.earthquakeapp.data.local.AppDatabase
import com.barisguneri.earthquakeapp.data.local.model.EarthquakeEntity
import com.barisguneri.earthquakeapp.data.local.model.RemoteKey
import com.barisguneri.earthquakeapp.data.mapper.toEntity
import com.barisguneri.earthquakeapp.data.remote.api.KandilliApiService

@OptIn(ExperimentalPagingApi::class)
class EarthquakeRemoteMediator(
    private val apiService: KandilliApiService,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, EarthquakeEntity>() {

    private val earthquakeDao = appDatabase.earthquakeDao()
    private val remoteKeyDao = appDatabase.remoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EarthquakeEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    Log.d("RemoteMediator", "APPEND: Son eleman -> $lastItem")

                    if (lastItem == null) {
                        Log.w("RemoteMediator", "APPEND tetiklendi ama son eleman null, Paging bitti sayılıyor.")
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    val remoteKey = remoteKeyDao.getRemoteKeyById(lastItem.id)
                    Log.d("RemoteMediator", "APPEND: Bulunan RemoteKey -> $remoteKey")

                    remoteKey?.nextKey ?: run {
                        Log.w("RemoteMediator", "APPEND: RemoteKey'in nextKey'i null, Paging bitti sayılıyor.")
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                }
            }

            Log.d("RemoteMediator", "API İsteği: Sayfa $page yükleniyor...")
            val response = apiService.getEarthquakes(
                skip = (page - 1) * state.config.pageSize,
                limit = state.config.pageSize
            )

            val earthquakesDto = response.body()?.result ?: emptyList()
            Log.d("RemoteMediator", "API Yanıtı: ${earthquakesDto.size} adet deprem geldi.")

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    earthquakeDao.clearAll()
                    remoteKeyDao.clearAll()
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                val remoteKeys = earthquakesDto.map {
                    RemoteKey(earthquakeId = it.earthquakeId, prevKey = prevKey, nextKey = nextKey)
                }

                val earthquakeEntities = earthquakesDto.map { it.toEntity() }
                remoteKeyDao.insertAll(remoteKeys)
                earthquakeDao.insertAll(earthquakeEntities)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: Exception) {
            Log.e("RemoteMediator", "Hata oluştu!", e)
            MediatorResult.Error(e)
        }
    }
}