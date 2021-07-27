package com.example.wolfbooksapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wolfbooksapp.model.BestBooks;
import com.example.wolfbooksapp.R;
import com.example.wolfbooksapp.view.activity.BookInfoActivity;
import com.github.siyamed.shapeimageview.RoundedImageView;

public class BestBooksRecyclerviewAdapter extends RecyclerView.Adapter<BestBooksRecyclerviewAdapter.ViewHolder> {
    private Context context;

    public BestBooksRecyclerviewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_best_books, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String volume_id = BestBooks.volume_id[viewHolder.getAdapterPosition()];
                Intent intent = new Intent(v.getContext(), BookInfoActivity.class);
                intent.putExtra("volume_id", volume_id);
                v.getContext().startActivity(intent);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BestBooksRecyclerviewAdapter.ViewHolder holder, int position) {
        holder.textView.setText(position+1+"");
        holder.titleTV.setText(BestBooks.title[position]);
        holder.authorTV.setText("By "+BestBooks.author[position]);

        try {
            Glide.with(context).load(BestBooks.image[position]).into(holder.imageView);
        }catch (Exception e) {

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.descriptionTV.setText(Html.fromHtml(BestBooks.desc[position], Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.descriptionTV.setText(Html.fromHtml(BestBooks.desc[position]));
        }
    }

    @Override
    public int getItemCount() {
        return BestBooks.title.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView, titleTV, authorTV, descriptionTV;
        RoundedImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
            titleTV = itemView.findViewById(R.id.titleTV);
            authorTV = itemView.findViewById(R.id.authorTV);
            descriptionTV = itemView.findViewById(R.id.descriptionTV);
        }
    }
}
