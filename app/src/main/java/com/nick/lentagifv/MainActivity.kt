package com.nick.lentagifv

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.nick.lentagifv.utils.ExoPlayerRecyclerView
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    val feedList: ExoPlayerRecyclerView by lazy {  findViewById(R.id.feed_list) }

    private val adapter = RemoteFeedAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        fetchFeed()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initViews() {

        feedList.layoutManager = LinearLayoutManager(this)
        feedList.adapter = adapter

        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.divider, application.theme))

        feedList.addItemDecoration(itemDecoration)
        feedList.smoothScrollToPosition(1)


    }



    private fun fetchFeed() {
        viewModel.fetchFeedLiveData().observe(this, Observer {
            lifecycleScope.launch {
                adapter.submitData(it)
            }
        })
    }

    override fun onPause() {
        feedList.onPausePlayer()
        super.onPause()
    }

    override fun onDestroy() {
        feedList.releasePlayer()
        super.onDestroy()
    }

}