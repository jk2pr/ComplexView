package com.jk.mindvalley.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.jk.mindvalley.MindValleyApplication
import com.jk.mindvalley.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule  {
    @Provides
    @Singleton
    fun provideGlideRequestOptions(): RequestOptions {
        return RequestOptions()
            .error(R.drawable.ic_terrain_black_24dp)
    }

    @Provides
    @Singleton
    fun provideGlide(
        @ApplicationContext
        application: Context,
        requestOptions: RequestOptions
    ): RequestManager {
        return Glide.with(application).setDefaultRequestOptions(requestOptions)
    }
}