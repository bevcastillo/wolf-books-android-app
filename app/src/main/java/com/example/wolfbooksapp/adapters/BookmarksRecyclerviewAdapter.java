package com.example.wolfbooksapp.adapters;

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
import com.example.wolfbooksapp.R;
import com.example.wolfbooksapp.helper.Constant;
import com.example.wolfbooksapp.request.db.DatabaseHelper;
import com.example.wolfbooksapp.model.api.Item;
import com.example.wolfbooksapp.model.db.VolumeBooks;
import com.example.wolfbooksapp.request.api.RequestService;
import com.example.wolfbooksapp.request.api.RetrofitClass;
import com.example.wolfbooksapp.vendor.NumberFormatter;
import com.example.wolfbooksapp.view.activity.BookInfoActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookmarksRecyclerviewAdapter extends RecyclerView.Adapter<BookmarksRecyclerviewAdapter.ViewHolder> {
    private Context context;
    private List<VolumeBooks> localVolumeBooks;
    private Call<Item> itemCall, itemCall1, itemCall2;
    private RequestService requestService = RetrofitClass.getAPIInstance();
    DatabaseHelper db;
//    DBManager dbManager;

    public BookmarksRecyclerviewAdapter(Context context, List<VolumeBooks> localVolumeBooks) {
        this.context = context;
        this.localVolumeBooks = localVolumeBooks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_book_card_horizontal, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.bookmarkActiveIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dbManager = new DBManager(context);
//                dbManager.open();
                db = new DatabaseHelper(context);

                VolumeBooks volumeBooks1 = localVolumeBooks.get(viewHolder.getAdapterPosition());
                itemCall2 = requestService.getBookItem(volumeBooks1.getVolumeId());
                itemCall2.enqueue(new Callback<Item>() {
                    @Override
                    public void onResponse(Call<Item> call, Response<Item> response) {
                        db.removeBookmark(volumeBooks1.getId());
//                        dbManager.close();
                        localVolumeBooks.remove(viewHolder.getAdapterPosition());
                        notifyItemRemoved(viewHolder.getAdapterPosition());
                        notifyItemChanged(viewHolder.getAdapterPosition(),localVolumeBooks.size());
                        Toast.makeText(context,response.body().getVolumeInfo().getTitle()+" has been removed.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Item> call, Throwable t) {

                    }
                });
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VolumeBooks volumeBooks1 = localVolumeBooks.get(viewHolder.getAdapterPosition());
                itemCall1 = requestService.getBookItem(volumeBooks1.getVolumeId());
                itemCall1.enqueue(new Callback<Item>() {
                    @Override
                    public void onResponse(Call<Item> call, Response<Item> response) {
                        if (response.isSuccessful()) {
                            //passing data from adapter to activity using intent
                            Intent intent = new Intent(v.getContext(), BookInfoActivity.class);
                            intent.putExtra("volume_id", response.body().getId());
                            intent.putExtra("bookmark_id", volumeBooks1.getId());
                            v.getContext().startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Item> call, Throwable t) {

                    }
                });
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarksRecyclerviewAdapter.ViewHolder holder, int position) {
        VolumeBooks volumeBooks = localVolumeBooks.get(position);
        itemCall = requestService.getBookItem(volumeBooks.getVolumeId());

        itemCall.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                Item item = response.body();
                if (response.isSuccessful()) {
                    holder.titleTV.setText(item.getVolumeInfo().getTitle());
                    holder.bookmarkIV.setVisibility(View.GONE);
                    holder.bookmarkActiveIV.setVisibility(View.VISIBLE);

                    holder.RatingRB.setVisibility(View.VISIBLE);

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
                            holder.ratingsTV.setText(item.getVolumeInfo().getAverageRating()+" avg rating — "+ NumberFormatter.format(item.getVolumeInfo().getRatingsCount())+" rating");
                        }else {
                            holder.ratingsTV.setText(item.getVolumeInfo().getAverageRating()+" avg rating — "+NumberFormatter.format(item.getVolumeInfo().getRatingsCount())+" ratings");
                        }
                    }catch (Exception e) {
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
                        holder.authorTV.setText(R.string.dash);
                    }
                }
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return localVolumeBooks.size();
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
