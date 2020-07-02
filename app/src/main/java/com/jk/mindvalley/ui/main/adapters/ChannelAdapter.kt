package com.jk.mindvalley.ui.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.jk.mindvalley.R
import com.jk.mindvalley.data.channels.Channels
import com.jk.mindvalley.data.channels.LatestMedia
import com.jk.mindvalley.data.channels.Series
import com.jk.mindvalley.utils.ui.UiUtil
import kotlinx.android.synthetic.main.item_channel.view.*

class ChannelAdapter(
    private val dataList: ArrayList<Channels>,
    private val requestManager: RequestManager
) : RecyclerView.Adapter<ChannelAdapter.DataViewHolder>() {


    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recyclerViewCourse: RecyclerView = itemView.course_horizontal_recyclerview
        private val recyclerViewSeries: RecyclerView = itemView.series_horizontal_recyclerview

        private fun setUpRecyclerView(recyclerView: RecyclerView) {
            recyclerView.apply {
                isNestedScrollingEnabled = false
                setHasFixedSize(true)
                setItemViewCacheSize(20)
                addItemDecoration(UiUtil.dec(this))
            }
        }

        fun bind(channels: Channels) {


            if (channels.series.isNotEmpty()) {
                val thumbnailUrl = channels.iconAsset?.url
                recyclerViewSeries.visibility = View.VISIBLE
                setUpRecyclerView(recyclerViewSeries)
                val seriesAdapter =
                    SeriesAdapter(arrayListOf(), requestManager).apply { setHasStableIds(true) }
                recyclerViewSeries.adapter = seriesAdapter
                renderSeries(
                    seriesAdapter,
                    itemView,
                    channels.title,
                    thumbnailUrl,
                    channels.mediaCount,
                    channels.series
                )
            }
            // renderSeries(channels.series)
            else {
                val thumbnailUrl = channels.iconAsset?.thumbnailUrl
                recyclerViewCourse.visibility = View.VISIBLE
                setUpRecyclerView(recyclerViewCourse)
                val courseAdapter =
                    CourseAdapter(arrayListOf(), requestManager).apply { setHasStableIds(true) }
                recyclerViewCourse.adapter = courseAdapter
                renderCourse(
                    courseAdapter,
                    itemView,
                    channels.title,
                    thumbnailUrl,
                    channels.mediaCount,
                    channels.latestMedia
                )
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {

        return DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_channel, parent,
                false
            )
        )
    }

    override fun getItemId(position: Int): Long {
        return dataList[position].id.hashCode().toLong()
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(dataList[position])

    fun addData(list: List<Channels>) {
        dataList.addAll(list)
    }

    private fun renderCourse(
        courseAdapter: CourseAdapter,
        itemView: View,
        title: String,
        thumbnailUrl: String?,
        mediaCount: Int,
        medias: List<LatestMedia>
    ) {

        courseAdapter.apply {
            addData(medias)
            notifyItemRangeInserted(0, medias.size - 1)
        }

        thumbnailUrl?.let {
            requestManager
                .load(thumbnailUrl)
                .into(itemView.image_channel)
        }

        itemView.textview_course_name.text = title
        itemView.episode_count.text = itemView.context.getString(R.string.episodes, mediaCount)


    }

    private fun renderSeries(
        seriesAdapter: SeriesAdapter,
        itemView: View,
        title: String,
        thumbnailUrl: String?,
        mediaCount: Int,
        series: List<Series>
    ) {

        seriesAdapter.apply {
            addData(series)
            notifyItemRangeInserted(0, series.size - 1)
        }

        thumbnailUrl?.let {
            requestManager
                .load(thumbnailUrl)
                .into(itemView.image_channel)
        }

        itemView.textview_course_name.text = title
        itemView.episode_count.text = itemView.context.getString(R.string.episodes, mediaCount)


    }
}
