package com.jk.mindvalley.ui.main

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jk.mindvalley.data.channels.ChannelData
import com.jk.mindvalley.data.new_episode.NewEpisode
import com.jk.mindvalley.data.response.Resource
import com.jk.mindvalley.services.IApi
import com.jk.mindvalley.utils.NetworkHelper
import kotlinx.coroutines.launch

class MainViewModel
@ViewModelInject constructor(
    private val iApi: IApi,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    private val _newEpisodeMutableLiveData = MutableLiveData<Resource<NewEpisode>>()
    private val _channelMutableLiveData = MutableLiveData<Resource<ChannelData>>()

    val newEpisodeLiveData: LiveData<Resource<NewEpisode>>
        get() = _newEpisodeMutableLiveData
    val channelLiveData: LiveData<Resource<ChannelData>>
        get() = _channelMutableLiveData

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _newEpisodeMutableLiveData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                //New Episod
                iApi.getNewEpisodeAsync().let {
                    try {
                        val response = it.await()
                        if (response.isSuccessful) {
                            val posts = response.body()
                            _newEpisodeMutableLiveData.value = Resource.success(posts)
                        } else {
                            _newEpisodeMutableLiveData.value =
                                Resource.error(response.errorBody().toString(), null)
                            Log.d("MainActivity ", response.errorBody().toString())
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()

                    }
                }

                iApi.getChannelAsync().let {
                    try {
                        val response = it.await()
                        if (response.isSuccessful) {
                            val posts = response.body()
                            _channelMutableLiveData.value = Resource.success(posts)
                        } else {
                            _channelMutableLiveData.value =
                                Resource.error(response.errorBody().toString(), null)
                            Log.d("MainActivity ", response.errorBody().toString())
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()

                    }

                }
            } else _newEpisodeMutableLiveData.postValue(
                Resource.error(
                    "No internet connection",
                    null
                )
            )
        }
    }
}