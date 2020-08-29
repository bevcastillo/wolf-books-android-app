package com.example.bookfinderapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.bookfinderapp.R;
import com.example.bookfinderapp.models.VolumeBooks;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText et_searchQuery;
    private RequestQueue mRequestQueue;
    private ArrayList<VolumeBooks> volumeBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_searchQuery = findViewById(R.id.et_searchQuery);

        //
        volumeBooks = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);


        et_searchQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getVolumeResponse();
                    return true;
                }

                return false;
            }
        });
    }

    private void getVolumeResponse() {

        String queryString = et_searchQuery.getText().toString();

        //get the queryString data and pass it to SearchResultsActivity
        Intent queryIntent = new Intent(this, SearchResultsActivity.class);
        queryIntent.putExtra("query_string", queryString);
        startActivity(queryIntent);
    }
}
