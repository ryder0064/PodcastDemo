package com.ryder.podcastdemo.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ryder.podcastdemo.data.model.EpisodesResponse

@Dao
interface EpisodesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEpisodes(episodesResponse: EpisodesResponse)

    @Query("SELECT * FROM episodes_table")
    fun getEpisodes(): LiveData<EpisodesResponse>

    @Query("SELECT * FROM episodes_table")
    fun getEpisodesSync(): EpisodesResponse

    @Query("DELETE FROM episodes_table")
    fun deleteEpisodes()
}
