package com.example.bookfinderapp.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.bookfinderapp.view.fragments.HomeFragmentV2;
import com.example.bookfinderapp.R;
import com.example.bookfinderapp.view.fragments.AboutAppFragment;
import com.example.bookfinderapp.view.fragments.BookmarksFragment;
import com.example.bookfinderapp.view.fragments.TopBooksFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    String TAG="";

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.searchItem:
                callSearch();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void callSearch() {
        //todo search
        Intent toSearchIntent = new Intent(NavigationActivity.this, SearchActivity.class);
        startActivity(toSearchIntent);
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
                selectedFragment = new TopBooksFragment();
                break;
            case R.id.nav_bookmarks:
                selectedFragment = new BookmarksFragment();
                break;
            case R.id.nav_about:
                selectedFragment = new AboutAppFragment();
                break;
            default:
                selectedFragment = new TopBooksFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.content_container, selectedFragment).commit();

        return true;
    }
}
