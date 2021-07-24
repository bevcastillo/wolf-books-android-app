package com.example.bookfinderapp.adapters;

import android.content.Context;
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
import com.bumptech.glide.request.RequestOptions;
import com.example.bookfinderapp.R;
import com.example.bookfinderapp.modelV2.Item;

import java.util.List;

public class BookSubjectRecyclerviewAdapter extends RecyclerView.Adapter<BookSubjectRecyclerviewAdapter.ViewHolder> {
    private Context context;
    private List<Item> items;

    public BookSubjectRecyclerviewAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_book_card_layout, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "LINK : "+ items.get(viewHolder.getAdapterPosition()).getVolumeInfo().getImageLinks().getSmallThumbnail().toString(), Toast.LENGTH_LONG).show();
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookSubjectRecyclerviewAdapter.ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.titleTV.setText(item.getVolumeInfo().getTitle());
        Glide.with(context).load(item.getVolumeInfo().getImageLinks().getSmallThumbnail()).centerCrop().into(holder.imageView);
        try {
            holder.averageRatingRB.setRating(item.getVolumeInfo().getAverageRating());
        }catch (NullPointerException e) {
            holder.averageRatingRB.setVisibility(View.GONE);
            holder.noRatingPlaceholderTV.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTV, noRatingPlaceholderTV;
        ImageView imageView;
        RatingBar averageRatingRB;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.titleTV);
            noRatingPlaceholderTV = itemView.findViewById(R.id.noRatingPlaceholderTV);
            imageView = itemView.findViewById(R.id.coverImage);
            averageRatingRB = itemView.findViewById(R.id.averageRatingRB);
        }
    }
}
