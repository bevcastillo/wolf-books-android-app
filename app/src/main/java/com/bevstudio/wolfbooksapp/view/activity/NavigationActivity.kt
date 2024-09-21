package com.bevstudio.wolfbooksapp.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bevstudio.wolfbooksapp.R
import com.bevstudio.wolfbooksapp.view.activity.SearchActivity
import com.bevstudio.wolfbooksapp.view.fragments.AboutAppFragment
import com.bevstudio.wolfbooksapp.view.fragments.BookmarksFragment
import com.bevstudio.wolfbooksapp.view.fragments.HomeFragmentV2
import com.bevstudio.wolfbooksapp.view.fragments.TopBooksFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class NavigationActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {
    private var bottomNavigationView: BottomNavigationView? = null
    var TAG: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        bottomNavigationView = findViewById(R.id.bottom_nav)
        bottomNavigationView?.setOnNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.content_container, HomeFragmentV2()).commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.searchItem -> {
                callSearch()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun callSearch() {
        val toSearchIntent = Intent(this@NavigationActivity, SearchActivity::class.java)
        startActivity(toSearchIntent)
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        var selectedFragment: Fragment?
        val id = menuItem.itemId

        selectedFragment = when (id) {
            R.id.nav_home -> HomeFragmentV2()
            R.id.nav_search -> TopBooksFragment()
            R.id.nav_bookmarks -> BookmarksFragment()
            R.id.nav_about -> AboutAppFragment()
            else -> TopBooksFragment()
        }
        supportFragmentManager.beginTransaction().replace(R.id.content_container, selectedFragment)
            .commit()

        return true
    }
}
