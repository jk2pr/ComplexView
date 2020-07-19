package com.jk.mindvalley.ui.main

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jk.mindvalley.data.FinalData
import com.jk.mindvalley.data.categories.CategoriesData
import com.jk.mindvalley.data.channels.ChannelData
import com.jk.mindvalley.data.new_episode.NewEpisode
import com.jk.mindvalley.data.response.Resource
import com.jk.mindvalley.db.dao.CategoryDao
import com.jk.mindvalley.db.dao.ChannelDataDao
import com.jk.mindvalley.db.dao.NewEpisodeDao
import com.jk.mindvalley.services.IApi
import com.jk.mindvalley.utils.NetworkHelper
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class MainViewModel
@ViewModelInject constructor(
    private val iApi: IApi,
    private val networkHelper: NetworkHelper,
    private val newEpisodeDao: NewEpisodeDao,
    private val channelDataDao: ChannelDataDao,
    private val categoryDao: CategoryDao
) : ViewModel() {
    private val _finalLiveData = MutableLiveData<Resource<FinalData>>()

    val fianlDataLiveData: LiveData<Resource<FinalData>>
        get() = _finalLiveData

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _finalLiveData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                //New Episode
                val newEpisodeDeferred = async { iApi.getNewEpisodeAsync() }
                val channelDeferred = async { iApi.getChannelAsync() }
                val cateDeferred = async { iApi.getCategoriesAsync() }

                var allNewEpisode: NewEpisode? = null
                var allChannelData: ChannelData? = null
                var allCategoriesData: CategoriesData? = null
                newEpisodeDeferred.await().let {
                    try {
                        val response = it
                        if (response.isSuccessful) {
                            val posts = response.body()
                            allNewEpisode = posts
                            //Save into DB for offline Display
                            posts?.let { saveNewEpisodeInToDb(posts) }
                        } else {
                            _finalLiveData.value =
                                Resource.error(response.errorBody().toString(), null)
                            Log.d("MainActivity ", response.errorBody().toString())
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                channelDeferred.await().let {
                    try {
                        val response = it
                        if (response.isSuccessful) {
                            val posts = response.body()
                            allChannelData = posts
                            posts?.let { saveChannelInToDb(posts) }
                        } else {
                            _finalLiveData.value =
                                Resource.error(response.errorBody().toString(), null)
                            Log.d("MainActivity ", response.errorBody().toString())
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()

                    }

                }
                cateDeferred.await().let {
                    try {
                        val response = it
                        if (response.isSuccessful) {
                            val posts = response.body()
                            allCategoriesData = posts
                            posts?.let { saveNewCategoriesInToDb(posts) }

                        } else {
                            _finalLiveData.value =
                                Resource.error(response.errorBody().toString(), null)
                            Log.d("MainActivity ", response.errorBody().toString())
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()

                    }

                }
                val finalData = FinalData(allNewEpisode, allChannelData, allCategoriesData)
                _finalLiveData.value = Resource.success(finalData)

            } else {
                _finalLiveData.postValue(Resource.error("No internet connection", null))
                //Get from Offline
                    val finalData = FinalData(
                        newEpisodeDao.getAllNewEpisodeAsync(),
                        channelDataDao.getAllChannelAsync(),
                        categoryDao.getAllCategoriesAsync()
                    )
                    _finalLiveData.value = Resource.success(finalData)

            }
        }
    }


    private suspend fun saveNewEpisodeInToDb(posts: NewEpisode) {
        viewModelScope.launch { newEpisodeDao.insert(posts) }

    }

    private suspend fun saveChannelInToDb(posts: ChannelData) {
        viewModelScope.launch { channelDataDao.insert(posts) }

    }

    private suspend fun saveNewCategoriesInToDb(posts: CategoriesData) {
        viewModelScope.launch { categoryDao.insert(posts) }

    }
}