package com.example.bookfinderapp.view.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookfinderapp.R;
import com.example.bookfinderapp.adapters.NewReleaseRecyclerviewAdapter;
import com.example.bookfinderapp.model.api.Books;
import com.example.bookfinderapp.model.api.Item;
import com.example.bookfinderapp.request.RequestService;
import com.example.bookfinderapp.request.RetrofitClass;
import com.example.bookfinderapp.view.activity.SearchResultsActivity;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    NewReleaseRecyclerviewAdapter newReleaseAdapter;
    LinearLayoutManager layoutManager;
    EditText searchQueryET;
    AdView mAdView;
    RecyclerView newBooksRV;
    ShimmerFrameLayout shimmer_view_container;
    SwipeRefreshLayout searchSRL;
    Call<Books> newReleaseBooksCall;
    RequestService requestService;

    public SearchFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchQueryET = view.findViewById(R.id.searchQueryET);
        mAdView = view.findViewById(R.id.adView);
        newBooksRV = view.findViewById(R.id.newBooksRV);
        shimmer_view_container = view.findViewById(R.id.shimmer_view_container);
        searchSRL = view.findViewById(R.id.searchSRL);

        requestService = RetrofitClass.getAPIInstance();

        searchSRL.setOnRefreshListener(this);
        searchSRL.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        getActivity().setTitle("Search");

        callNewBooks();

        searchQueryET.setOnEditorActionListener(   new TextView.OnEditorActionListener() {
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
                    callSearchResults();
                    return true;
                }

                return false;
            }
        });

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void callNewBooks() {
        searchSRL.setRefreshing(false);
        newReleaseBooksCall = requestService.getNewReleaseBooks();
        newReleaseBooksCall.enqueue(new Callback<Books>() {
            @Override
            public void onResponse(Call<Books> call, Response<Books> response) {
                shimmer_view_container.setVisibility(View.GONE);
                newBooksRV.setVisibility(View.VISIBLE);
                if (response.code()!=200) {
                    Toast.makeText(getContext(), "Error : "+response.code(), Toast.LENGTH_SHORT).show();
                }

                shimmer_view_container.setVisibility(View.GONE);
                Books books = response.body();

                if (response.isSuccessful()) {
                    for (int i=0; i<books.getItems().size(); i++) {
                        setUpNewReleaseBooks(response.body().getItems());
                    }
                }
            }

            @Override
            public void onFailure(Call<Books> call, Throwable t) {

            }
        });
    }

    private void setUpNewReleaseBooks(List<Item> items) {
        newReleaseAdapter = new NewReleaseRecyclerviewAdapter(getContext(),items);
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        newBooksRV.setLayoutManager(layoutManager);
        newBooksRV.setAdapter(newReleaseAdapter);
        newReleaseAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        shimmer_view_container.stopShimmer();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmer_view_container.startShimmer();
    }

    private void callSearchResults() {
        String queryString = searchQueryET.getText().toString().trim();

        Intent searchIntent = new Intent(getActivity(), SearchResultsActivity.class);
        searchIntent.putExtra("query_string", queryString);
        startActivity(searchIntent);
    }


    @Override
    public void onRefresh() {
        shimmer_view_container.setVisibility(View.VISIBLE);
        newBooksRV.setVisibility(View.GONE);
        callNewBooks();
    }
}
