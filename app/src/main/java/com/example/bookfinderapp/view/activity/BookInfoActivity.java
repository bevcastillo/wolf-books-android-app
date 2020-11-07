package com.example.bookfinderapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookfinderapp.R;
import com.example.bookfinderapp.helper.DBManager;
import com.example.bookfinderapp.helper.DatabaseHelper;
import com.example.bookfinderapp.models.VolumeBooks;
import com.google.android.material.snackbar.Snackbar;

public class BookInfoActivity extends AppCompatActivity {

    TextView tvTitle, tvAuthor, tvDesc, tvPublisher, tvPublishedOn, tvisbn, tvPageCount, tvLang,
            tvRatingsCount, tvCategories, tvRatings, tvCategoryChip;
    ConstraintLayout view;
    RatingBar rbRatings;
    ImageView ivThumbnail;
    Button btnPreview, btnBuy;
    String strTitle, strAuthor, strDesc, strPublisher, strPublishedOn, strCurrency, strPrice, strLang,
            strPrevLink, strBuyLink, strThumbnail, strCategories, strVolumeId, strRatings;
    int pageCount, ratingsCount;
    double ratings;

    private DatabaseHelper db;
    private DBManager dbManager;

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
        tvPageCount = findViewById(R.id.tv_pageCount);
        tvLang = findViewById(R.id.tv_language);
        tvCategories = findViewById(R.id.tv_categories);
        rbRatings = findViewById(R.id.ratingbar_book);
        tvRatingsCount = findViewById(R.id.tv_reviews_count);
        tvRatings = findViewById(R.id.tv_ratings);
        view = findViewById(R.id.view);


        setTitle("Book Details");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bookmark_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_bookmark:
                addToBookmark();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addToBookmark() {
        dbManager = new DBManager(this);
        dbManager.open();
        db = new DatabaseHelper(this);

        VolumeBooks volumeBooks = new VolumeBooks(strVolumeId, strTitle,strAuthor,strDesc,strPublisher,strPublishedOn,strCategories,
                strThumbnail,strPrevLink,strPrice,strCurrency,strBuyLink,strLang,
                pageCount,ratings,ratingsCount,true);

        db.addBookmark(volumeBooks);
        Snackbar.make(view, strTitle+" has been added to bookmarks", Snackbar.LENGTH_LONG).show();
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
            strCurrency = bundle.getString("book_currency");
            strVolumeId = bundle.getString("book_vol_id");


            tvTitle.setText(strTitle);
            tvAuthor.setText("by "+strAuthor);
            tvDesc.setText(strDesc);
            tvPublisher.setText(strPublisher);
            tvPublishedOn.setText(strPublishedOn);
            tvLang.setText(strLang);
            tvCategories.setText(strCategories);
            rbRatings.setRating((float) ratings);
            tvRatingsCount.setText("/"+ratingsCount+" Reviews");
            tvPageCount.setText(pageCount+" pages");
            tvRatings.setText(ratings+"");

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
