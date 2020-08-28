package com.example.bookfinderapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookfinderapp.DBManager;
import com.example.bookfinderapp.R;
import com.example.bookfinderapp.models.VolumeBooks;

import java.util.List;

public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.ViewHolder> {

    private Context context;
    private List<VolumeBooks> listdata;
    private RequestOptions options;

    public BookmarksAdapter(Context context, List<VolumeBooks> listdata) {
        this.context = context;
        this.listdata = listdata;

        //requesting options for Glide
        options = new RequestOptions().centerCrop().placeholder(R.drawable.custom_loading_image).error(R.drawable.custom_loading_image);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_book_card, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VolumeBooks data = listdata.get(position);

        holder.tvTitle.setText(data.getTitle());
        holder.tvAuthor.setText("by " + data.getAuthors());
        holder.rb_ratings.setRating((float) data.getAverageRating());
        holder.tvRatings.setText(data.getAverageRating() + "");
        holder.tvRatingsCount.setText("/ " + data.getRatingsCount() + "");
        holder.tvPublisher.setText(data.getPublisher());

        Glide.with(context).load(data.getThumbnail()).apply(options).into(holder.ivThumbnail);

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
