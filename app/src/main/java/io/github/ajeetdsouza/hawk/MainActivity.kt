package io.github.ajeetdsouza.hawk

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import io.github.ajeetdsouza.hawk.api.HnServiceType
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    NavController.OnDestinationChangedListener {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar_main)

        val toggle = ActionBarDrawerToggle(
            this, drawerlayout_main, toolbar_main, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerlayout_main.addDrawerListener(toggle)
        toggle.syncState()

        navController = Navigation.findNavController(this, R.id.navhostfragment_main)
        navController.setGraph(R.navigation.navigation, StoryListFragmentArgs(HnServiceType.TOP).toBundle())

        NavigationUI.setupActionBarWithNavController(this, navController, drawerlayout_main)
        NavigationUI.setupWithNavController(navigationview_main, navController)

        navigationview_main.setNavigationItemSelectedListener(this)
        navController.addOnDestinationChangedListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerlayout_main)
    }

    override fun onBackPressed() {
        if (drawerlayout_main.isDrawerOpen(GravityCompat.START)) {
            drawerlayout_main.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        item.isChecked = true

        when (item.itemId) {
            R.id.menuitem_nav_frontpagestories ->
                navController.navigate(
                    R.id.fragment_nav_storylist,
                    StoryListFragmentArgs(HnServiceType.TOP).toBundle()
                )
            R.id.menuitem_nav_newstories ->
                navController.navigate(
                    R.id.fragment_nav_storylist,
                    StoryListFragmentArgs(HnServiceType.NEW).toBundle()
                )
            R.id.menuitem_nav_askstories ->
                navController.navigate(
                    R.id.fragment_nav_storylist,
                    StoryListFragmentArgs(HnServiceType.ASK).toBundle()
                )
            R.id.menuitem_nav_showstories ->
                navController.navigate(
                    R.id.fragment_nav_storylist,
                    StoryListFragmentArgs(HnServiceType.SHOW).toBundle()
                )
            R.id.menuitem_nav_jobstories ->
                navController.navigate(
                    R.id.fragment_nav_storylist,
                    StoryListFragmentArgs(HnServiceType.JOBS).toBundle()
                )
            R.id.menuitem_nav_settings ->
                navController.navigate(R.id.fragment_nav_settings)
        }

        drawerlayout_main.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        when (destination.id) {
            R.id.fragment_nav_storylist -> {
                if (arguments != null) {
                    val storyItemFragmentArgs = StoryListFragmentArgs.fromBundle(arguments)
                    when (storyItemFragmentArgs.hnServiceType) {
                        HnServiceType.TOP -> {
                            navigationview_main.setCheckedItem(R.id.menuitem_nav_frontpagestories)
                            toolbar_main.subtitle = getString(R.string.nav_topstories)
                        }
                        HnServiceType.NEW -> {
                            navigationview_main.setCheckedItem(R.id.menuitem_nav_newstories)
                            toolbar_main.subtitle = getString(R.string.nav_newstories)
                        }
                        HnServiceType.ASK -> {
                            navigationview_main.setCheckedItem(R.id.menuitem_nav_askstories)
                            toolbar_main.subtitle = getString(R.string.nav_askstories)
                        }
                        HnServiceType.SHOW -> {
                            navigationview_main.setCheckedItem(R.id.menuitem_nav_showstories)
                            toolbar_main.subtitle = getString(R.string.nav_showstories)
                        }
                        HnServiceType.JOBS -> {
                            navigationview_main.setCheckedItem(R.id.menuitem_nav_jobstories)
                            toolbar_main.subtitle = getString(R.string.nav_jobstories)
                        }
                    }
                }
            }
            else -> toolbar_main.subtitle = null
        }
    }
}
