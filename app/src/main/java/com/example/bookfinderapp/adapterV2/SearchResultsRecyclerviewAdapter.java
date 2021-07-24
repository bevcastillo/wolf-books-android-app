package com.example.bookfinderapp.adapterV2;

import android.content.Context;
import android.content.Intent;
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
import com.example.bookfinderapp.modelV2.Item;
import com.example.bookfinderapp.view.activity.BookInfoActivity;

import java.util.List;

public class CategoriesRecyclerviewAdapter extends RecyclerView.Adapter<CategoriesRecyclerviewAdapter.ViewHolder> {
    private Context context;
    private List<Item> items;

    public CategoriesRecyclerviewAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_book_card_layout, parent, false);
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
    public void onBindViewHolder(@NonNull CategoriesRecyclerviewAdapter.ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.titleTV.setText(item.getVolumeInfo().getTitle());

        try {
            Glide.with(context).load(item.getVolumeInfo().getImageLinks().getSmallThumbnail()).centerCrop().into(holder.imageView);
        }catch (Exception e) {
            Glide.with(context).load("https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/No-Image-Placeholder.svg/488px-No-Image-Placeholder.svg.png")
                    .centerCrop().into(holder.imageView);
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
        TextView titleTV, authorTV;
        ImageView imageView;
        RatingBar averageRatingRB;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.titleTV);
            authorTV = itemView.findViewById(R.id.authorTV);
            imageView = itemView.findViewById(R.id.imageView);
            averageRatingRB = itemView.findViewById(R.id.averageRatingRB);
        }
    }
}
