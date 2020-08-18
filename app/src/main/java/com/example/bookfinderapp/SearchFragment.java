package com.example.bookfinderapp;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bookfinderapp.viewmodels.SearchResultsActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private EditText et_searchQuery;



    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        et_searchQuery = view.findViewById(R.id.et_searchQuery);

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

    private void getVolumeResponse() {

        String queryString = et_searchQuery.getText().toString();

        //get the queryString data and pass it to SearchResultsActivity
        Intent queryIntent = new Intent(getActivity(), SearchResultsActivity.class);
        queryIntent.putExtra("query_string", queryString);
        startActivity(queryIntent);
    }

}
