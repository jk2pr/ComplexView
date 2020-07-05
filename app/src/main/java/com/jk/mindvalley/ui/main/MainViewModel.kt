package com.jk.mindvalley.ui.main

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jk.mindvalley.data.categories.CategoriesData
import com.jk.mindvalley.data.channels.ChannelData
import com.jk.mindvalley.data.new_episode.NewEpisode
import com.jk.mindvalley.data.response.Resource
import com.jk.mindvalley.db.dao.CategoryDao
import com.jk.mindvalley.db.dao.ChannelDataDao
import com.jk.mindvalley.db.dao.NewEpisodeDao
import com.jk.mindvalley.services.IApi
import com.jk.mindvalley.utils.NetworkHelper
import kotlinx.coroutines.launch

class MainViewModel
@ViewModelInject constructor(
    private val iApi: IApi,
    private val networkHelper: NetworkHelper,
    private val newEpisodeDao: NewEpisodeDao,
    private val channelDataDao: ChannelDataDao,
    private val categoryDao: CategoryDao
) : ViewModel() {
    private val _newEpisodeMutableLiveData = MutableLiveData<Resource<NewEpisode>>()
    private val _channelMutableLiveData = MutableLiveData<Resource<ChannelData>>()
    private val _categoriesMutableLiveData = MutableLiveData<Resource<CategoriesData>>()

    val newEpisodeLiveData: LiveData<Resource<NewEpisode>>
        get() = _newEpisodeMutableLiveData
    val channelLiveData: LiveData<Resource<ChannelData>>
        get() = _channelMutableLiveData

    val categoriesLiveData: LiveData<Resource<CategoriesData>>
        get() = _categoriesMutableLiveData

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            _newEpisodeMutableLiveData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                //New Episode
                iApi.getNewEpisodeAsync().let {
                    try {
                        val response = it.await()
                        if (response.isSuccessful) {
                            val posts = response.body()
                            _newEpisodeMutableLiveData.value = Resource.success(posts)
                            //Save into DB for offline Display
                            posts?.let { saveNewEpisodeInToDb(posts) }
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
                            posts?.let { saveChannelInToDb(posts) }
                        } else {
                            _channelMutableLiveData.value =
                                Resource.error(response.errorBody().toString(), null)
                            Log.d("MainActivity ", response.errorBody().toString())
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()

                    }

                }
                iApi.getCategoriesAsync().let {
                    try {
                        val response = it.await()
                        if (response.isSuccessful) {
                            val posts = response.body()
                            _categoriesMutableLiveData.value = Resource.success(posts)
                            posts?.let { saveNewCategoriesInToDb(posts) }

                        } else {
                            _categoriesMutableLiveData.value =
                                Resource.error(response.errorBody().toString(), null)
                            Log.d("MainActivity ", response.errorBody().toString())
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()

                    }

                }
            } else {
                _newEpisodeMutableLiveData.postValue(Resource.error("No internet connection", null))
                //Get from Offline
                viewModelScope.launch {
                    _newEpisodeMutableLiveData.value =
                        Resource.success(newEpisodeDao.getAllNewEpisodeAsync())

                    _channelMutableLiveData.value =
                        Resource.success(channelDataDao.getAllChannelAsync())

                    _categoriesMutableLiveData.value =
                        Resource.success(categoryDao.getAllCategoriesAsync())
                }
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