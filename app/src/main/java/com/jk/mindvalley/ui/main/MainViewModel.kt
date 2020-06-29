package com.jk.mindvalley.ui.main

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jk.mindvalley.data.new_episode.Data
import com.jk.mindvalley.data.response.Resource
import com.jk.mindvalley.services.IApi
import com.jk.mindvalley.utils.NetworkHelper
import kotlinx.coroutines.launch

class MainViewModel
@ViewModelInject constructor(
    private val iApi: IApi,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    private val _dataListsMutableLiveData = MutableLiveData<Resource<Data>>()
    val dataList: LiveData<Resource<Data>>
        get() = _dataListsMutableLiveData

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _dataListsMutableLiveData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                iApi.getNewEpisodeAsync().let {
                    try {
                        val response = it.await()
                        if (response.isSuccessful) {
                            val posts = response.body()
                            _dataListsMutableLiveData.value = Resource.success(posts)
                        } else {
                            _dataListsMutableLiveData.value =
                                Resource.error(response.errorBody().toString(), null)
                            Log.d("MainActivity ", response.errorBody().toString())
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()

                    }

                }
            } else _dataListsMutableLiveData.postValue(
                Resource.error(
                    "No internet connection",
                    null
                )
            )
        }
    }
}