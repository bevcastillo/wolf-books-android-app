package com.example.bookfinderapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookfinderapp.R;
import com.example.bookfinderapp.adapterV2.CategoriesRecyclerviewAdapter;
import com.example.bookfinderapp.adapterV2.SearchResultsRecyclerviewAdapter;
import com.example.bookfinderapp.adapters.VolumeBooksAdapter;
import com.example.bookfinderapp.helper.Constant;
import com.example.bookfinderapp.helper.DBManager;
import com.example.bookfinderapp.helper.DatabaseHelper;
import com.example.bookfinderapp.modelV2.Books;
import com.example.bookfinderapp.modelV2.Item;
import com.example.bookfinderapp.models.VolumeBooks;
import com.example.bookfinderapp.request.RequestService;
import com.example.bookfinderapp.request.RetrofitClass;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultsActivity extends AppCompatActivity {

    private RecyclerView searchResultsRV;
    private String strQuery="";
    private TextView searchQueryTV, placeholderTV;
    RequestService requestService;
    Call<Books> searchResultsCall;
    SearchResultsRecyclerviewAdapter searchAdapter;
    LinearLayoutManager layoutManager;
    private LinearLayout placeholderLL;
    private ShimmerFrameLayout shimmerFL;

    private DatabaseHelper db;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        searchQueryTV = findViewById(R.id.searchQueryTV);
        searchResultsRV = findViewById(R.id.searchResultsRV);
        placeholderTV = findViewById(R.id.placeholderTV);
        placeholderLL = findViewById(R.id.placeholderLL);
        shimmerFL = findViewById(R.id.shimmerFL);
//        requestService = RetrofitClass.getAPIInstance();
        requestService = RetrofitClass.getNewBooksAPIInstance();

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            strQuery = bundle.getString("query_string");
            setTitle(strQuery);
        }else {
            Toast.makeText(SearchResultsActivity.this, "Something went wrong ...", Toast.LENGTH_SHORT).show();
        }

        callSearchResults();
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

}
