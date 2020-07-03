package com.jk.mindvalley.ui.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jk.mindvalley.R
import com.jk.mindvalley.data.categories.Categories
import kotlinx.android.synthetic.main.item_channel.view.*

class CategoryAdapter(
    private val dataList: ArrayList<Categories>
) : RecyclerView.Adapter<CategoryAdapter.DataViewHolder>() {


    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(categories: Categories) {
            (itemView as TextView).text = categories.name
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {

        return DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_categories, parent,
                false
            )
        )
    }


    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(dataList[position])

    fun addData(list: List<Categories>) {
        dataList.addAll(list)
    }


}
