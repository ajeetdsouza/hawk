package io.github.ajeetdsouza.hawk

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saurabharora.customtabs.ConnectionCallback
import com.saurabharora.customtabs.CustomTabActivityHelper
import com.saurabharora.customtabs.extensions.launchWithFallback

class StoryListFragment : Fragment(), ConnectionCallback {
    private val args: StoryListFragmentArgs by navArgs()
    private lateinit var customTabActivityHelper : CustomTabActivityHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        customTabActivityHelper = CustomTabActivityHelper(requireContext(), lifecycle, this)

        val view = inflater.inflate(R.layout.fragment_storylist, container, false)
        val onClickListener = View.OnClickListener {  clickedView ->
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(clickedView.context)
            val url = clickedView.getTag(R.id.tag_storyitem_url) as Uri

            if (sharedPreferences.getBoolean("customtabs", false)) {
                val customTabsIntent = CustomTabsIntent.Builder(customTabActivityHelper.session)
                    .addDefaultShareMenuItem()
                    .setShowTitle(true)
                    .setToolbarColor(ContextCompat.getColor(clickedView.context, R.color.colorPrimary))
                    .build()
                customTabsIntent.launchWithFallback(requireActivity(), url)
            }
            else {
                val intent = Intent(Intent.ACTION_VIEW, url)
                if (intent.resolveActivity(clickedView.context.packageManager) != null) {
                    startActivity(intent)
                }
            }
        }

        if (view is RecyclerView) {
            with(view) {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = StoryListAdapter(args.hnServiceType, onClickListener)
            }
        }
        return view
    }

    override fun onCustomTabsConnected() = Unit
    override fun onCustomTabsDisconnected() = Unit
}
