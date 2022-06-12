package com.ryder.podcastdemo.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ryder.podcastdemo.data.model.EpisodesResponse

class FeedConverter {

    private val gson = Gson()

    @TypeConverter
    fun feedToString(feed: EpisodesResponse.Feed): String {
        return gson.toJson(feed)
    }

    @TypeConverter
    fun stringToFeed(data: String): EpisodesResponse.Feed {
        val feedType = object : TypeToken<EpisodesResponse.Feed>() {}.type
        return gson.fromJson(data, feedType)
    }
}
