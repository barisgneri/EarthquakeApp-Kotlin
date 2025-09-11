package com.barisguneri.earthquakeapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.barisguneri.earthquakeapp.data.local.model.EarthquakeEntity
import com.barisguneri.earthquakeapp.data.local.model.RemoteKey

@Database(
    entities = [EarthquakeEntity::class, RemoteKey::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun earthquakeDao(): EarthquakeDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}