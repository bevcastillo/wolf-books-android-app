package com.example.bookfinderapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookfinderapp.R;
import com.example.bookfinderapp.adapters.SearchResultsRecyclerviewAdapter;
import com.example.bookfinderapp.model.api.Books;
import com.example.bookfinderapp.model.api.Item;
import com.example.bookfinderapp.request.api.RequestService;
import com.example.bookfinderapp.request.api.RetrofitClass;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultsActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView searchResultsRV;
    String strQuery="";
    TextView sortRelevanceTV, placeholderTV, sortNewest, totalItemsTV;
    RequestService requestService;
    Call<Books> searchResultsCall;
    SearchResultsRecyclerviewAdapter searchAdapter;
    LinearLayoutManager layoutManager;
    LinearLayout placeholderLL;
    ShimmerFrameLayout shimmerFL;
    String orderBy="relevance";
    int page;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        sortRelevanceTV = findViewById(R.id.sortRelevanceTV);
        sortNewest = findViewById(R.id.sortNewest);
        searchResultsRV = findViewById(R.id.searchResultsRV);
        placeholderTV = findViewById(R.id.placeholderTV);
        placeholderLL = findViewById(R.id.placeholderLL);
        shimmerFL = findViewById(R.id.shimmerFL);
        sortNewest = findViewById(R.id.sortNewest);
        totalItemsTV = findViewById(R.id.totalItemsTV);
        progressBar = findViewById(R.id.progressBar);

        requestService = RetrofitClass.getNewBooksAPIInstance();

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            strQuery = bundle.getString("query_string");
            setTitle(strQuery);

            loadRelevantItems(page);
        }else {
            Toast.makeText(SearchResultsActivity.this, "Something went wrong ...", Toast.LENGTH_SHORT).show();
        }

        sortRelevanceTV.setOnClickListener(this);
        sortNewest.setOnClickListener(this);

        //todo -- add infinite scroll listener
//        searchResultsRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (! searchResultsRV.canScrollVertically(1)) {
//                    page++;
//                    loadMore(page);
//                }
//            }
//        });

    }

    private void loadMore(int page) {
        if (orderBy=="relevance") {
            loadRelevantItems(page);
        }else {
            loadNewestItems(page);
        }

    }

    private void callSearchResults(String orderBy, int index) {
        String finalQuery = strQuery.replace(" ","+");
        searchResultsCall = requestService.getSearchResults(finalQuery,index,orderBy,40);

        searchResultsCall.enqueue(new Callback<Books>() {
            @Override
            public void onResponse(Call<Books> call, Response<Books> response) {
                if (response.isSuccessful() || response.body() != null) {
                    shimmerFL.setVisibility(View.GONE);
                    searchResultsRV.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    totalItemsTV.setText(response.body().getTotalItems()+" results");
                    for (int i=0; i<response.body().getItems().size(); i++) {
                        setUpSearchResultslist(response.body().getItems());
                    }
                }

                if (response.code()!=200) {
                    shimmerFL.setVisibility(View.GONE);
                    placeholderLL.setVisibility(View.VISIBLE);
                    placeholderTV.setText("Error : "+response.code());
                }
            }

            @Override
            public void onFailure(Call<Books> call, Throwable t) {
                shimmerFL.setVisibility(View.GONE);
                placeholderLL.setVisibility(View.VISIBLE);
                placeholderTV.setText("Something went wrong.");
            }
        });
    }

    private void setUpSearchResultslist(List<Item> itemList) {
        searchAdapter = new SearchResultsRecyclerviewAdapter(SearchResultsActivity.this,itemList);
        layoutManager = new LinearLayoutManager(SearchResultsActivity.this,LinearLayoutManager.VERTICAL,false);
        searchResultsRV.setLayoutManager(layoutManager);
        searchResultsRV.setAdapter(searchAdapter);
    }

    @Override
    protected void onPause() {
        shimmerFL.stopShimmer();
        super.onPause();
    }

    void loadRelevantItems(int page) {
        sortRelevanceTV.setAlpha((float) 1);
        sortNewest.setAlpha((float) 0.5);
        shimmerFL.setVisibility(View.GONE);
        searchResultsRV.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        callSearchResults("relevance",page);
    }

    void loadNewestItems(int page) {
        sortRelevanceTV.setAlpha((float) 0.5);
        sortNewest.setAlpha((float) 1);
        shimmerFL.setVisibility(View.VISIBLE);
        searchResultsRV.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        callSearchResults("newest",page);
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
