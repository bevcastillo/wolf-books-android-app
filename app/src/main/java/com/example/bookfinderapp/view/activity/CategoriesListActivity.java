package com.example.bookfinderapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bookfinderapp.R;
import com.example.bookfinderapp.adapters.AuthorResultsRecyclerviewAdapter;
import com.example.bookfinderapp.model.api.Books;
import com.example.bookfinderapp.model.api.Item;
import com.example.bookfinderapp.request.api.RequestService;
import com.example.bookfinderapp.request.api.RetrofitClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesListActivity extends AppCompatActivity implements View.OnClickListener {
    TextView sortRelevanceTV, sortNewest;
    RecyclerView authorListRV;
    String orderBy="relevance", category_name="", category_query="";
    int page=40;
    AuthorResultsRecyclerviewAdapter searchAdapter;
    LinearLayoutManager layoutManager;
    ProgressBar progressBar;
    RequestService requestService;
    Call<Books> authorResultsCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_list);

        sortRelevanceTV = findViewById(R.id.sortRelevanceTV);
        sortNewest = findViewById(R.id.sortNewest);
        authorListRV = findViewById(R.id.authorListRV);
        progressBar = findViewById(R.id.progressBar);

        requestService = RetrofitClass.getNewBooksAPIInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            category_query = bundle.getString("category_query");
            category_name = bundle.getString("category_name");
            setTitle(category_name);

            loadRelevantItems(page);
        }

        sortRelevanceTV.setOnClickListener(this);
        sortNewest.setOnClickListener(this);
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

    private void setUpCategoriesList(List<Item> itemList) {
        searchAdapter = new AuthorResultsRecyclerviewAdapter(CategoriesListActivity.this,itemList);
        layoutManager = new LinearLayoutManager(CategoriesListActivity.this,LinearLayoutManager.VERTICAL,false);
        authorListRV.setLayoutManager(layoutManager);
        authorListRV.setAdapter(searchAdapter);
    }

    private void callCategoriesList(String orderBy, int page) {
        authorResultsCall = requestService.getSearchResults(category_query,0,orderBy,40);
        authorResultsCall.enqueue(new Callback<Books>() {
            @Override
            public void onResponse(Call<Books> call, Response<Books> response) {
                if (response.isSuccessful()) {
                    for (int i=0; i<response.body().getItems().size(); i++) {
                        setUpCategoriesList(response.body().getItems());
                    }
                }
            }

            @Override
            public void onFailure(Call<Books> call, Throwable t) {

            }
        });
    }

    void loadRelevantItems(int page) {
        sortRelevanceTV.setAlpha((float) 1);
        sortNewest.setAlpha((float) 0.5);
        progressBar.setVisibility(View.VISIBLE);
        authorListRV.setVisibility(View.GONE);
        callCategoriesList("relevance",page);
    }

    void loadNewestItems(int page) {
        sortRelevanceTV.setAlpha((float) 0.5);
        sortNewest.setAlpha((float) 1);
        progressBar.setVisibility(View.VISIBLE);
        authorListRV.setVisibility(View.GONE);
        callCategoriesList("newest",page);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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