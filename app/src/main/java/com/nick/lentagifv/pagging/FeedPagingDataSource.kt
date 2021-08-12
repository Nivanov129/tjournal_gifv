package com.nick.lentagifv.pagging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nick.lentagifv.models.FeedResponse
import com.nick.lentagifv.network.FeedApi
import retrofit2.HttpException
import java.io.IOException

class FeedPagingDataSource(private val service: FeedApi) :
    PagingSource<Pair<Long, Long>, FeedResponse.Result.Item>() {

    override suspend fun load(params: LoadParams<Pair<Long, Long>>): LoadResult<Pair<Long, Long>, FeedResponse.Result.Item> {
        return try {
            val data = service.getFeed(
                lastId = params.key?.first,
                lastSortingValue = params.key?.second
            ).body()



            LoadResult.Page(
                data = data?.result?.items ?: arrayListOf(),
                prevKey = params.key,
                nextKey = Pair(data?.result?.lastId ?: 0L, data?.result?.lastSortingValue ?: 0L)
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Pair<Long, Long>, FeedResponse.Result.Item>): Pair<Long, Long>? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}