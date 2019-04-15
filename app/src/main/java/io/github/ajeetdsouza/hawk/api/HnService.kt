package io.github.ajeetdsouza.hawk.api

import retrofit2.Call
import retrofit2.http.GET

enum class HnServiceType {
    TOP,
    NEW,
    SHOW,
    ASK,
    JOBS
}

interface HnService {
    @GET("/stories/top")
    fun storiesTop(): Call<HnStoryList>

    @GET("/stories/new")
    fun storiesNew(): Call<HnStoryList>

    @GET("/stories/show")
    fun storiesShow(): Call<HnStoryList>

    @GET("/stories/ask")
    fun storiesAsk(): Call<HnStoryList>

    @GET("/stories/jobs")
    fun storiesJobs(): Call<HnStoryList>
}