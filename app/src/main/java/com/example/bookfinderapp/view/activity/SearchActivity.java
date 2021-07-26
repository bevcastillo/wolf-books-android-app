package com.example.bookfinderapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookfinderapp.R;
import com.example.bookfinderapp.adapters.SearchResultsRecyclerviewAdapter;
import com.example.bookfinderapp.model.api.Books;
import com.example.bookfinderapp.model.api.Item;
import com.example.bookfinderapp.request.api.RequestService;
import com.example.bookfinderapp.request.api.RetrofitClass;
import com.example.bookfinderapp.vendor.InternetConnection;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    EditText searchQueryET;
    ImageView micIV, clearBTN;
    TextView sortRelevanceTV, sortNewest, headerTV, textTV, placeholderTitleTV, placeholderTextTV;
    RecyclerView searchResultsRV;
    ProgressBar progressBar;
    SearchResultsRecyclerviewAdapter searchAdapter;
    Button errorBTN;
    LinearLayoutManager layoutManager;
    View noConnectionLL;
    String search_keyword="";
    String orderBy="relevance";
    RequestService requestService;
    Call<Books> searchResultsCall;
    int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setTitle(" ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        searchQueryET = findViewById(R.id.searchQueryET);
        micIV = findViewById(R.id.micIV);
        clearBTN = findViewById(R.id.clearBTN);
        sortRelevanceTV = findViewById(R.id.sortRelevanceTV);
        sortNewest = findViewById(R.id.sortNewest);
        searchResultsRV = findViewById(R.id.searchResultsRV);
        progressBar = findViewById(R.id.progressBar);
        noConnectionLL = findViewById(R.id.noConnectionLL);
        headerTV = findViewById(R.id.headerTV);
        textTV = findViewById(R.id.textTV);
        errorBTN = findViewById(R.id.errorBTN);
        placeholderTitleTV = findViewById(R.id.placeholderTitleTV);
        placeholderTextTV = findViewById(R.id.placeholderTextTV);

        requestService = RetrofitClass.getNewBooksAPIInstance();

        InternetConnection.isInternetConnected(this,noConnectionLL,searchResultsRV);
        headerTV.setText(R.string.no_internet_header);
        textTV.setText(R.string.no_internet_text);
        errorBTN.setVisibility(View.VISIBLE);
        errorBTN.setText(R.string.try_again);

        micIV.setOnClickListener(this);
        clearBTN.setOnClickListener(this);
        sortRelevanceTV.setOnClickListener(this);
        sortNewest.setOnClickListener(this);

        searchQueryET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search_keyword = searchQueryET.getText().toString().trim();
                    if (!searchQueryET.getText().toString().trim().isEmpty()) {
                        loadRelevantItems(page);
                        placeholderTitleTV.setVisibility(View.GONE);
                        placeholderTextTV.setVisibility(View.GONE);
                        searchResultsRV.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                    }else {
                        searchResultsRV.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        placeholderTitleTV.setVisibility(View.VISIBLE);
                        placeholderTextTV.setVisibility(View.VISIBLE);
                    }
                }
                return false;
            }
        });

        searchQueryET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                clearBTN.setVisibility(View.VISIBLE);
                micIV.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                search_keyword = searchQueryET.getText().toString().trim();
                if (s.length() >0 && (!searchQueryET.getText().toString().trim().isEmpty())){
                    loadRelevantItems(page);
                }else if (s.length() ==0 && searchQueryET.getText().toString().trim().isEmpty()){
                    clearBTN.setVisibility(View.GONE);
                    micIV.setVisibility(View.VISIBLE);
                    placeholderTitleTV.setVisibility(View.VISIBLE);
                    placeholderTextTV.setVisibility(View.VISIBLE);
                    searchResultsRV.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                }else {
                    clearBTN.setVisibility(View.GONE);
                    micIV.setVisibility(View.VISIBLE);
                    placeholderTitleTV.setVisibility(View.GONE);
                    placeholderTextTV.setVisibility(View.GONE);
                    searchResultsRV.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void callSearchResults(String orderBy, int index) {
        String finalQuery = search_keyword.replace(" ","+");
        searchResultsCall = requestService.getSearchResults(finalQuery,index,orderBy,40);

        searchResultsCall.enqueue(new Callback<Books>() {
            @Override
            public void onResponse(Call<Books> call, Response<Books> response) {
                if (response.isSuccessful() || response.body() != null) {
                    placeholderTitleTV.setVisibility(View.GONE);
                    placeholderTextTV.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    searchResultsRV.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
//                    totalItemsTV.setText(response.body().getTotalItems()+" results");
                    for (int i=0; i<response.body().getItems().size(); i++) {
                        setUpSearchResultslist(response.body().getItems());
                    }
                }

                if (response.code()!=200) {
                    progressBar.setVisibility(View.GONE);
//                    placeholderLL.setVisibility(View.VISIBLE);
//                    placeholderTV.setText("Error : "+response.code());
                }
            }

            @Override
            public void onFailure(Call<Books> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
//                placeholderLL.setVisibility(View.VISIBLE);
//                placeholderTV.setText("Something went wrong.");
                placeholderTitleTV.setVisibility(View.GONE);
                placeholderTextTV.setText(R.string.something_went_wrong);
                placeholderTextTV.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setUpSearchResultslist(List<Item> itemList) {
        searchAdapter = new SearchResultsRecyclerviewAdapter(SearchActivity.this,itemList);
        layoutManager = new LinearLayoutManager(SearchActivity.this,LinearLayoutManager.VERTICAL,false);
        searchResultsRV.setLayoutManager(layoutManager);
        searchResultsRV.setAdapter(searchAdapter);
    }

    void loadRelevantItems(int page) {
        sortRelevanceTV.setAlpha((float) 1);
        sortNewest.setAlpha((float) 0.5);
        searchResultsRV.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        callSearchResults("relevance",page);
    }

    void loadNewestItems(int page) {
        sortRelevanceTV.setAlpha((float) 0.5);
        sortNewest.setAlpha((float) 1);
        searchResultsRV.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        callSearchResults("newest",page);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.micIV:
                break;
            case R.id.clearBTN:
                search_keyword="";
                searchQueryET.setText("");
                progressBar.setVisibility(View.GONE);
                break;
            case R.id.sortRelevanceTV:
                loadRelevantItems(page);
                orderBy="relevance";
                break;
            case R.id.sortNewest:
                loadNewestItems(page);
                orderBy="newest";
                break;
        }
    }
}