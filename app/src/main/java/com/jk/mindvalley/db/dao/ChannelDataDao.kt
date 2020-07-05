package com.jk.mindvalley.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jk.mindvalley.data.channels.ChannelData
import com.jk.mindvalley.data.new_episode.NewEpisode


@Dao
interface ChannelDataDao {
     @Query("SELECT * FROM ChannelData")
     suspend fun getAllChannelAsync(): ChannelData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(channelData: ChannelData)

}