package com.aston.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aston.data.model.episode.EpisodeInfoData
import kotlinx.coroutines.flow.Flow

@Dao
interface EpisodeDao {

    @Query("SELECT * FROM episodes_table WHERE id IN (:ids)")
    fun fetchEpisodes(ids: List<Int>): Flow<List<EpisodeInfoData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodes(episodes: List<EpisodeInfoData>)

}