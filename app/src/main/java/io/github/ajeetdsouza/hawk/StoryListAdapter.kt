package io.github.ajeetdsouza.hawk

import android.net.Uri
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import io.github.ajeetdsouza.hawk.api.*
import kotlinx.android.synthetic.main.fragment_storyitem.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

class StoryListAdapter(hnServiceType: HnServiceType, private val onClickListener: View.OnClickListener) :
    RecyclerView.Adapter<StoryListAdapter.ViewHolder>(), Callback<HnStoryList> {

    companion object {
        private const val TAG = "StoryListAdapter"
    }

    private val storyList = mutableListOf<HnStoryItem>()

    init {
        val moshi = Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
            .add(Uri::class.java, UriJsonAdapter().nullSafe())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://api.hexforhn.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val service = retrofit.create<HnService>(HnService::class.java)

        when (hnServiceType) {
            HnServiceType.TOP -> service.storiesTop()
            HnServiceType.NEW -> service.storiesNew()
            HnServiceType.ASK -> service.storiesAsk()
            HnServiceType.SHOW -> service.storiesShow()
            HnServiceType.JOBS -> service.storiesJobs()

        }.enqueue(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_storyitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = storyList[position]

        holder.titleView.text = item.title
        holder.urlView.text = item.domain

        holder.scoreView.text = item.score.toString()
        holder.commentsView.text = item.commentCount.toString()
        holder.timeView.text = DateUtils.getRelativeTimeSpanString(item.time.time)

        Glide
            .with(holder.faviconView)
            .load("https://logo.clearbit.com/${item.domain}")
            .placeholder(R.color.colorImgFallback)
            .error(R.color.colorImgFallback)
            .into(holder.faviconView)

        holder.mView.setTag(R.id.tag_storyitem_url, item.commentsUrl)
        holder.mView.setOnClickListener(onClickListener)

        holder.faviconView.setTag(R.id.tag_storyitem_url, item.url)
        holder.faviconView.setOnClickListener(onClickListener)
    }

    override fun getItemCount(): Int = storyList.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val commentsView: TextView = mView.textview_storyitem_comments
        val faviconView: ImageView = mView.imageview_storyitem_favicon
        val scoreView: TextView = mView.textview_storyitem_score
        val timeView: TextView = mView.textview_storyitem_time
        val titleView: TextView = mView.textview_storyitem_title
        val urlView: TextView = mView.textview_storyitem_url
    }

    override fun onResponse(call: Call<HnStoryList>, response: Response<HnStoryList>) {
        val body = response.body()
        if (body != null) {
            storyList.addAll(body)
            notifyDataSetChanged()
        } else {
            Log.e(TAG, "Hex API call succeeded, but returned a null body")
        }
    }

    override fun onFailure(call: Call<HnStoryList>, e: Throwable) {
        Log.e(TAG, "Hex API call failed", e)
    }
}
