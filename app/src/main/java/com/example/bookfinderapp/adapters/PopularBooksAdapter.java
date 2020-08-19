package com.example.bookfinderapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookfinderapp.R;
import com.example.bookfinderapp.models.VolumeBooks;

import java.util.List;

public class PopularBooksAdapter extends RecyclerView.Adapter<PopularBooksAdapter.ViewHolder> {
    private Context context;
    private List<VolumeBooks> listdata;
    private RequestOptions options;

    public PopularBooksAdapter(Context context, List<VolumeBooks> listdata) {
        this.context = context;
        this.listdata = listdata;

        options = new RequestOptions().centerCrop().placeholder(R.drawable.custom_loading_image).error(R.drawable.custom_loading_image);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_single_books, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VolumeBooks volumeBooks = listdata.get(position);

        holder.tvTitle.setText(volumeBooks.getTitle());
        holder.rbRatings.setRating((float) volumeBooks.getAverageRating());

        Glide.with(context).load(volumeBooks.getThumbnail()).apply(options).into(holder.ivThumbnail);

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivThumbnail;
        TextView tvTitle;
        RatingBar rbRatings;
        ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivThumbnail = itemView.findViewById(R.id.iv_image);
            tvTitle = itemView.findViewById(R.id.tv_title);
            rbRatings = itemView.findViewById(R.id.ratingbar_book);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
