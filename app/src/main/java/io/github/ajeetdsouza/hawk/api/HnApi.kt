package io.github.ajeetdsouza.hawk.api

import android.net.Uri
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class HnStoryItem(
    val id: Int,
    val title: String,
    val url: Uri,
    val commentsUrl: Uri,
    val commentCount: Int,
    val domain: String,
    val time: Date,
    val score: Int
)

typealias HnStoryList = List<HnStoryItem>
