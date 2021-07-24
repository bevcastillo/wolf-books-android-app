package com.example.bookfinderapp.view.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bookfinderapp.R;
import com.example.bookfinderapp.adapters.BookmarksRecyclerviewAdapter;
import com.example.bookfinderapp.helper.DatabaseHelper;
import com.example.bookfinderapp.model.db.VolumeBooks;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookmarksFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    TextView tvBookmarkCount, placeholderTV, retryTV;
    RecyclerView rvBookmarks;
    LinearLayout layout_no_data;
    ShimmerFrameLayout shimmerFrameLayout;
    SwipeRefreshLayout bookmarksSRL;
    BookmarksRecyclerviewAdapter bookmarksAdapter; //re-use the existing adapter
    List<VolumeBooks> list;
    DatabaseHelper db;

    public BookmarksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookmarks, container, false);

        tvBookmarkCount = view.findViewById(R.id.tv_bookmarks_count);
        rvBookmarks = view.findViewById(R.id.rv_bookmarks);
        layout_no_data = view.findViewById(R.id.layout_no_data);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);
        bookmarksSRL = view.findViewById(R.id.bookmarksSRL);
        placeholderTV = view.findViewById(R.id.placeholderTV);
        retryTV = view.findViewById(R.id.retryTV);

        getActivity().setTitle("My Bookmarks");

        db = new DatabaseHelper(view.getContext());
        list = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        rvBookmarks.setLayoutManager(layoutManager);
        list = db.getAll();

        tvBookmarkCount.setText(list.size()+" item/s found");
        bookmarksSRL.setOnRefreshListener(this);

        loadBookmarks();
        return view;
    }

    private void loadBookmarks() {
        bookmarksSRL.setRefreshing(false);
        bookmarksAdapter = new BookmarksRecyclerviewAdapter(getActivity(), list);
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
        rvBookmarks.setAdapter(bookmarksAdapter);
        bookmarksAdapter.notifyDataSetChanged();

        if (list.isEmpty()) {
            layout_no_data.setVisibility(View.VISIBLE);
            placeholderTV.setText("No Bookmarks Found");
            retryTV.setVisibility(View.GONE);
        } else {
            layout_no_data.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        shimmerFrameLayout.stopShimmer();
        super.onPause();
    }

    @Override
    public void onRefresh() {
        loadBookmarks();
    }
}
