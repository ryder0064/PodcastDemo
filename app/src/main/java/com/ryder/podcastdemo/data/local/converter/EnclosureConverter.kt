package com.ryder.podcastdemo.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ryder.podcastdemo.data.model.EpisodesResponse

class EnclosureConverter {

    private val gson = Gson()

    @TypeConverter
    fun enclosureToString(enclosure: EpisodesResponse.Item.Enclosure): String {
        return gson.toJson(enclosure)
    }

    @TypeConverter
    fun stringToEnclosure(data: String): EpisodesResponse.Item.Enclosure {
        val enclosureType = object : TypeToken<EpisodesResponse.Item.Enclosure>() {}.type
        return gson.fromJson(data, enclosureType)
    }
}
