package com.jk.mindvalley.ui.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.jk.mindvalley.R
import com.jk.mindvalley.data.channels.Series
import kotlinx.android.synthetic.main.item_new_episode.view.*

class SeriesAdapter(
    private val dataList: ArrayList<Series>,
    private val requestManager: RequestManager
) : RecyclerView.Adapter<SeriesAdapter.DataViewHolder>() {

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(series: Series) {
            itemView.textview_channel_name.text = series.title
            requestManager
                .load(series.coverAsset.url)
                .into(itemView.image_cover_asset)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_series, parent,
                false
            )
        )

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(dataList[position])

    fun addData(list: List<Series>) {
        dataList.addAll(list)
    }
}