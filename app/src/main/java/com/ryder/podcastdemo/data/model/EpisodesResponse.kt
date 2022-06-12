package com.ryder.podcastdemo.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "episodes_table")
data class EpisodesResponse(
    @PrimaryKey
    val id: Int = 0,
    @SerializedName("status")
    val status: String,
    @SerializedName("feed")
    @ColumnInfo(name = "feed")
    val feed: Feed,
    @SerializedName("items")
    @ColumnInfo(name = "items")
    val items: List<Item>,
) {
    data class Feed(
        @ColumnInfo(name = "title")
        @SerializedName("title")
        val title: String,
        @ColumnInfo(name = "description")
        @SerializedName("description")
        val description: String
    )

    data class Item(
        @ColumnInfo(name = "title")
        @SerializedName("title")
        val title: String,
        @ColumnInfo(name = "pubDate")
        @SerializedName("pubDate")
        val pubDate: String,
        @ColumnInfo(name = "description")
        @SerializedName("description")
        val description: String,
        @ColumnInfo(name = "thumbnail")
        @SerializedName("thumbnail")
        val thumbnail: String,
        @ColumnInfo(name = "author")
        @SerializedName("author")
        val author: String,
        @ColumnInfo(name = "enclosure")
        @SerializedName("enclosure")
        val enclosure: Enclosure
    ) {
        data class Enclosure(
            @ColumnInfo(name = "link")
            @SerializedName("link")
            val link: String
        )
    }
}