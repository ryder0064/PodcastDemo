package com.ryder.podcastdemo.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ryder.podcastdemo.data.model.EpisodesResponse

class ItemsConverter {

    private val gson = Gson()

    @TypeConverter
    fun itemsToString(list: List<EpisodesResponse.Item>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun stringToItems(data: String): List<EpisodesResponse.Item> {
        val listType = object : TypeToken<List<EpisodesResponse.Item>>() {}.type
        return gson.fromJson(data, listType)
    }
}
