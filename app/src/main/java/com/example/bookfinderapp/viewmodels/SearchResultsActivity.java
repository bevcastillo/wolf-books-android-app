package com.example.bookfinderapp.viewmodels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
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
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {

    private RecyclerView rvBooksResults;

    private static final String LOG_TAG = SearchResultsActivity.class.getSimpleName();
    private static final String BOOK_BASE_URL = "https://www.googleapis.com/books/v1/volumes?q="; //base URI

    private String strQuery;
    private TextView tvResultsFor;
    private RequestQueue mRequestQueue;

    private ArrayList<VolumeBooks> volumeBooks;
    private VolumeBooksAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        rvBooksResults = findViewById(R.id.rv_search_results);
        tvResultsFor = findViewById(R.id.tv_results);

        setTitle("Search Results");


        //ne
        rvBooksResults.setHasFixedSize(true);
        rvBooksResults.setLayoutManager(new LinearLayoutManager(this));
        volumeBooks = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            strQuery = bundle.getString("query_string");
            tvResultsFor.setText("Results for: "+strQuery);
        }

        search();

    }

    private void parseJson(String key) {

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, key.toString(), null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String title = null;
                        String subtitle = null;
                        String authors = null; //authors from Json
                        String description = null;
                        String publisher = null;
                        String publishedDate = null;
                        String categories = null;
                        String thumbnail = null;
                        String previewLink = null;
                        String infoLink = null;
                        String price = null;
                        String currencyCode = null;
                        int pageCount = 100;
                        String buyLink = null;

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
                                        authors = authorArr.getString(0) + "," +authorArr.getString(1);
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

                                }catch (Exception e){

                                }

                                thumbnail = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");

                                previewLink = volumeInfo.getString("previewLink");
                                infoLink = volumeInfo.getString("infoLink");


                                volumeBooks.add(new VolumeBooks(title, subtitle, authors, description,
                                        publisher, publishedDate, categories, thumbnail, previewLink,
                                        infoLink, price, currencyCode, buyLink, pageCount));

//                                VolumeBooksAdapter adapter = new VolumeBooksAdapter(getApplicationContext(), volumeBooks);
//                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//                                rvBooksResults.setLayoutManager(layoutManager);
//                                rvBooksResults.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
//                                rvBooksResults.setItemAnimator(new DefaultItemAnimator());
//                                rvBooksResults.setAdapter(adapter);

                                adapter = new VolumeBooksAdapter(SearchResultsActivity.this, volumeBooks);
                                rvBooksResults.setAdapter(adapter);

                                adapter.notifyDataSetChanged();

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

    private void search(){
        //  Log.d("QUERY",search_query);


        if(strQuery.equals("")) {
            Toast.makeText(this,"Please enter your query",Toast.LENGTH_SHORT).show();
            return;
        }
        String final_query=strQuery.replace(" ","+");
        Uri uri=Uri.parse(BOOK_BASE_URL+final_query);
        Uri.Builder buider = uri.buildUpon();

        parseJson(buider.toString());


    }

}
