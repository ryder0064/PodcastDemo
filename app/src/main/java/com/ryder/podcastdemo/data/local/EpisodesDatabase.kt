package com.ryder.podcastdemo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ryder.podcastdemo.data.local.converter.EnclosureConverter
import com.ryder.podcastdemo.data.local.converter.FeedConverter
import com.ryder.podcastdemo.data.local.converter.ItemsConverter
import com.ryder.podcastdemo.data.local.dao.EpisodesDao
import com.ryder.podcastdemo.data.model.EpisodesResponse

@Database(entities = [EpisodesResponse::class], version = 1)
@TypeConverters(value = [ItemsConverter::class, FeedConverter::class, EnclosureConverter::class])
abstract class EpisodesDatabase : RoomDatabase() {
    abstract fun episodesDao(): EpisodesDao
}
