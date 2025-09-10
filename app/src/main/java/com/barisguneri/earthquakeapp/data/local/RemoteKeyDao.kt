package com.barisguneri.earthquakeapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.barisguneri.earthquakeapp.data.local.model.RemoteKey

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<RemoteKey>)

    @Query("SELECT * FROM remote_keys WHERE earthquakeId = :id")
    suspend fun getRemoteKeyById(id: String): RemoteKey?

    @Query("DELETE FROM remote_keys")
    suspend fun clearAll()

}