package com.jk.mindvalley.ui.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.jk.mindvalley.R
import com.jk.mindvalley.data.new_episode.Media
import kotlinx.android.synthetic.main.item_new_episode.view.*

class NewEpisodeAdapter(
    private val dataList: ArrayList<Media>,
    private val requestManager: RequestManager
) : RecyclerView.Adapter<NewEpisodeAdapter.DataViewHolder>() {

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(media: Media) {
            itemView.textView_name.text = media.title
            itemView.textview_channel_name.text = media.channel.title
            requestManager
                .load(media.coverAsset.url)
                .into(itemView.image_cover_asset)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_new_episode, parent,
                false
            )
        )

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(dataList[position])

    fun addData(list: List<Media>) {
        dataList.addAll(list)
    }
}