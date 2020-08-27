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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookmarksFragment extends Fragment {

    TextView tvBookmarkCount;
    RecyclerView rvBookmarks;

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

        getActivity().setTitle("Bookmarks");

        db = new DatabaseHelper(view.getContext());
        list = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        rvBookmarks.setLayoutManager(layoutManager);
        list = db.getAll();

        tvBookmarkCount.setText(list.size()+" items found.");

        adapter = new BookmarksAdapter(view.getContext(), list);
        rvBookmarks.setAdapter(adapter);

//        list = new ArrayList<>();
//        list = db.getAll();
//        adapter = new BookmarksAdapter(view.getContext(), list);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
//        rvBookmarks.setLayoutManager(layoutManager);
//        rvBookmarks.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, true));
//        rvBookmarks.setItemAnimator(new DefaultItemAnimator());
//
//        rvBookmarks.setAdapter(adapter);
//
//        adapter.notifyDataSetChanged();




        return view;
    }

}
