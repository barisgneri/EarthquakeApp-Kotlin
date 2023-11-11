package com.barisguneri.earthquakeapp.di

import com.barisguneri.earthquakeapp.repository.EarthquakeRepository
import com.barisguneri.earthquakeapp.services.EarthquakeApi
import com.barisguneri.earthquakeapp.util.Constants
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideEarthquakeRepository(
        api: EarthquakeApi
    ) = EarthquakeRepository(api)

    @Singleton
    @Provides
    fun provideEarthquakeApi() : EarthquakeApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(EarthquakeApi::class.java)
    }
}