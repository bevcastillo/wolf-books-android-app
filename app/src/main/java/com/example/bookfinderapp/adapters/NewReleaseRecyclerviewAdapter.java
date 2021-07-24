package com.example.bookfinderapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_new_books, parent, false);
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
        holder.numberTV.setText("#"+pos);

        try {
            Glide.with(context).load(item.getVolumeInfo().getImageLinks().getSmallThumbnail()).centerCrop().into(holder.imageIV);
        }catch (Exception e) {
            Glide.with(context).load(Constant.N0_IMAGE_PLACEHOLDER)
                    .centerCrop().into(holder.imageIV);
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.descriptionTV.setText(Html.fromHtml(item.getSearchInfo().getTextSnippet(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                holder.descriptionTV.setText(Html.fromHtml(item.getSearchInfo().getTextSnippet()));
            }
        }catch (Exception e) {
            holder.descriptionTV.setText("No description");
        }

        try {
            holder.ratingsRB.setVisibility(View.VISIBLE);
            holder.ratingsRB.setRating(item.getVolumeInfo().getAverageRating());
        }catch (Exception e) {
            holder.ratingsRB.setVisibility(View.GONE);
            holder.ratingsTV.setVisibility(View.GONE);
            holder.noRatingPlaceholderTV.setText("No Rating");
        }

        try {
            holder.noRatingPlaceholderTV.setVisibility(View.GONE);

            if (item.getVolumeInfo().getRatingsCount()==1) {
                holder.ratingsTV.setText("("+item.getVolumeInfo().getRatingsCount()+" review)");
            }
            holder.ratingsTV.setText("("+item.getVolumeInfo().getRatingsCount()+" reviews)");
        }catch (Exception e) {
            holder.ratingsTV.setVisibility(View.GONE);
            holder.ratingsRB.setVisibility(View.GONE);
            holder.noRatingPlaceholderTV.setVisibility(View.GONE);
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
            holder.authorTV.setTypeface(null, Typeface.ITALIC);
            holder.authorTV.setText("Not Available");
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTV, numberTV, authorTV, descriptionTV, placeholder, ratingsTV, noRatingPlaceholderTV;
        ImageView imageIV;
        RatingBar ratingsRB;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.titleTV);
            numberTV = itemView.findViewById(R.id.numberTV);
            authorTV = itemView.findViewById(R.id.authorTV);
            descriptionTV = itemView.findViewById(R.id.descriptionTV);
            imageIV = itemView.findViewById(R.id.imageIV);
            ratingsRB = itemView.findViewById(R.id.ratingsRB);
            placeholder = itemView.findViewById(R.id.placeholder);
            ratingsTV = itemView.findViewById(R.id.ratingsTV);
            noRatingPlaceholderTV = itemView.findViewById(R.id.noRatingPlaceholderTV);
        }
    }
}
