package com.bevstudio.wolfbooksapp.view.fragments;


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

import com.bevstudio.wolfbooksapp.R;
import com.bevstudio.wolfbooksapp.adapters.BookmarksRecyclerviewAdapter;
import com.bevstudio.wolfbooksapp.request.db.DatabaseHelper;
import com.bevstudio.wolfbooksapp.model.db.VolumeBooks;
import com.bevstudio.wolfbooksapp.vendor.InternetConnection;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookmarksFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    TextView tvBookmarkCount, placeholderTV, headerTV, textTV;
    RecyclerView bookmarksRV;
    ShimmerFrameLayout shimmerFrameLayout;
    SwipeRefreshLayout bookmarksSRL;
    View noConnectionLL;
    Button errorBTN;
    BookmarksRecyclerviewAdapter bookmarksAdapter;
    List<VolumeBooks> list;
    DatabaseHelper db;

    public BookmarksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookmarks, container, false);

        setHasOptionsMenu(true);

        tvBookmarkCount = view.findViewById(R.id.tv_bookmarks_count);
        bookmarksRV = view.findViewById(R.id.bookmarksRV);
        noConnectionLL = view.findViewById(R.id.noConnectionLL);
        shimmerFrameLayout = view.findViewById(R.id.shimmerFrameLayout);
        bookmarksSRL = view.findViewById(R.id.bookmarksSRL);
        headerTV = view.findViewById(R.id.headerTV);
        textTV = view.findViewById(R.id.textTV);
        errorBTN = view.findViewById(R.id.errorBTN);

        getActivity().setTitle("My Bookmarks");
        bookmarksSRL.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        bookmarksSRL.setOnRefreshListener(this);
        shimmerFrameLayout.setVisibility(View.VISIBLE);

        checkInternetConnection();
        loadBookmarks();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    private void checkInternetConnection() {
        InternetConnection.isInternetConnected(getContext(),noConnectionLL,bookmarksRV);
        headerTV.setText(R.string.no_internet_header);
        textTV.setText(R.string.no_internet_text);
        errorBTN.setVisibility(View.VISIBLE);
        errorBTN.setText(R.string.try_again);
    }

    private void loadBookmarks() {
        bookmarksSRL.setRefreshing(false);
        db = new DatabaseHelper(getContext());
        list = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        bookmarksRV.setLayoutManager(layoutManager);
        list = db.getAll();

        if (list.size()==1) {
            tvBookmarkCount.setText(list.size()+" item found");
        }else if (list.size()==0){
            tvBookmarkCount.setText("");
        }else {
            tvBookmarkCount.setText(list.size()+" items found");
        }

        bookmarksAdapter = new BookmarksRecyclerviewAdapter(getActivity(), list);
        shimmerFrameLayout.setVisibility(View.GONE);
        bookmarksRV.setAdapter(bookmarksAdapter);
        bookmarksAdapter.notifyDataSetChanged();

        if (list.isEmpty()) {
            noConnectionLL.setVisibility(View.VISIBLE);
            bookmarksRV.setVisibility(View.GONE);
            headerTV.setText(R.string.no_bookmarks);
            textTV.setText(R.string.no_bookmarks_placeholder);
            errorBTN.setVisibility(View.GONE);
        } else {
            bookmarksRV.setVisibility(View.VISIBLE);
            noConnectionLL.setVisibility(View.GONE);
            shimmerFrameLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        shimmerFrameLayout.stopShimmer();
        super.onPause();
    }

    @Override
    public void onRefresh() {
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        bookmarksRV.setVisibility(View.GONE);
        loadBookmarks();
    }

    @Override
    public void onDestroyView() {
        db.close();
        super.onDestroyView();
    }
}
