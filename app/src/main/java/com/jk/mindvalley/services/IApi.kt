package com.jk.mindvalley.services

import com.jk.mindvalley.data.categories.CategoriesData
import com.jk.mindvalley.data.channels.ChannelData
import com.jk.mindvalley.data.new_episode.NewEpisode
import retrofit2.Response
import retrofit2.http.GET

interface IApi {
    @GET("z5AExTtw")
    suspend fun getNewEpisode(): NewEpisode

    @GET("Xt12uVhM")
    suspend fun getChannel(): ChannelData

    @GET("A0CgArX3")
    suspend fun getCategories(): CategoriesData

}