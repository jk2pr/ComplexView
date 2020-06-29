package com.jk.mindvalley.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.jk.mindvalley.R
import com.jk.mindvalley.data.new_episode.Media
import com.jk.mindvalley.data.response.Status
import com.jk.mindvalley.ui.main.adapters.MainAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val mainViewModel: MainViewModel by navGraphViewModels(R.id.nav) { defaultViewModelProviderFactory }
    private lateinit var adapter: MainAdapter

    @Inject
    lateinit var requestManager: RequestManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    private fun setupObserver() {
        mainViewModel.dataList.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { data -> renderList(data.media) }
                    recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObserver()
    }

    private fun setupUI() {

        recyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        adapter = MainAdapter(arrayListOf(), requestManager)

        val dec = DividerItemDecoration(
            recyclerView.context,
            (recyclerView.layoutManager as LinearLayoutManager).orientation
        )
        dec.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.shape)!!)
        recyclerView.addItemDecoration(dec)


        recyclerView.adapter = adapter
    }

    private fun renderList(medias: List<Media>) {
        adapter.addData(medias)
        adapter.notifyDataSetChanged()
    }
}