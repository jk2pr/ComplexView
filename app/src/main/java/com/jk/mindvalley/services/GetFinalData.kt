package com.jk.mindvalley.services

import com.jk.mindvalley.data.FinalData
import com.jk.mindvalley.data.response.Resource
import com.jk.mindvalley.services.IApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetFinalData
@Inject constructor(
    private val iApi: IApi
) {

    /**
     * Show loading
     * Get Data from network
     * Show FinalData
     */
    suspend fun execute(): Flow<Resource<FinalData>> = flow {
        emit(Resource.Loading)

        try {
            val newEpisode = iApi.getNewEpisode()
            val categories = iApi.getCategories()
            val channels = iApi.getChannel()
            emit(
                Resource.Success(
                    FinalData(
                        newEpisodeData = newEpisode,
                        categoryData = categories,
                        channelData = channels
                    )
                )
            )
        } catch (e:Exception){
            emit(Resource.Error(e))
        }



    }

}