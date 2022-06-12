package com.ryder.podcastdemo.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryder.podcastdemo.data.repository.EpisodesRepository
import com.ryder.podcastdemo.util.TAG
import kotlinx.coroutines.launch

class EpisodeDetailViewModel(private val repository: EpisodesRepository) : ViewModel(){
    val currentEpisode = repository.getCurrentEpisode()

    fun playNext() {
        viewModelScope.launch {
            repository.playNext()
        }
    }
    fun playPrevious() {
        viewModelScope.launch {
            repository.playPrevious()
        }
    }
}