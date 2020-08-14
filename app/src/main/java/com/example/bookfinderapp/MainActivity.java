package com.example.bookfinderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookfinderapp.models.FetchBook;

/* Created by Beverly May Castillo on 8/12/20 */


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText et_search_input;
    private TextView tv_advanced_search, mTitleText, mAuthorText;
    private Button btn_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_search_input = findViewById(R.id.et_search);
        tv_advanced_search = findViewById(R.id.tv_advanced_search);
        btn_search = findViewById(R.id.button);
        mTitleText = findViewById(R.id.title);
        mAuthorText = findViewById(R.id.author);

        btn_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.button:
                searchBooks();
                break;
        }
    }

    public void searchBooks() {
        String queryString = et_search_input.getText().toString();

        Toast.makeText(this, queryString+" is your query", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Searched: "+queryString);
        new FetchBook(mTitleText, mAuthorText).execute(queryString);

    }
}
