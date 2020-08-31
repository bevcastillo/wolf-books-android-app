package com.example.bookfinderapp.view;


import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookfinderapp.R;
import com.example.bookfinderapp.adapters.NewBooksAdapter;
import com.example.bookfinderapp.adapters.VolumeBooksAdapter;
import com.example.bookfinderapp.helper.Constant;
import com.example.bookfinderapp.models.VolumeBooks;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment{

    private EditText et_searchQuery;
    private AdView mAdView;

    private RecyclerView rvNewBooks;

    private RequestQueue mRequestQueue;

    private ArrayList<VolumeBooks> volumeBooks;
    private NewBooksAdapter adapter;

    private ShimmerFrameLayout shimmerFrameLayout;


    public SearchFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_search, container, false);

        et_searchQuery = view.findViewById(R.id.et_searchQuery);
        mAdView = view.findViewById(R.id.adView);
        rvNewBooks = view.findViewById(R.id.rv_new_books);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);

        //
        rvNewBooks.setHasFixedSize(true);
        rvNewBooks.setLayoutManager(new LinearLayoutManager(getActivity()));
        volumeBooks = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(getActivity());

        loadSearchResults();

        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        getActivity().setTitle("Search");

        et_searchQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    try {
                        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken()
                                ,InputMethodManager.HIDE_NOT_ALWAYS);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    getVolumeResponse();
                    return true;
                }

                return false;
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        shimmerFrameLayout.stopShimmer();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();
    }


    private void getVolumeResponse() {

        String queryString = et_searchQuery.getText().toString();

        //check if the user is connected to the internet
        if (isInternetAvailable(getActivity())) {
            //get the queryString data and pass it to SearchResultsActivity
            Intent queryIntent = new Intent(getActivity(), SearchResultsActivity.class);
            queryIntent.putExtra("query_string", queryString);
            startActivity(queryIntent);
        }


    }

    private static boolean isInternetAvailable(Context context) {
        NetworkInfo info = (NetworkInfo) ((ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        if (info == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setTitle("No Internet Connection");
            builder.setMessage("Unable to connect to Bookify. Please check your internet connection and try again.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.show();
            return false;
        }
        
        return true;
    }

    private void parseJson(String key) {

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, key.toString(), null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String volumeId = "";
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

                                volumeId = item.getString("id");
                                thumbnail = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");

                                String previewLink = volumeInfo.getString("previewLink");
                                String infoLink = volumeInfo.getString("infoLink");


                                volumeBooks.add(new VolumeBooks(volumeId,title, authors,
                                        description, publisher, publishedDate,
                                        categories, thumbnail, previewLink, price, currencyCode,
                                        buyLink, language, pageCount, averageRating, ratingsCount, false)); //we set false as default value of isBookmark

                                adapter = new NewBooksAdapter(getActivity(), volumeBooks);

                                rvNewBooks.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                                shimmerFrameLayout.stopShimmer();
                                shimmerFrameLayout.setVisibility(View.GONE);
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
        request.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(request);

    }

    private void loadSearchResults(){
        Uri uri = Uri.parse(Constant.BOOK_NEW_URL);
        Uri.Builder builder = uri.buildUpon();

        parseJson(builder.toString());

    }



}
