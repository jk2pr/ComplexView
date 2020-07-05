package com.jk.mindvalley.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jk.mindvalley.data.categories.CategoriesData
import com.jk.mindvalley.data.channels.ChannelData
import com.jk.mindvalley.data.new_episode.NewEpisode
import com.jk.mindvalley.db.converters.CategoriesDataConverter
import com.jk.mindvalley.db.converters.ChannelDataConverter
import com.jk.mindvalley.db.converters.NewEpisodeDataConverter
import com.jk.mindvalley.db.dao.CategoryDao
import com.jk.mindvalley.db.dao.ChannelDataDao
import com.jk.mindvalley.db.dao.NewEpisodeDao

@Database(entities = [(NewEpisode::class),(ChannelData::class),(CategoriesData::class)], version = 1)
   @TypeConverters(NewEpisodeDataConverter::class,ChannelDataConverter::class, CategoriesDataConverter::class)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun newEpisodeDao(): NewEpisodeDao
        abstract fun channelDao(): ChannelDataDao
        abstract fun categoriesDao(): CategoryDao
    }