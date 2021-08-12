package com.nick.lentagifv.pagging

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.nick.lentagifv.models.FeedResponse
import com.nick.lentagifv.network.FeedApi
import com.nick.lentagifv.network.NetworkService

class FeedRepo(
    private val service: FeedApi = NetworkService().retrofitService()
) {
    companion object {
        fun getInstance() = FeedRepo()
    }

    fun letFeedItemsLiveData(pagingConfig: PagingConfig = getDefaultPageConfig()): LiveData<PagingData<FeedResponse.Result.Item>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { FeedPagingDataSource(service) }
        ).liveData
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = 12, enablePlaceholders = false)
    }
}