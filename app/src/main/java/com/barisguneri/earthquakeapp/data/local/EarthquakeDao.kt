package com.barisguneri.earthquakeapp.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.barisguneri.earthquakeapp.data.local.model.EarthquakeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EarthquakeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(earthquakes: List<EarthquakeEntity>)

    @Query("DELETE FROM earthquakes")
    suspend fun clearAll()

    @Query("""
        SELECT * FROM earthquakes
        WHERE
            (:searchQuery = '' OR title LIKE '%' || :searchQuery || '%') AND
            magnitude <= :minMagnitude AND
            dateTime >= :startTimeStamp
        ORDER BY
            CASE WHEN :sortBy = 'DATE' THEN date END DESC,
            CASE WHEN :sortBy = 'MAGNITUDE' THEN magnitude END DESC
    """)
    fun getFilteredAndSortedEarthquakes(
        searchQuery: String,
        minMagnitude: Double,
        startTimeStamp: Long,
        sortBy: String
    ): PagingSource<Int, EarthquakeEntity>

    @Query("""
        SELECT * FROM earthquakes
        WHERE
            (:searchQuery = '' OR title LIKE '%' || :searchQuery || '%')
    """)
    fun getFilteredAndSortedEarthquakesList(
        searchQuery: String,
    ): Flow<List<EarthquakeEntity>>
}