package com.example.wolfbooksapp.view.fragments;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.wolfbooksapp.R;
import com.example.wolfbooksapp.adapters.BestBooksRecyclerviewAdapter;
import com.example.wolfbooksapp.model.api.Books;
import com.example.wolfbooksapp.request.api.RequestService;
import com.example.wolfbooksapp.request.api.RetrofitClass;
import com.example.wolfbooksapp.vendor.InternetConnection;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopBooksFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    BestBooksRecyclerviewAdapter bestBooksdAdapter;
    LinearLayoutManager layoutManager;
    AdView mAdView;
    RecyclerView newBooksRV;
    ShimmerFrameLayout shimmer_view_container;
    SwipeRefreshLayout searchSRL;
    Call<Books> newReleaseBooksCall;
    RequestService requestService;
    View noConnectionLL, parentLL;
    TextView headerTV, textTV;
    Button errorBTN;

    public TopBooksFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_trending, container, false);

        setHasOptionsMenu(true);
        mAdView = view.findViewById(R.id.adView);
        newBooksRV = view.findViewById(R.id.newBooksRV);
        shimmer_view_container = view.findViewById(R.id.shimmer_view_container);
        searchSRL = view.findViewById(R.id.searchSRL);
        parentLL = view.findViewById(R.id.parentLL);
        noConnectionLL = view.findViewById(R.id.noConnectionLL);
        headerTV = view.findViewById(R.id.headerTV);
        textTV = view.findViewById(R.id.textTV);
        errorBTN = view.findViewById(R.id.errorBTN);

        requestService = RetrofitClass.getAPIInstance();

        InternetConnection.isInternetConnected(getContext(),noConnectionLL,parentLL);
        headerTV.setText(R.string.no_internet_header);
        textTV.setText(R.string.no_internet_text);
        errorBTN.setVisibility(View.VISIBLE);
        errorBTN.setText(R.string.try_again);

        searchSRL.setOnRefreshListener(this);
        searchSRL.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        getActivity().setTitle("Best Books Ever");
        displayBestBooksEver();

        return view;
    }

    private void displayBestBooksEver() {
        shimmer_view_container.setVisibility(View.GONE);
        newBooksRV.setVisibility(View.VISIBLE);
        searchSRL.setRefreshing(false);
        bestBooksdAdapter = new BestBooksRecyclerviewAdapter(getContext());
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        newBooksRV.setLayoutManager(layoutManager);
        newBooksRV.setAdapter(bestBooksdAdapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    @Override
    public void onStop() {
        super.onStop();
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

    @Override
    public void onRefresh() {
        shimmer_view_container.setVisibility(View.VISIBLE);
        newBooksRV.setVisibility(View.GONE);
        displayBestBooksEver();
    }
}
