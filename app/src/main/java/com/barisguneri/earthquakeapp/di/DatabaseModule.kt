package com.barisguneri.earthquakeapp.di

import android.content.Context
import androidx.room.Room
import com.barisguneri.earthquakeapp.data.local.AppDatabase
import com.barisguneri.earthquakeapp.data.local.EarthquakeDao
import com.barisguneri.earthquakeapp.data.local.RemoteKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "earthquake_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideEarthquakeDao(appDatabase: AppDatabase): EarthquakeDao {
        return appDatabase.earthquakeDao()
    }

    @Provides
    @Singleton
    fun provideRemoteKeyDao(appDatabase: AppDatabase): RemoteKeyDao {
        return appDatabase.remoteKeyDao()
    }
}