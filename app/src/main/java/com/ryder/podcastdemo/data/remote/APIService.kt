package com.ryder.podcastdemo.data.remote

import org.json.JSONObject
import retrofit2.http.GET

interface APIService {
    @GET("")
    suspend fun getEpisodeResponse(): JSONObject
}