package com.barisguneri.earthquakeapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.barisguneri.earthquakeapp.common.ErrorType
import com.barisguneri.earthquakeapp.common.NetworkConstants.PAGE_SIZE
import com.barisguneri.earthquakeapp.common.PagingException
import com.barisguneri.earthquakeapp.data.api.KandilliApiService
import com.barisguneri.earthquakeapp.data.mapper.toDomain
import com.barisguneri.earthquakeapp.domain.model.EarthquakeInfo
import retrofit2.HttpException
import java.io.IOException

class EarthquakePagingSource(private val apiService: KandilliApiService) : PagingSource<Int, EarthquakeInfo>() {

    override fun getRefreshKey(state: PagingState<Int, EarthquakeInfo>): Int? {
        // Genellikle bu standart implementasyon yeterlidir.
        // Ekranda görünen son pozisyona en yakın sayfayı bulmaya çalışır.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EarthquakeInfo> {
        val pageNumber = params.key ?: 1
        // Sayfa 1: skip = (1-1) * 20 = 0
        // Sayfa 2: skip = (2-1) * 20 = 20
        val skip = (pageNumber - 1) * PAGE_SIZE
        println(skip)
        val limit = PAGE_SIZE // params.loadSize da kullanılabilir ama sabit boyut daha tutarlıdır.
        return try {
            val response = apiService.getEarthquakes(skip = skip, limit = limit)
            if (response.isSuccessful){
                val resultDtoList = response.body()?.result ?: emptyList()
                val domainList = resultDtoList.mapNotNull { it.toDomain() } // Mapper burada kullanılıyor!
                LoadResult.Page(
                    data = domainList,
                    prevKey = if (pageNumber == 1) null else pageNumber - 1, // Önceki sayfanın anahtarı
                    nextKey = if (domainList.isEmpty()) null else pageNumber + 1 // Sonraki sayfanın anahtarı
                )
            }else{
                // HTTP hatası varsa (4xx, 5xx), bunu kendi özel hata modelime çevir.
                LoadResult.Error(PagingException(ErrorType.HttpError(response.code(), response.message())))
            }

        }catch (e: Exception){
            val errorType = when(e) {
                is IOException -> ErrorType.NoInternetConnection
                is HttpException -> ErrorType.HttpError(e.code(), e.message())
                else -> ErrorType.Unknown(e)
            }
            LoadResult.Error(PagingException(errorType))
        }
    }
}