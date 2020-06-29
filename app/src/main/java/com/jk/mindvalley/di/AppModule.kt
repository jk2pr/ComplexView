package com.jk.mindvalley.di

import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.jk.mindvalley.MindValleyApplication
import com.jk.mindvalley.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {
    @Provides
    fun provideGlideRequestOptions(): RequestOptions {
        return RequestOptions()
            .error(R.drawable.ic_terrain_black_24dp)
    }

    @Provides
    fun provideGlide(
        application: MindValleyApplication,
        requestOptions: RequestOptions
    ): RequestManager {
        return Glide.with(application).setDefaultRequestOptions(requestOptions)
    }
}