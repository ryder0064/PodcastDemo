package com.ryder.podcastdemo.ui.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryder.podcastdemo.data.repository.EpisodesRepository
import com.ryder.podcastdemo.util.TAG
import kotlinx.coroutines.launch

class EpisodesViewModel(private val repository: EpisodesRepository) : ViewModel() {
    val episodes = repository.getEpisodes()
    val isLoading = repository.getLoadingStatus()
    val warningMessage = repository.getWarningMessage()

    init {
        refreshEpisodes()
    }

    fun setCurrentEpisode(position: Int) {
        viewModelScope.launch {
            repository.setCurrentEpisode(position)
        }
    }

    private fun refreshEpisodes() = viewModelScope.launch { repository.refreshData() }
}