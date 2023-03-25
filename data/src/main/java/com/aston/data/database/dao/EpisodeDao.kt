package com.aston.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aston.data.model.episode.EpisodeInfoData
import kotlinx.coroutines.flow.Flow

@Dao
interface EpisodeDao {

    @Query("SELECT * FROM episodes_table WHERE name LIKE '%' || :episodeName || '%' " +
            "AND episode LIKE '%' || :episodeNumber || '%'")
    fun fetchEpisodes(
        episodeName: String,
        episodeNumber: String
    ): PagingSource<Int, EpisodeInfoData>

    @Query("SELECT * FROM episodes_table WHERE id IN (:ids)")
    fun fetchEpisodesByIds(ids: List<Int>): Flow<List<EpisodeInfoData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEpisodes(episodes: List<EpisodeInfoData>)

}