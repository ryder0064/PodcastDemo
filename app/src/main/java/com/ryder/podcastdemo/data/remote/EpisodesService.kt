package com.ryder.podcastdemo.data.remote

import com.ryder.podcastdemo.data.model.EpisodesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EpisodesService {
    @GET("/api.json")
    suspend fun getEpisodesResponse(@Query( "rss_url") query :String) : Response<EpisodesResponse>
}