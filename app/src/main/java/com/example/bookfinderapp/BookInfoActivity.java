package com.example.bookfinderapp;

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

public class BookInfoActivity extends AppCompatActivity {

    TextView tvTitle, tvAuthor, tvDesc, tvPublisher, tvPublishedOn, tvisbn, tvPageCount, tvLang, tvPublisherheader;
    RatingBar rbRatings;
    ImageView ivThumbnail;
    Button btnPreview, btnBuy;
    String strTitle, strAuthor, strDesc, strPublisher, strPublishedOn, strIsbn, strPageCount, strLang,
            strRatings, strPrevLink, strBuyLink, strThumbnail;
    int pageCount, ratings;

    RequestOptions requestO = new RequestOptions().centerCrop().placeholder(R.drawable.owl_logo).error(R.drawable.owl_logo);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);

        ivThumbnail = findViewById(R.id.iv_book_thumbnail);
        tvTitle = findViewById(R.id.tv_title);
        tvAuthor = findViewById(R.id.tv_author);
        rbRatings = findViewById(R.id.ratings);
        btnPreview = findViewById(R.id.btn_preview);
        btnBuy = findViewById(R.id.btn_buy);
        tvDesc = findViewById(R.id.tv_desc);
        tvPublisher = findViewById(R.id.tv_publisher);
        tvPublishedOn = findViewById(R.id.tv_publishedOn);
        tvisbn = findViewById(R.id.tv_isbn);
        tvPageCount = findViewById(R.id.tv_pageCount);
        tvLang = findViewById(R.id.tv_language);
        tvPublisherheader = findViewById(R.id.tv_publisher_1);

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
            ratings = bundle.getInt("book_ratings");
            strThumbnail = bundle.getString("book_thumbnail");
            strPrevLink = bundle.getString("book_prevLink");
            strBuyLink = bundle.getString("book_buyLink");

            tvTitle.setText(strTitle);
            tvAuthor.setText(strAuthor);
            rbRatings.setNumStars(ratings);
            tvDesc.setText(strDesc);
            tvPublisher.setText(strPublisher);
            tvPublishedOn.setText(strPublishedOn);
            tvLang.setText(strLang);
            tvPublisherheader.setText(strPublisher);
//            tvPageCount.setText(pageCount);

            Glide.with(this).load(strThumbnail).apply(requestO).into(ivThumbnail);

            final String finalLinkPreview = strPrevLink;
            final String finalBuyLink = strBuyLink;

            btnPreview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalLinkPreview));
                    startActivity(intent);
                }
            });

            btnBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalBuyLink));
                    startActivity(intent);
                }
            });
        }
    }
}
