package com.jk.mindvalley.services

import com.jk.mindvalley.data.new_episode.NewEpisode
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface IApi {
    @GET("z5AExTtw")
    fun getNewEpisodeAsync():
            Deferred<Response<NewEpisode>>
    // Response<List<Cat>>
}