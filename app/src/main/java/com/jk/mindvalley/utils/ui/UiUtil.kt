package com.jk.mindvalley.utils.ui

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jk.mindvalley.R

object UiUtil {

    fun itemDecoration(recyclerView: RecyclerView): DividerItemDecoration {
        val context = recyclerView.context
        return DividerItemDecoration(
            context,
            (recyclerView.layoutManager as LinearLayoutManager).orientation
        ).apply { setDrawable(ContextCompat.getDrawable(context, R.drawable.divider_shape)!!) }
    }
}