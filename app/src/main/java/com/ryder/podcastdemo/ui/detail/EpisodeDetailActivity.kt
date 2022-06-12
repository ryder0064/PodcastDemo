package com.ryder.podcastdemo.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.R
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.util.Util
import com.ryder.podcastdemo.databinding.ActivityEpisodeDetailBinding
import com.ryder.podcastdemo.util.TAG
import com.ryder.podcastdemo.util.VideoCacheHelper
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class EpisodeDetailActivity : AppCompatActivity() {
    private val viewModel: EpisodeDetailViewModel by viewModel()
    private lateinit var binding: ActivityEpisodeDetailBinding
    private var player: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEpisodeDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        player = ExoPlayer.Builder(this).build()
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
            this, Util.getUserAgent(this, "Episodes")
        )
        val cacheDataSourceFactory: DataSource.Factory = CacheDataSource.Factory()
            .setCache(VideoCacheHelper.getCache(this))
            .setUpstreamDataSourceFactory(dataSourceFactory)

        binding.playerView.showController()

        viewModel.currentEpisode.observe(this) {
            binding.title.text = it.title
            binding.description.text = it.description
            binding.pubDate.text = it.pubDate
            binding.author.text = it.author

            Picasso.get()
                .load(it.thumbnail)
                .fit()
                .centerInside()
                .into(binding.image)

            val videoSource: MediaSource = ProgressiveMediaSource.Factory(cacheDataSourceFactory)
                .createMediaSource(MediaItem.fromUri(it.enclosure.link))

            binding.playerView.player = player?.apply {
                playWhenReady = true
                setMediaSource(videoSource)
                prepare()
                addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(state: Int) {
                        if (state == ExoPlayer.STATE_ENDED) {
                            viewModel.playNext()
                        }
                    }
                })
            }
        }

        binding.cusNext.setOnClickListener {
            viewModel.playNext()
        }

        binding.cusPrev.setOnClickListener {
            viewModel.playPrevious()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.playerView.player?.stop()
    }
}