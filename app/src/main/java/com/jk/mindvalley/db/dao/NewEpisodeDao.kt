package com.jk.mindvalley.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jk.mindvalley.data.new_episode.NewEpisode


@Dao
interface NewEpisodeDao {
     @Query("SELECT * FROM NewEpisode")
     suspend fun getAllNewEpisodeAsync(): NewEpisode

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(newEpisode: NewEpisode)

}