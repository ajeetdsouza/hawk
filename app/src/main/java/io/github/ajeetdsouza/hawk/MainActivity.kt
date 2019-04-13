package io.github.ajeetdsouza.hawk

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawerlayout_main, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerlayout_main.addDrawerListener(toggle)
        toggle.syncState()

        navController = Navigation.findNavController(this, R.id.navhostfragment_main)
        navController.setGraph(
            R.navigation.nav_graph,
            StoryListFragmentArgs(getString(R.string.nav_frontpagestories)).toBundle()
        )

        NavigationUI.setupActionBarWithNavController(this, navController, drawerlayout_main)
        NavigationUI.setupWithNavController(navigationview_main, navController)

        navigationview_main.setNavigationItemSelectedListener(this)
        navigationview_main.setCheckedItem(R.id.menuitem_nav_frontpagestories)
    }

    override fun onBackPressed() {
        if (drawerlayout_main.isDrawerOpen(GravityCompat.START)) {
            drawerlayout_main.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuitem_nav_frontpagestories -> {
                val args = StoryListFragmentArgs(getString(R.string.nav_frontpagestories))
                navController.navigate(R.id.fragment_nav_storylist, args.toBundle())
            }
            R.id.menuitem_nav_newstories -> {
                val args = StoryListFragmentArgs(getString(R.string.nav_newstories))
                navController.navigate(R.id.fragment_nav_storylist, args.toBundle())
            }
            R.id.menuitem_nav_askstories -> {
                val args = StoryListFragmentArgs(getString(R.string.nav_askstories))
                navController.navigate(R.id.fragment_nav_storylist, args.toBundle())
            }
            R.id.menuitem_nav_showstories -> {
                val args = StoryListFragmentArgs(getString(R.string.nav_showstories))
                navController.navigate(R.id.fragment_nav_storylist, args.toBundle())
            }
            R.id.menuitem_nav_jobstories -> {
                val args = StoryListFragmentArgs(getString(R.string.nav_jobstories))
                navController.navigate(R.id.fragment_nav_storylist, args.toBundle())
            }
        }

        drawerlayout_main.closeDrawer(GravityCompat.START)
        return true
    }
}
