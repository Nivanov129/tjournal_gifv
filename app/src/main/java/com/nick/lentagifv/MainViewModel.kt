package com.nick.lentagifv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nick.lentagifv.models.FeedResponse
import com.nick.lentagifv.pagging.FeedRepo

class MainViewModel( val repository: FeedRepo = FeedRepo.getInstance()) : ViewModel() {
    //todo need add ui notification about error
    fun fetchFeedLiveData(): LiveData<PagingData<FeedResponse.Result.Item>> {
        return repository.letFeedItemsLiveData().cachedIn(viewModelScope)
    }

}