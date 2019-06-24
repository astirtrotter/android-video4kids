package com.example.video4kids.common.backend.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @POST("search")
    fun getSearchVideo(@Query("age_id") name: String, @Query("query") userkey: String): Call<List<VideoItem>>

    @GET("category-videos/{path}/{kind}")
    fun getVideos(@Path("path") path: String, @Path("kind") kind: String): Call<List<VideoItem>>

    //http://mywork.promoletter.com/public/api/tags/mrbean/videos
    @GET("tags/{path}/{kind}")
    fun getVideosMore(@Path("path") path: String, @Path("kind") kind: String): Call<List<VideoItem>>
}