package com.codinginflow.imagesearchapp.api

import com.codinginflow.imagesearchapp.BuildConfig
import com.codinginflow.imagesearchapp.utils.Constants.CLIENT_ID
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {

    @Headers("Accept-Version: v1", "Authorization: Client-ID $CLIENT_ID")
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UnsplashResponse
}