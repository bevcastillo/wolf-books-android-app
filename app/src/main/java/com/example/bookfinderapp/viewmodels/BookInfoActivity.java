package com.example.bookfinderapp.viewmodels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookfinderapp.R;

public class BookInfoActivity extends AppCompatActivity {

    TextView tvTitle, tvAuthor, tvDesc, tvPublisher, tvPublishedOn, tvisbn, tvPageCount, tvLang,
            tvRatingsCount, tvCategories, tvCategoryChip;
    RatingBar rbRatings;
    ImageView ivThumbnail;
    Button btnPreview, btnBuy;
    String strTitle, strAuthor, strDesc, strPublisher, strPublishedOn, strIsbn, strPrice, strLang,
            strPrevLink, strBuyLink, strThumbnail, strCategories;
    int pageCount, ratingsCount;
    double ratings;

    RequestOptions requestO = new RequestOptions().centerCrop().placeholder(R.drawable.custom_loading_image).error(R.drawable.custom_loading_image);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);

        ivThumbnail = findViewById(R.id.iv_book_thumbnail);
        tvTitle = findViewById(R.id.tv_title);
        tvAuthor = findViewById(R.id.tv_author);
        btnPreview = findViewById(R.id.btn_preview);
        btnBuy = findViewById(R.id.btn_buy);
        tvDesc = findViewById(R.id.tv_desc);
        tvPublisher = findViewById(R.id.tv_publisher);
        tvPublishedOn = findViewById(R.id.tv_publishedOn);
        tvisbn = findViewById(R.id.tv_isbn);
        tvPageCount = findViewById(R.id.tv_pageCount);
        tvLang = findViewById(R.id.tv_language);
        tvCategories = findViewById(R.id.tv_categories);
        rbRatings = findViewById(R.id.ratingbar_book);
        tvRatingsCount = findViewById(R.id.tv_reviews_count);
        tvCategoryChip = findViewById(R.id.tv_categories_chip);

        setTitle("");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            strTitle = bundle.getString("book_title");
            strAuthor = bundle.getString("book_auth");
            strDesc = bundle.getString("book_desc");
            strLang = bundle.getString("book_lang");
            strPublisher = bundle.getString("book_publisher");
            strPublishedOn = bundle.getString("book_publishedOn");
            pageCount = bundle.getInt("book_pageCount");
            ratings = bundle.getDouble("book_ratings");
            strThumbnail = bundle.getString("book_thumbnail");
            strPrevLink = bundle.getString("book_prevLink");
            strBuyLink = bundle.getString("book_buyLink");
            strPrice = bundle.getString("book_price");
            strCategories = bundle.getString("book_categories");
            ratingsCount = bundle.getInt("book_ratingsCount");


            tvTitle.setText(strTitle);
            tvAuthor.setText("by "+strAuthor);
            tvDesc.setText(strDesc);
            tvPublisher.setText(strPublisher);
            tvPublishedOn.setText(strPublishedOn);
            tvLang.setText(strLang);
            tvCategories.setText(strCategories);
            rbRatings.setRating((float) ratings);
            tvRatingsCount.setText(ratingsCount+" Reviews");
            tvPageCount.setText(pageCount+"");
            tvCategoryChip.setText(strCategories);
//            tvPageCount.setText(pageCount);



            Glide.with(this).load(strThumbnail).apply(requestO).into(ivThumbnail);

            final String finalLinkPreview = strPrevLink;
            final String finalBuyLink = strBuyLink;

            if (strPrice.equals("Not For Sale")) {
                btnBuy.setText(strPrice);
                btnBuy.setEnabled(false); //disable the link button if the book is not for sale
            }else {
                btnBuy.setText("Buy for "+strPrice);
                btnBuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalBuyLink));
                        startActivity(intent);
                    }
                });
            }

            btnPreview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalLinkPreview));
                    startActivity(intent);
                }
            });


        }
    }
}
