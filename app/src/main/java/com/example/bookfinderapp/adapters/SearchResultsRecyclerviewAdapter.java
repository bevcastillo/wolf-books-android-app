package com.example.bookfinderapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookfinderapp.R;
import com.example.bookfinderapp.helper.Constant;
import com.example.bookfinderapp.request.db.DatabaseHelper;
import com.example.bookfinderapp.model.api.Item;
import com.example.bookfinderapp.model.db.VolumeBooks;
import com.example.bookfinderapp.view.activity.BookInfoActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsRecyclerviewAdapter extends RecyclerView.Adapter<SearchResultsRecyclerviewAdapter.ViewHolder> {
    private Context context;
    private List<Item> items;
    private String bookmarkedStatus="";

    public SearchResultsRecyclerviewAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_book_card_horizontal, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String volumeId = items.get(viewHolder.getAdapterPosition()).getId();
                Intent intent = new Intent(v.getContext(), BookInfoActivity.class);
                intent.putExtra("volume_id", volumeId);
                intent.putExtra("is_bookmarked", bookmarkedStatus);
                v.getContext().startActivity(intent);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultsRecyclerviewAdapter.ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.titleTV.setText(item.getVolumeInfo().getTitle());

//        holder.bookmarkIV.setVisibility(View.GONE);
//        holder.bookmarkActiveIV.setVisibility(View.GONE);

//        for (int i=0; i<list.size(); i++) {
//            if (list.get(i).getVolumeId().equals(item.getId())) {
//                holder.bookmarkActiveIV.setVisibility(View.VISIBLE);
//                holder.bookmarkIV.setVisibility(View.GONE);
//                bookmarkedStatus="yes";
//                break;
//            }else {
//                holder.bookmarkActiveIV.setVisibility(View.GONE);
//                holder.bookmarkIV.setVisibility(View.VISIBLE);
//                bookmarkedStatus="no";
//            }
//        }

        try{
            holder.publisherTV.setText(item.getVolumeInfo().getPublisher());
        }catch (Exception e) {
            holder.publisherTV.setText("Not Available");
        }

        try {
            Glide.with(context).load(item.getVolumeInfo().getImageLinks().getSmallThumbnail()).centerCrop().into(holder.imageView);
        }catch (Exception e) {
            Glide.with(context).load(Constant.N0_IMAGE_PLACEHOLDER)
                    .centerCrop().into(holder.imageView);
        }
        try {
            if (item.getVolumeInfo().getRatingsCount()==1) {
                holder.ratingsTV.setText("("+item.getVolumeInfo().getRatingsCount()+" review)");
            }else {
                holder.ratingsTV.setText("("+item.getVolumeInfo().getRatingsCount()+" reviews)");
            }
            holder.RatingRB.setRating(item.getVolumeInfo().getAverageRating());
        }catch (Exception e) {
            holder.ratingsTV.setText(R.string.no_reviews);
            holder.ratingsTV.setVisibility(View.GONE);
        }

        try {
            holder.RatingRB.setVisibility(View.VISIBLE);
            holder.RatingRB.setRating(item.getVolumeInfo().getAverageRating());
        }catch (Exception e) {
            holder.RatingRB.setVisibility(View.GONE);
            holder.ratingsTV.setText(R.string.no_reviews);
        }

        try {
            switch (item.getVolumeInfo().getAuthors().size()) {
                case 1:
                    holder.authorTV.setText("By "+item.getVolumeInfo().getAuthors().get(0));
                    break;
                case 2:
                    holder.authorTV.setText("By "+item.getVolumeInfo().getAuthors().get(0)+", "+item.getVolumeInfo().getAuthors().get(1));
                    break;
                case 3:
                    holder.authorTV.setText("By "+item.getVolumeInfo().getAuthors().get(0)+", "+item.getVolumeInfo().getAuthors().get(1)+", "+item.getVolumeInfo().getAuthors().get(2));
                    break;
                case 4:
                    holder.authorTV.setText("By "+item.getVolumeInfo().getAuthors().get(0)+", "+
                            item.getVolumeInfo().getAuthors().get(1)+", "+
                            item.getVolumeInfo().getAuthors().get(2)+", "+item.getVolumeInfo().getAuthors().get(3));
                    break;
                case 5:
                    holder.authorTV.setText("By "+item.getVolumeInfo().getAuthors().get(0)+", "+item.getVolumeInfo().getAuthors().get(1)+", "+item.getVolumeInfo().getAuthors().get(2)+
                            ", "+item.getVolumeInfo().getAuthors().get(3)+", "+item.getVolumeInfo().getAuthors().get(4));
                    break;
                default:
                    holder.authorTV.setText("By "+item.getVolumeInfo().getAuthors().get(0));
            }
        }catch (Exception e) {
            holder.authorTV.setText("No Author");
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, bookmarkActiveIV, bookmarkIV;
        TextView publisherTV, titleTV, authorTV, ratingsTV, noRatingTV;
        RatingBar RatingRB;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            publisherTV = itemView.findViewById(R.id.publisherTV);
            titleTV = itemView.findViewById(R.id.titleTV);
            authorTV = itemView.findViewById(R.id.authorTV);
            ratingsTV = itemView.findViewById(R.id.ratingsTV);
            RatingRB = itemView.findViewById(R.id.RatingRB);
            noRatingTV = itemView.findViewById(R.id.noRatingTV);
            bookmarkActiveIV = itemView.findViewById(R.id.bookmarkActiveIV);
            bookmarkIV = itemView.findViewById(R.id.bookmarkIV);
        }
    }
}
