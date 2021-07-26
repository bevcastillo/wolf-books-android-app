package com.example.bookfinderapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookfinderapp.R;
import com.example.bookfinderapp.helper.Constant;
import com.example.bookfinderapp.model.api.Item;
import com.example.bookfinderapp.view.activity.BookInfoActivity;

import java.util.List;

public class NewReleaseRecyclerviewAdapter extends RecyclerView.Adapter<NewReleaseRecyclerviewAdapter.ViewHolder> {
    private Context context;
    private List<Item> items;

    public NewReleaseRecyclerviewAdapter(Context context, List<Item> items) {
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

                //passing data from adapter to activity using intent
                Intent intent = new Intent(v.getContext(), BookInfoActivity.class);
                intent.putExtra("volume_id", volumeId);
                v.getContext().startActivity(intent);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewReleaseRecyclerviewAdapter.ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.titleTV.setText(item.getVolumeInfo().getTitle());
        int pos = position+1;

        holder.titleTV.setText(item.getVolumeInfo().getTitle());
        holder.bookmarkIV.setVisibility(View.GONE);
        holder.bookmarkActiveIV.setVisibility(View.GONE);

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
            holder.RatingRB.setVisibility(View.VISIBLE);
            holder.noRatingTV.setVisibility(View.GONE);
            holder.RatingRB.setRating(item.getVolumeInfo().getAverageRating());
        }catch (Exception e) {
            holder.noRatingTV.setText("No Rating");
            holder.RatingRB.setVisibility(View.GONE);
        }

        try {
            if (item.getVolumeInfo().getRatingsCount()==1) {
                holder.ratingsTV.setVisibility(View.VISIBLE);
                holder.ratingsTV.setText("("+item.getVolumeInfo().getRatingsCount()+" review)");
            }else {
                holder.ratingsTV.setVisibility(View.VISIBLE);
                holder.ratingsTV.setText("("+item.getVolumeInfo().getRatingsCount()+" reviews)");
            }
        }catch (Exception e) {
            holder.ratingsTV.setVisibility(View.GONE);
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
        ImageView imageView, bookmarkIV, bookmarkActiveIV;
        TextView publisherTV, titleTV, authorTV, noRatingTV, ratingsTV, tv_ratingsCount;
        RatingBar RatingRB;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            publisherTV = itemView.findViewById(R.id.publisherTV);
            titleTV = itemView.findViewById(R.id.titleTV);
            authorTV = itemView.findViewById(R.id.authorTV);
            noRatingTV = itemView.findViewById(R.id.noRatingTV);
            ratingsTV = itemView.findViewById(R.id.ratingsTV);
            tv_ratingsCount = itemView.findViewById(R.id.tv_ratingsCount);
            RatingRB = itemView.findViewById(R.id.RatingRB);
            bookmarkIV = itemView.findViewById(R.id.bookmarkIV);
            bookmarkActiveIV = itemView.findViewById(R.id.bookmarkActiveIV);
        }
    }
}
