package com.jk.mindvalley.services

import com.jk.mindvalley.data.categories.CategoriesData
import com.jk.mindvalley.data.channels.ChannelData
import com.jk.mindvalley.data.new_episode.NewEpisode
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface IApi {
    @GET("z5AExTtw")
    fun getNewEpisodeAsync():
            Deferred<Response<NewEpisode>>

    @GET("Xt12uVhM")
    fun getChannelAsync():
            Deferred<Response<ChannelData>>

    @GET("A0CgArX3")
    fun getCategoriesAsync():
            Deferred<Response<CategoriesData>>

    // Response<List<Cat>>
}