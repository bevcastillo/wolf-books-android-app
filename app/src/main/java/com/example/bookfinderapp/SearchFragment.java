package com.example.bookfinderapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookfinderapp.adapters.PopularBooksAdapter;
import com.example.bookfinderapp.adapters.VolumeBooksAdapter;
import com.example.bookfinderapp.models.VolumeBooks;
import com.example.bookfinderapp.viewmodels.SearchResultsActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment{

    private EditText et_searchQuery;
    private RecyclerView rvPopBooks, rvReligionBooks, rvEducBooks;

    private static final String LOG_TAG = SearchResultsActivity.class.getSimpleName();
    private static final String BOOK_BASE_URL = "https://www.googleapis.com/books/v1/volumes?q="; //base URI
    private static final String BOOK_MAX_RES = "&maxResults=20";

    private RequestQueue mRequestQueue;

    String strPopular = "popularbooks";
    String strEduc = "educationalbooks";
    String strReligion = "religion";

    private ArrayList<VolumeBooks> volumeBooks;
    private PopularBooksAdapter pop_adapter;



    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        et_searchQuery = view.findViewById(R.id.et_searchQuery);
        rvPopBooks = view.findViewById(R.id.rv_popular_books);
        rvReligionBooks = view.findViewById(R.id.rv_religion_books);
        rvEducBooks = view.findViewById(R.id.rv_educational_books);

        //
        rvPopBooks.setHasFixedSize(true);
        
        rvPopBooks.setLayoutManager(new LinearLayoutManager(getContext()));
        volumeBooks = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(getContext());


        //methods
        displayPopularBooks();

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

        return view;
    }

    private void displayPopularBooks() {

        String final_popular_category = strPopular.replace(" ","+");
        Uri uri = Uri.parse(BOOK_BASE_URL+final_popular_category+BOOK_MAX_RES);
        Uri.Builder builder = uri.buildUpon();

        parseJson(builder.toString());

    }

    private void getVolumeResponse() {

        String queryString = et_searchQuery.getText().toString();

        //get the queryString data and pass it to SearchResultsActivity
        Intent queryIntent = new Intent(getActivity(), SearchResultsActivity.class);
        queryIntent.putExtra("query_string", queryString);
        startActivity(queryIntent);
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


                                volumeBooks.add(new VolumeBooks(title, subtitle, authors,
                                        description, publisher, publishedDate,
                                        categories, thumbnail, previewLink,
                                        infoLink, price, currencyCode,
                                        buyLink, language, isbn, pageCount, averageRating, ratingsCount));

                                pop_adapter = new PopularBooksAdapter(getContext(), volumeBooks);
                                rvPopBooks.setAdapter(pop_adapter);

                                pop_adapter.notifyDataSetChanged();
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

}
