package com.example.bookfinderapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookfinderapp.R;
import com.example.bookfinderapp.adapters.VolumeBooksAdapter;
import com.example.bookfinderapp.models.VolumeBooks;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {

    private RecyclerView rvBooksResults;

    private static final String LOG_TAG = SearchResultsActivity.class.getSimpleName();
    private static final String BOOK_BASE_URL = "https://www.googleapis.com/books/v1/volumes?q="; //base URI
    private static final String BOOK_MAX_RES = "&maxResults=40";

    private String strQuery;
    private TextView tvResultsFor;
    private RequestQueue mRequestQueue;

    private ArrayList<VolumeBooks> volumeBooks;
    private VolumeBooksAdapter adapter;

    private LinearLayout layoutNoData;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        rvBooksResults = findViewById(R.id.rv_search_results);
        tvResultsFor = findViewById(R.id.tv_results);
        layoutNoData = findViewById(R.id.layout_no_data);
        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);

        setTitle("Search Results");


        //ne
        rvBooksResults.setHasFixedSize(true);
        rvBooksResults.setLayoutManager(new LinearLayoutManager(this));
        volumeBooks = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);

        volumeBooks.clear(); //clearing the results first to avoid data duplication when re-loaded

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            strQuery = bundle.getString("query_string");
            tvResultsFor.setText("results for "+"''"+strQuery+"''");
        }

        loadSearchResults();

    }


    @Override
    protected void onPause() {
        shimmerFrameLayout.stopShimmer();
        super.onPause();
    }

    private void parseJson(String key) {

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, key.toString(), null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String title = "";
                        String subtitle = "";
                        String authors = ""; //authors from Json
                        String description = "No description";
                        String publisher = "";
                        String publishedDate = "";
                        String categories = "No Categories";
                        String thumbnail = null;
                        String price = "Not For Sale";
                        String currencyCode = "Not available";
                        String language = "Not Available";
                        String isbn = "Not Available";
                        int pageCount = 0;
                        int ratingsCount = 0;
                        double averageRating = 5.0;
                        String buyLink = "Not Available";

                        try {
                            JSONArray items = response.getJSONArray("items");

                            for (int i = 0 ; i< items.length() ;i++) {
                                JSONObject item = items.getJSONObject(i);
                                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                                //
                                try{
                                    title = volumeInfo.getString("title");

                                    //author === json array of authors
                                    JSONArray authorArr = volumeInfo.getJSONArray("authors");
                                    if(authorArr.length() == 1){
                                        authors = authorArr.getString(0);
                                    }else {
                                        authors = authorArr.getString(0) + ", " +authorArr.getString(1);
                                    }


                                    publisher = volumeInfo.getString("publisher");
                                    publishedDate = volumeInfo.getString("publishedDate");
                                    pageCount = volumeInfo.getInt("pageCount");


                                    JSONObject saleInfo = item.getJSONObject("saleInfo");
                                    JSONObject listPrice = saleInfo.getJSONObject("listPrice");

                                    price = listPrice.getString("amount") + " " +listPrice.getString("currencyCode");

                                    description = volumeInfo.getString("description");
                                    buyLink = saleInfo.getString("buyLink");
                                    categories = volumeInfo.getJSONArray("categories").getString(0);
                                    averageRating = volumeInfo.getDouble("averageRating");
                                    ratingsCount = volumeInfo.getInt("ratingsCount");
                                    language = volumeInfo.getString("language");

                                }catch (Exception e){

                                }

                                thumbnail = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");

                                String previewLink = volumeInfo.getString("previewLink");
                                String infoLink = volumeInfo.getString("infoLink");


                                volumeBooks.add(new VolumeBooks(title, authors,
                                        description, publisher, publishedDate,
                                        categories, thumbnail, previewLink, price, currencyCode,
                                        buyLink, language, pageCount, averageRating, ratingsCount, false)); //we set false as default value of isBookmark

                                adapter = new VolumeBooksAdapter(SearchResultsActivity.this, volumeBooks);
                                shimmerFrameLayout.stopShimmer();
                                shimmerFrameLayout.setVisibility(View.GONE);

                                rvBooksResults.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                                if (volumeBooks.isEmpty()) {
                                    layoutNoData.setVisibility(View.VISIBLE);
                                } else {
                                    layoutNoData.setVisibility(View.GONE);
                                }
                            }


                        } catch (Exception e) {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);

    }

    private void loadSearchResults(){
        if(strQuery.equals("")) {
            Toast.makeText(this,"Please enter your query",Toast.LENGTH_SHORT).show();
            return;
        }

        String final_query = strQuery.replace(" ","+");
        Uri uri = Uri.parse(BOOK_BASE_URL+final_query+BOOK_MAX_RES);
        Uri.Builder builder = uri.buildUpon();

        parseJson(builder.toString());

    }

}
