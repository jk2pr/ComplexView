package com.jk.mindvalley.data

import com.jk.mindvalley.data.categories.CategoriesData
import com.jk.mindvalley.data.channels.ChannelData
import com.jk.mindvalley.data.new_episode.NewEpisode

data class FinalData(
    val newEpisodeData:  NewEpisode?,val channelData: ChannelData?,val categoryData: CategoriesData?)
