package io.github.ajeetdsouza.hawk

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saurabharora.customtabs.ConnectionCallback
import com.saurabharora.customtabs.CustomTabActivityHelper
import com.saurabharora.customtabs.extensions.launchWithFallback

class StoryListFragment : Fragment(), ConnectionCallback {
    private val args: StoryListFragmentArgs by navArgs()

    private val customTabActivityHelper =
        CustomTabActivityHelper(requireContext(), lifecycle, this)

    private val onClickListener = View.OnClickListener { view ->
        val customTabsIntent = CustomTabsIntent.Builder(customTabActivityHelper.session)
            .addDefaultShareMenuItem()
            .setShowTitle(true)
            .setToolbarColor(ContextCompat.getColor(view.context, R.color.colorPrimary))
            .build()
        customTabsIntent.launchWithFallback(requireActivity(), view.getTag(R.id.tag_storyitem_url) as Uri)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_storyitem_list, container, false)

        // Set the adapter
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
