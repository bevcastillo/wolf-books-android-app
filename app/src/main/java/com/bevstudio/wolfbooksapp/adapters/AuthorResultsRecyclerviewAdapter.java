package com.bevstudio.wolfbooksapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bevstudio.wolfbooksapp.R;
import com.bevstudio.wolfbooksapp.helper.Constant;
import com.bevstudio.wolfbooksapp.model.api.Item;
import com.bevstudio.wolfbooksapp.vendor.NumberFormatter;
import com.bevstudio.wolfbooksapp.view.activity.BookInfoActivity;

import java.util.List;

public class AuthorResultsRecyclerviewAdapter extends RecyclerView.Adapter<AuthorResultsRecyclerviewAdapter.ViewHolder> {
    private Context context;
    private List<Item> items;
    private String bookmarkedStatus="";

    public AuthorResultsRecyclerviewAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_book_card_author, parent, false);
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
    public void onBindViewHolder(@NonNull AuthorResultsRecyclerviewAdapter.ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.titleTV.setText(item.getVolumeInfo().getTitle());

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
            holder.authorTV.setText(R.string.dash);
        }

        try{
            holder.publisherTV.setText(item.getVolumeInfo().getPublisher());
        }catch (Exception e) {
            holder.publisherTV.setText(R.string.dash);
        }

        try {
            Glide.with(context).load(item.getVolumeInfo().getImageLinks().getSmallThumbnail()).centerCrop().into(holder.imageView);
        }catch (Exception e) {
            Glide.with(context).load(Constant.N0_IMAGE_PLACEHOLDER)
                    .centerCrop().into(holder.imageView);
        }

        try {
            if (item.getVolumeInfo().getRatingsCount()==1) {
                holder.ratingsTV.setText(item.getVolumeInfo().getAverageRating()+" avg rating — "+NumberFormatter.format(item.getVolumeInfo().getRatingsCount())+" rating");
            }else {
                holder.ratingsTV.setText(item.getVolumeInfo().getAverageRating()+" avg rating — "+NumberFormatter.format(item.getVolumeInfo().getRatingsCount())+" ratings");
            }
        }catch (Exception e) {
            holder.ratingsTV.setText(R.string.no_reviews);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView publisherTV, titleTV, ratingsTV, descriptionTV, authorTV;
        RatingBar RatingRB;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            publisherTV = itemView.findViewById(R.id.publisherTV);
            titleTV = itemView.findViewById(R.id.titleTV);
            ratingsTV = itemView.findViewById(R.id.ratingsTV);
            RatingRB = itemView.findViewById(R.id.RatingRB);
            descriptionTV = itemView.findViewById(R.id.descriptionTV);
            authorTV = itemView.findViewById(R.id.authorTV);
        }
    }
}
