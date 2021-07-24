package com.example.bookfinderapp.adapters;

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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookfinderapp.helper.DBManager;
import com.example.bookfinderapp.helper.DatabaseHelper;
import com.example.bookfinderapp.view.activity.BookInfoActivity;
import com.example.bookfinderapp.R;
import com.example.bookfinderapp.models.VolumeBooks;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class VolumeBooksAdapter extends RecyclerView.Adapter<VolumeBooksAdapter.ViewHolder> {

    private Context context;
    private List<VolumeBooks> listdata;
    private List<VolumeBooks> list;
    private RequestOptions options;
    private DatabaseHelper db;
    private DBManager dbManager;


    public VolumeBooksAdapter(Context context, List<VolumeBooks> listdata) {
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
                String str_id = listdata.get(viewHolder.getAdapterPosition()).getStr_id();
                String title = listdata.get(viewHolder.getAdapterPosition()).getTitle();
                String author = listdata.get(viewHolder.getAdapterPosition()).getAuthors();
                double ratings = listdata.get(viewHolder.getAdapterPosition()).getAverageRating();
                String previewLink = listdata.get(viewHolder.getAdapterPosition()).getPreviewLink();
                String buyLink = listdata.get(viewHolder.getAdapterPosition()).getBuyLink();
                String description = listdata.get(viewHolder.getAdapterPosition()).getDescription();
                String publisher = listdata.get(viewHolder.getAdapterPosition()).getPublisher();
                String publishedOn = listdata.get(viewHolder.getAdapterPosition()).getPublishedDate();
                int pageCount = listdata.get(viewHolder.getAdapterPosition()).getPageCount();
                String language = listdata.get(viewHolder.getAdapterPosition()).getLanguage();
                String price = listdata.get(viewHolder.getAdapterPosition()).getPrice();
                String thumbnail = listdata.get(viewHolder.getAdapterPosition()).getThumbnail();
                String categories = listdata.get(viewHolder.getAdapterPosition()).getCategories();
                int ratingsCount = listdata.get(viewHolder.getAdapterPosition()).getRatingsCount();
                String currency = listdata.get(viewHolder.getAdapterPosition()).getCurrencyCode();
                String volumeId = listdata.get(viewHolder.getAdapterPosition()).getVolumeId();

                dbManager = new DBManager(v.getContext());
                dbManager.open();
                db = new DatabaseHelper(v.getContext());

                VolumeBooks volumeBooks = new VolumeBooks(str_id, title,author,description,publisher,publishedOn,categories,
                                                            thumbnail,previewLink,price,currency,buyLink,language,
                                                            pageCount,ratings,ratingsCount,true);
                db.addBookmark(volumeBooks);
                Snackbar.make(view, title+" has been added to bookmark list", Snackbar.LENGTH_LONG).show();

                viewHolder.ivBookmark.setImageResource(R.drawable.ic_bookmark_primary);
            }
        });

        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = listdata.get(viewHolder.getAdapterPosition()).getTitle();
                String author = listdata.get(viewHolder.getAdapterPosition()).getAuthors();
                double ratings = listdata.get(viewHolder.getAdapterPosition()).getAverageRating();
                String previewLink = listdata.get(viewHolder.getAdapterPosition()).getPreviewLink();
                String buyLink = listdata.get(viewHolder.getAdapterPosition()).getBuyLink();
                String description = listdata.get(viewHolder.getAdapterPosition()).getDescription();
                String publisher = listdata.get(viewHolder.getAdapterPosition()).getPublisher();
                String publishedOn = listdata.get(viewHolder.getAdapterPosition()).getPublishedDate();
                int pageCount = listdata.get(viewHolder.getAdapterPosition()).getPageCount();
                String language = listdata.get(viewHolder.getAdapterPosition()).getLanguage();
                String price = listdata.get(viewHolder.getAdapterPosition()).getPrice();
                String thumbnail = listdata.get(viewHolder.getAdapterPosition()).getThumbnail();
                String categories = listdata.get(viewHolder.getAdapterPosition()).getCategories();
                int ratingsCount = listdata.get(viewHolder.getAdapterPosition()).getRatingsCount();
                String currency = listdata.get(viewHolder.getAdapterPosition()).getCurrencyCode();
                String volumeId = listdata.get(viewHolder.getAdapterPosition()).getVolumeId();

//                passing data to BookInfoActivity
                Intent intent = new Intent(v.getContext(), BookInfoActivity.class);
                intent.putExtra("book_title", title);
                intent.putExtra("book_auth", author);
                intent.putExtra("book_ratings", ratings);
                intent.putExtra("book_prevLink", previewLink);
                intent.putExtra("book_buyLink", buyLink);
                intent.putExtra("book_desc", description);
                intent.putExtra("book_publisher", publisher);
                intent.putExtra("book_publishedOn", publishedOn);
                intent.putExtra("book_pageCount", pageCount);
                intent.putExtra("book_lang", language);
                intent.putExtra("book_price", price);
                intent.putExtra("book_thumbnail", thumbnail);
                intent.putExtra("book_categories", categories);
                intent.putExtra("book_ratingsCount", ratingsCount);
                intent.putExtra("book_currency", currency);
                intent.putExtra("book_vol_id", volumeId);

                v.getContext().startActivity(intent);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        VolumeBooks volumeBooks = listdata.get(position);
        Boolean isBookmark = volumeBooks.isBookmark();

        dbManager = new DBManager(context);
        dbManager.open();
        db = new DatabaseHelper(context);

        holder.ivThumbnail.setBackground(null);
        Glide.with(context).load(volumeBooks.getThumbnail()).apply(options).into(holder.ivThumbnail);

        holder.tvTitle.setBackground(null);
        holder.tvTitle.setText(volumeBooks.getTitle());

        holder.tvAuthor.setBackground(null);
        holder.tvAuthor.setText(volumeBooks.getAuthors());

        holder.rb_ratings.setBackground(null);
        holder.rb_ratings.setVisibility(View.VISIBLE);
        holder.rb_ratings.setRating((float) volumeBooks.getAverageRating());

        holder.tvRatings.setBackground(null);
        holder.tvRatings.setText(volumeBooks.getAverageRating() + "");

        holder.tvRatingsCount.setBackground(null);
        holder.tvRatingsCount.setText("");

        holder.tvPublisher.setBackground(null);
        holder.tvPublisher.setText(volumeBooks.getPublisher());

        holder.ivBookmark.setBackground(null);
        holder.ivBookmark.setImageResource(R.drawable.ic_bookmark_border_primary);

//        if (db.getVolumeId(volumeBooks.getVolumeId())!=null) {
//            holder.ivBookmark.setImageResource(R.drawable.ic_bookmark_border_primary);
//        } else {
//            holder.ivBookmark.setImageResource(R.drawable.ic_bookmark_primary);
//        }
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
