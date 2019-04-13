package io.github.ajeetdsouza.hawk.api

import android.net.Uri
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter

class UriJsonAdapter : JsonAdapter<Uri>() {
    override fun fromJson(reader: JsonReader): Uri {
        val uri = reader.nextString()
        return Uri.parse(uri).normalizeScheme()
    }

    override fun toJson(writer: JsonWriter, uri: Uri?) {
        writer.value(uri?.path)
    }

}
