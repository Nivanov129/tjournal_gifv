package com.nick.lentagifv.network

import com.nick.lentagifv.models.FeedResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FeedApi {

    @GET("/v2.0/timeline")
    suspend fun getFeed(
        @Query("allSite") boolean: Boolean = false,
        @Query("sorting") sorting: String = "date",
        @Query("subsitesIds") subsitesIds: LongArray = longArrayOf(237832),
        @Query("lastId") lastId: Long? = null,
        @Query("lastSortingValue") lastSortingValue: Long? = null
    ): Response<FeedResponse>

}