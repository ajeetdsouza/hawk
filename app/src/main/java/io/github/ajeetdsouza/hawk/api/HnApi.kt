package io.github.ajeetdsouza.hawk.api

import android.net.Uri
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

enum class HnItemType {
    @Json(name = "story")
    STORY,
    @Json(name = "comment")
    COMMENT,
    @Json(name = "job")
    JOB
}

@JsonClass(generateAdapter = true)
data class HnItem(
    val created_at: Date? = null,
    val title: String? = null,
    val type: HnItemType? = null,
    val url: Uri? = null,
    val points: Int? = null,
    val num_comments: Int? = null
)

@JsonClass(generateAdapter = true)
data class HnSearch(
    val hits: List<HnItem>
)
