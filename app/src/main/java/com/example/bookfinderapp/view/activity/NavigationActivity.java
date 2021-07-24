package com.example.bookfinderapp.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.bookfinderapp.view.fragments.HomeFragmentV2;
import com.example.bookfinderapp.R;
import com.example.bookfinderapp.view.fragments.AboutAppFragment;
import com.example.bookfinderapp.view.fragments.BookmarksFragment;
import com.example.bookfinderapp.view.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_container, new HomeFragmentV2()).commit();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment selectedFragment = null;

        int id = menuItem.getItemId();

        switch (id) {
            case R.id.nav_home:
                selectedFragment = new HomeFragmentV2();
                break;
            case R.id.nav_search:
                selectedFragment = new SearchFragment();
                break;
            case R.id.nav_bookmarks:
                selectedFragment = new BookmarksFragment();
                break;
            case R.id.nav_about:
                selectedFragment = new AboutAppFragment();
                break;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.content_container, selectedFragment).commit();


        return true;
    }
}
