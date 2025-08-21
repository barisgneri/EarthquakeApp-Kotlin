package com.barisguneri.earthquakeapp.di

import com.barisguneri.earthquakeapp.data.api.KandilliApiService
import com.barisguneri.earthquakeapp.core.common.NetworkConstants.BASE_URL
import com.barisguneri.earthquakeapp.data.repository.EarthquakeRepositoryImpl
import com.barisguneri.earthquakeapp.domain.repository.EarthquakeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideEarthquakeApi(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): KandilliApiService {
        return retrofit.create(KandilliApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideEarthquakeRepository(apiService: KandilliApiService): EarthquakeRepository {
        return EarthquakeRepositoryImpl(apiService = apiService)
    }
}