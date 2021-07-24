package com.example.bookfinderapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookfinderapp.R;
import com.example.bookfinderapp.helper.DBManager;
import com.example.bookfinderapp.helper.DatabaseHelper;
import com.example.bookfinderapp.models.BooksVolume;
import com.example.bookfinderapp.models.VolumeBooks;
import com.example.bookfinderapp.view.activity.BookInfoActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class BooksVolumeAdapter extends RecyclerView.Adapter<BooksVolumeAdapter.ViewHolder> {

    private Context context;
    private List<BooksVolume> listdata;
    private List<BooksVolume> list;
    private RequestOptions options;


    public BooksVolumeAdapter(Context context, List<BooksVolume> listdata) {
        this.context = context;
        this.listdata = listdata;

        //requesting options for Glide
        options = new RequestOptions().centerCrop().placeholder(R.drawable.custom_loading_image).error(R.drawable.custom_loading_image);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_book_card_horizontal, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.ivBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = listdata.get(viewHolder.getAdapterPosition()).getVolumeInfo().title;
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        BooksVolume volumeBooks = listdata.get(position);
        holder.tvTitle.setBackground(null);
        holder.tvTitle.setText(volumeBooks.getVolumeInfo().title);

    }

    @Override
    public int getItemCount() {
        return listdata.size();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView layout;
        ImageView ivThumbnail, ivBookmark;
        TextView tvTitle, tvAuthor, tvRatings, tvRatingsCount, tvPublisher;
        RatingBar rb_ratings;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.book_layout);
            ivThumbnail = itemView.findViewById(R.id.iv_book_image);
            tvTitle = itemView.findViewById(R.id.tv_book_title);
            tvAuthor = itemView.findViewById(R.id.tv_book_author);
            rb_ratings = itemView.findViewById(R.id.ratingbar_book);
            tvRatings = itemView.findViewById(R.id.tv_ratings);
            tvRatingsCount = itemView.findViewById(R.id.tv_ratingsCount);
            tvPublisher = itemView.findViewById(R.id.tv_publisher);
            ivBookmark = itemView.findViewById(R.id.iv_bookmark);


        }
    }

}
