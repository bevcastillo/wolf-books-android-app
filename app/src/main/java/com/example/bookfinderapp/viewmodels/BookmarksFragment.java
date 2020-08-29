package com.example.bookfinderapp.viewmodels;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookfinderapp.R;
import com.example.bookfinderapp.adapters.BookmarksAdapter;
import com.example.bookfinderapp.helper.DatabaseHelper;
import com.example.bookfinderapp.models.VolumeBooks;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookmarksFragment extends Fragment {

    TextView tvBookmarkCount;
    RecyclerView rvBookmarks;
    LinearLayout layoutNoData;
    ShimmerFrameLayout shimmerFrameLayout;

    List<VolumeBooks> list;
    BookmarksAdapter adapter;

    DatabaseHelper db;


    public BookmarksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bookmarks, container, false);

        tvBookmarkCount = view.findViewById(R.id.tv_bookmarks_count);
        rvBookmarks = view.findViewById(R.id.rv_bookmarks);
        layoutNoData = view.findViewById(R.id.layout_no_data);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);

        getActivity().setTitle("My Bookmarks");

        db = new DatabaseHelper(view.getContext());
        list = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        rvBookmarks.setLayoutManager(layoutManager);
        list = db.getAll();

        tvBookmarkCount.setText(list.size()+" items found");

        loadBookmarks();


        return view;
    }

    private void loadBookmarks() {

        adapter = new BookmarksAdapter(getActivity(), list);

        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);

        rvBookmarks.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if (list.isEmpty()) {
            layoutNoData.setVisibility(View.VISIBLE);
        } else {
            layoutNoData.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        shimmerFrameLayout.stopShimmer();
        super.onPause();
    }

}
