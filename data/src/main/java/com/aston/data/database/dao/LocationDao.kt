package com.aston.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aston.data.model.location.LocationInfoData
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Query(
        "SELECT * FROM location_table WHERE name LIKE '%' || :locationName || '%' " + "AND type LIKE '%' || :locationType || '%' " + "AND dimension LIKE '%' || :locationDimension || '%'"
    )
    fun fetchLocations(
        locationName: String, locationType: String, locationDimension: String,
    ): PagingSource<Int, LocationInfoData>

    @Query("SELECT * FROM location_table WHERE id LIKE '%' || :locationId || '%'")
    fun fetchLocationById(locationId: Int): Flow<LocationInfoData>

    @Query("SELECT * FROM location_table WHERE name LIKE '%' || :originLocationName || '%'")
    fun fetchOriginLocationByName(originLocationName: String): Flow<LocationInfoData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocations(locations: List<LocationInfoData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: LocationInfoData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOriginLocation(originLocation: LocationInfoData)

}