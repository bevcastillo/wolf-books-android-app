package com.example.bookfinderapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookfinderapp.R;
import com.example.bookfinderapp.models.VolumeBooks;

import java.util.List;

public class VolumeBooksAdapter extends RecyclerView.Adapter<VolumeBooksAdapter.ViewHolder> {

    private Context context;
    private List<VolumeBooks> listdata;
    private RequestOptions options;


    public VolumeBooksAdapter(Context context, List<VolumeBooks> listdata) {
        this.context = context;
        this.listdata = listdata;

        //requesting options for Glide
        options = new RequestOptions().centerCrop().placeholder(R.drawable.owl_logo).error(R.drawable.owl_logo);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_bookslist, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.cvBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "You have pressed me!", Toast.LENGTH_SHORT).show();
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VolumeBooks volumeBooks = listdata.get(position);

        holder.tvTitle.setText(volumeBooks.getTitle());
        holder.tvAuthor.setText(volumeBooks.getAuthors());
        holder.tvPublisher.setText(volumeBooks.getPublisher());
        holder.tvPublished.setText(volumeBooks.getPublishedDate());

        Glide.with(context).load(volumeBooks.getThumbnail()).apply(options).into(holder.ivThumbnail);

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cvBooks;
        ImageView ivThumbnail;
        TextView tvTitle, tvAuthor, tvPublisher, tvPublished;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cvBooks = itemView.findViewById(R.id.card_books);
            ivThumbnail = itemView.findViewById(R.id.iv_book_image);
            tvTitle = itemView.findViewById(R.id.tv_book_title);
            tvAuthor = itemView.findViewById(R.id.tv_book_author);
            tvPublisher = itemView.findViewById(R.id.tv_book_publisher);
            tvPublished = itemView.findViewById(R.id.tv_book_published);


        }
    }

}
