package com.ryder.podcastdemo.ui.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ryder.podcastdemo.R
import com.ryder.podcastdemo.data.model.EpisodesResponse
import com.squareup.picasso.Picasso

class EpisodesAdapter(private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<EpisodesAdapter.ViewHolder>() {

    private var episodes: List<EpisodesResponse.Item> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_episode_view, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(episodes[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClicked(position)
        }
    }

    override fun getItemCount(): Int = episodes.size

    @SuppressLint("NotifyDataSetChanged") //always update all
    fun setData(data: List<EpisodesResponse.Item>) {
        episodes = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        private val thumbnail: ImageView = itemView.findViewById(R.id.thumbnail)

        fun bind(item: EpisodesResponse.Item) {
            title.text = item.title
            Picasso.get()
                .load(item.thumbnail)
                .fit()
                .centerInside()
                .into(thumbnail)
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int)
    }
}