package com.example.bookfinderapp.view;


import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookfinderapp.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment{

    private EditText et_searchQuery;
    private ImageView ivAmico;
    private AdView mAdView;

    public SearchFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_search, container, false);

        et_searchQuery = view.findViewById(R.id.et_searchQuery);
        ivAmico = view.findViewById(R.id.iv_amico);
        mAdView = view.findViewById(R.id.adView);

        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        getActivity().setTitle("Bookify");

        Animator translateAnimator = AnimatorInflater.loadAnimator(getActivity(), R.animator.translate);
        translateAnimator.setTarget(ivAmico);
        translateAnimator.start();

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



}
