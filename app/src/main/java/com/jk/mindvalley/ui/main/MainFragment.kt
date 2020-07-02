package com.jk.mindvalley.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.jk.mindvalley.R
import com.jk.mindvalley.data.new_episode.Media
import com.jk.mindvalley.data.response.Status
import com.jk.mindvalley.ui.main.adapters.ChannelAdapter
import com.jk.mindvalley.ui.main.adapters.NewEpisodeAdapter
import com.jk.mindvalley.ui.main.adapters.SeriesAdapter
import com.jk.mindvalley.utils.ui.UiUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val mainViewModel: MainViewModel by navGraphViewModels(R.id.nav) { defaultViewModelProviderFactory }
    private lateinit var newEpisodeAdapter: NewEpisodeAdapter
    private lateinit var channelAdapter: ChannelAdapter

    @Inject
    lateinit var requestManager: RequestManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    private fun setupObserver() {
        mainViewModel.newEpisodeLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { res -> renderNewEpisode(res.data.media) }
                    showLoader(false)
                }
                Status.LOADING -> {
                    showLoader(true)
                }
                Status.ERROR -> {
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        mainViewModel.channelLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { res ->
                        channelAdapter.addData(res.data.channels)
                        channelAdapter.notifyItemRangeInserted(0,res.data.channels.size-1)
                    }
                }
                Status.LOADING -> {
                }
                Status.ERROR -> {
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    private fun showLoader(isShowing: Boolean) {
        if (isShowing) {
            main.visibility = View.GONE
            progress_circular.visibility = View.VISIBLE
        } else {
            main.visibility = View.VISIBLE
            progress_circular.visibility = View.GONE

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObserver()
    }


    private fun setupUI() {


        new_episode_recyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        channel_recyclerview.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        newEpisodeAdapter = NewEpisodeAdapter(arrayListOf(), requestManager)
        channelAdapter = ChannelAdapter(arrayListOf(), requestManager)

        new_episode_recyclerView.addItemDecoration(UiUtil.dec(new_episode_recyclerView))
        channel_recyclerview.addItemDecoration(UiUtil.dec(channel_recyclerview))


        new_episode_recyclerView.adapter = newEpisodeAdapter
        channel_recyclerview.adapter = channelAdapter

    }

    private fun renderNewEpisode(medias: List<Media>) {
        newEpisodeAdapter.addData(medias)
        newEpisodeAdapter.notifyDataSetChanged()

    }


}