package io.github.ajeetdsouza.hawk.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface HnService {
    companion object {
        const val hitsPerPage = 100
    }

    @GET("v1/items/{id}")
    fun item(@Path("id") id: Int): Call<HnItem>

    @GET("v1/search?tags=front_page&hitsPerPage=$hitsPerPage")
    fun frontPage(): Call<HnSearch>

    @GET("v1/search_by_date?tags=story&hitsPerPage=$hitsPerPage")
    fun new(): Call<HnSearch>

    @GET("v1/search?tags=show_hn&hitsPerPage=$hitsPerPage")
    fun showHn(): Call<HnSearch>

    @GET("v1/search?tags=ask_hn&hitsPerPage=$hitsPerPage")
    fun askHn(): Call<HnSearch>

    @GET("v1/search_by_date?tags=job&hitsPerPage=$hitsPerPage")
    fun jobs(): Call<HnSearch>
}