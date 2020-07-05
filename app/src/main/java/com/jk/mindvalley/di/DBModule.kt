package com.jk.mindvalley.di

import android.content.Context
import androidx.room.Room
import com.jk.mindvalley.db.AppDatabase
import com.jk.mindvalley.db.dao.CategoryDao
import com.jk.mindvalley.db.dao.ChannelDataDao
import com.jk.mindvalley.db.dao.NewEpisodeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DBModule() {
    @Provides
    @Singleton
    fun getAppDB(@ApplicationContext app: Context): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "NEW_EPISODE_DB"
        ).build()

    }
    @Provides
    @Singleton
    fun getNewEpisodeDao( appDb: AppDatabase): NewEpisodeDao {
        return appDb.newEpisodeDao()

    } @Provides
    @Singleton
    fun getChannelDataDao( appDb: AppDatabase): ChannelDataDao {
        return appDb.channelDao()

    }

    @Provides
    @Singleton
    fun getCategoriesDataDao( appDb: AppDatabase): CategoryDao {
        return appDb.categoriesDao()

    }
}