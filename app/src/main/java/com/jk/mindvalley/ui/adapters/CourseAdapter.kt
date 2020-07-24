package com.jk.mindvalley.ui.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.jk.mindvalley.R
import com.jk.mindvalley.data.channels.LatestMedia
import kotlinx.android.synthetic.main.item_course.view.*

class CourseAdapter(
    private val dataList: ArrayList<LatestMedia>,
    private val requestManager: RequestManager
) : RecyclerView.Adapter<CourseAdapter.DataViewHolder>() {

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(latestMedia: LatestMedia) {
            itemView.textview_course_name.text = latestMedia.title
            requestManager
                .load(latestMedia.coverAsset.url)
                .into(itemView.image_cover_asset)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_course, parent,
                false
            )
        )

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(dataList[position])

    fun addData(list: List<LatestMedia>) {
        dataList.addAll(list)
    }

}