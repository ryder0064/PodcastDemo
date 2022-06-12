package com.ryder.podcastdemo.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ryder.podcastdemo.data.local.dao.EpisodesDao
import com.ryder.podcastdemo.data.model.EpisodesResponse
import com.ryder.podcastdemo.data.remote.EpisodesService
import com.ryder.podcastdemo.util.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class EpisodesRepository(
    private val episodesService: EpisodesService,
    private val episodesDao: EpisodesDao
) {
    private var currentPosition = -1
    private val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private val warningMessage = MutableLiveData<String>()
    private val currentEpisode = MutableLiveData<EpisodesResponse.Item>()

    fun getEpisodes(): LiveData<EpisodesResponse> = episodesDao.getEpisodes()
    fun getCurrentEpisode(): LiveData<EpisodesResponse.Item> = currentEpisode
    fun getLoadingStatus(): LiveData<Boolean> = isLoading
    fun getWarningMessage(): MutableLiveData<String> = warningMessage

    suspend fun setCurrentEpisode(position: Int) {
        currentPosition = position
        withContext(Dispatchers.IO) {
            currentEpisode.postValue(episodesDao.getEpisodesSync().items[currentPosition])
        }
    }

    suspend fun playNext() {
        withContext(Dispatchers.IO) {
            if (currentPosition != episodesDao.getEpisodesSync().items.size - 1) {
                currentEpisode.postValue(episodesDao.getEpisodesSync().items[++currentPosition])
            }
        }
    }

    suspend fun playPrevious() {
        if(currentPosition == 0) return
        withContext(Dispatchers.IO) {
            currentEpisode.postValue(episodesDao.getEpisodesSync().items[--currentPosition])
        }
    }

    suspend fun refreshData() {
        isLoading.value = true
        withContext(Dispatchers.IO) {
            try {
                fetchEpisodes()
            } catch (exception: Exception) {
                Log.e(TAG, "Error: ${exception.message}")
                when (exception) {
                    is IOException -> warningMessage.postValue("Network problem occurred")
                    else -> {
                        warningMessage.postValue("Unexpected problem occurred")
                    }
                }
            }
            isLoading.postValue(false)
        }
    }


    private suspend fun fetchEpisodes() {
        val response = episodesService.getEpisodesResponse("https://feeds.soundcloud.com/users/soundcloud:users:322164009/sounds.rss")
        if (response.isSuccessful) {
            val episodes = response.body()
            if (episodes != null && episodes.status == "ok") {
                episodesDao.insertEpisodes(episodes)
            } else {
                episodesDao.deleteEpisodes()
            }
        } else {
            Log.e(TAG, "Error: ${response.errorBody()}")
            warningMessage.postValue("Unexpected problem occurred")
        }
    }
}