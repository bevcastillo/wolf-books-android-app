package com.example.bookfinderapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookfinderapp.R;
import com.example.bookfinderapp.adapters.SearchResultsRecyclerviewAdapter;
import com.example.bookfinderapp.helper.Constant;
import com.example.bookfinderapp.helper.DBManager;
import com.example.bookfinderapp.helper.DatabaseHelper;
import com.example.bookfinderapp.model.api.Books;
import com.example.bookfinderapp.model.api.Item;
import com.example.bookfinderapp.request.RequestService;
import com.example.bookfinderapp.request.RetrofitClass;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultsActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView searchResultsRV;
    String strQuery="";
    TextView sortRelevanceTV, placeholderTV, sortNewest;
    RequestService requestService;
    Call<Books> searchResultsCall;
    SearchResultsRecyclerviewAdapter searchAdapter;
    LinearLayoutManager layoutManager;
    LinearLayout placeholderLL;
    ShimmerFrameLayout shimmerFL;

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


        requestService = RetrofitClass.getNewBooksAPIInstance();

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            strQuery = bundle.getString("query_string");
            setTitle(strQuery);
        }else {
            Toast.makeText(SearchResultsActivity.this, "Something went wrong ...", Toast.LENGTH_SHORT).show();
        }

        callSearchResults();

        sortRelevanceTV.setOnClickListener(this);
        sortNewest.setOnClickListener(this);
    }

    private void callSearchResults() {
        String finalQuery = strQuery.replace(" ","+");
        searchResultsCall = requestService.getVolumeBooks(finalQuery);

        searchResultsCall.enqueue(new Callback<Books>() {
            @Override
            public void onResponse(Call<Books> call, Response<Books> response) {
                if (response.isSuccessful()) {
                    shimmerFL.setVisibility(View.GONE);
                    searchResultsRV.setVisibility(View.VISIBLE);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sortRelevanceTV:
            case R.id.sortNewest:
                Toast.makeText(this,"Coming Soon", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
