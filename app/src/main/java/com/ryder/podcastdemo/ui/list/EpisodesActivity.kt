package com.ryder.podcastdemo.ui.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ryder.podcastdemo.databinding.ActivityEpisodesBinding
import com.ryder.podcastdemo.ui.detail.EpisodeDetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class EpisodesActivity : AppCompatActivity() {
    private val viewModel: EpisodesViewModel by viewModel()
    private lateinit var binding: ActivityEpisodesBinding
    private lateinit var viewAdapter: EpisodesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEpisodesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewAdapter = EpisodesAdapter(object :EpisodesAdapter.OnItemClickListener {
            override fun onItemClicked(position: Int) {
                viewModel.setCurrentEpisode(position)
                startActivity(Intent(this@EpisodesActivity, EpisodeDetailActivity::class.java))
            }
        })
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(this@EpisodesActivity)
            adapter = viewAdapter
        }

        viewModel.episodes.observe(this) {
            it?.apply {
                binding.title.text = feed.title
                binding.description.text = feed.description
                viewAdapter.setData(items)
            }
        }

        viewModel.isLoading.observe(this) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        viewModel.warningMessage.observe(this) { text ->
            Toast.makeText(this, text, Toast.LENGTH_LONG).show()
        }
    }
}