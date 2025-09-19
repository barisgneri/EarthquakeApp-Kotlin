package com.barisguneri.earthquakeapp.di

import com.barisguneri.earthquakeapp.data.repository.EarthquakeRepositoryImpl
import com.barisguneri.earthquakeapp.domain.repository.EarthquakeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindEarthquakeRepository(myRepositoryImpl: EarthquakeRepositoryImpl): EarthquakeRepository
}