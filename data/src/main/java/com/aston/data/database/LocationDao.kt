package com.aston.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aston.data.model.location.LocationInfoData

@Dao
interface LocationDao {

    @Query(
        "SELECT * FROM location_table WHERE name LIKE '%' || :locationName || '%' " +
                "AND type LIKE '%' || :locationType || '%' " +
                "AND dimension LIKE '%' || :locationDimension || '%'"
    )
    fun fetchLocations(
        locationName: String, locationType: String, locationDimension: String,
    ): PagingSource<Int, LocationInfoData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocations(locations: List<LocationInfoData>)

    @Query("DELETE FROM location_table")
    suspend fun deleteLocations()

}