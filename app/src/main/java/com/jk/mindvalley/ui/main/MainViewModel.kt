package com.jk.mindvalley.ui.main

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.jk.mindvalley.services.GetFinalData
import com.jk.mindvalley.data.FinalData
import com.jk.mindvalley.data.categories.CategoriesData
import com.jk.mindvalley.data.channels.ChannelData
import com.jk.mindvalley.data.new_episode.NewEpisode
import com.jk.mindvalley.data.response.Resource
import com.jk.mindvalley.db.dao.CategoryDao
import com.jk.mindvalley.db.dao.ChannelDataDao
import com.jk.mindvalley.db.dao.NewEpisodeDao
import com.jk.mindvalley.utils.NetworkHelper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class MainViewModel
@ViewModelInject constructor(
    private val getFinalData: GetFinalData,
    private val networkHelper: NetworkHelper,
    private val newEpisodeDao: NewEpisodeDao,
    private val channelDataDao: ChannelDataDao,
    private val categoryDao: CategoryDao,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _finalMutableLiveData = MutableLiveData<Resource<FinalData>>()

    val finalDataLiveData: LiveData<Resource<FinalData>>
        get() = _finalMutableLiveData

    @ExperimentalCoroutinesApi
    fun setState(mainState: MainState) {
        viewModelScope.launch {
            when (mainState) {
                is MainState.GetFinalDataEvent -> {
                    getFinalData.execute().onEach {
                        _finalMutableLiveData.value = it
                    }.launchIn(viewModelScope)
                }
            }
        }
    }

    sealed class MainState {

        object GetFinalDataEvent : MainState()

        object None : MainState()
    }
    /*private fun fetchData() :FinalData{
        viewModelScope.launch {
            _finalLiveData.postValue(Resource.Loading)
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
                                Resource.Error(Exception(response.errorBody().toString()))
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
                                Resource.Error(Exception(response.errorBody().toString()))
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
                                Resource.Error(Exception(response.errorBody().toString()))
                            Log.d("MainActivity ", response.errorBody().toString())
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()

                    }

                }
                val finalData = FinalData(allNewEpisode, allChannelData, allCategoriesData)
                _finalLiveData.value = Resource.Success(finalData)

            } else {
                _finalLiveData.postValue( Resource.Error(Exception("No Internet connection found")))
                //Get from Offline
                    val finalData = FinalData(
                        newEpisodeDao.getAllNewEpisodeAsync(),
                        channelDataDao.getAllChannelAsync(),
                        categoryDao.getAllCategoriesAsync()
                    )
                    _finalLiveData.value = Resource.Success(finalData)

            }
        }
    }*/


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