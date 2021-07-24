package com.example.bookfinderapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bookfinderapp.R;
import com.example.bookfinderapp.helper.Constant;
import com.example.bookfinderapp.helper.DBManager;
import com.example.bookfinderapp.helper.DatabaseHelper;
import com.example.bookfinderapp.model.api.AccessInfo;
import com.example.bookfinderapp.model.api.Item;
import com.example.bookfinderapp.model.api.SaleInfo;
import com.example.bookfinderapp.model.api.VolumeInfo;
import com.example.bookfinderapp.model.db.VolumeBooks;
import com.example.bookfinderapp.request.RequestService;
import com.example.bookfinderapp.request.RetrofitClass;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.jackandphantom.blurimage.BlurImage;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookInfoActivity extends AppCompatActivity implements View.OnClickListener {

    RoundedImageView bookImageIV;
    TextView publisherTV, titleTV, authorTV, noRatingPlaceholderTV, reviewCountTV, descriptionTV, categoriesTV,
            publishedDateTV, pageCountTV, languageTV, isbnsTV, ratingsTV, maturityRatingTV, previewBTN, printTypeTV2,
            printTypeTV, heightTV, widthTV, thicknessTV, placeholdersample;
    ShimmerFrameLayout  shimmer_view_container;
    ConstraintLayout layout_parent;
    Button buyLinkBTN, activeBookmark, inactiveBookmark;
    RatingBar averageRatingRB;
    RequestService requestService;
    Call<Item> singleItemCall;
    String volume_id="", description="", title="", bookmarked="";
    List<VolumeBooks> list = new ArrayList<>();
    DatabaseHelper db;
    DBManager dbManager;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);

        requestService = RetrofitClass.getAPIInstance();
        bookImageIV = findViewById(R.id.bookImageIV);
        ratingsTV = findViewById(R.id.ratingsTV);
        publisherTV = findViewById(R.id.publisherTV);
        titleTV = findViewById(R.id.titleTV);
        authorTV = findViewById(R.id.authorTV);
        noRatingPlaceholderTV = findViewById(R.id.noRatingPlaceholderTV);
        descriptionTV = findViewById(R.id.descriptionTV);
        categoriesTV = findViewById(R.id.categoriesTV);
        publishedDateTV = findViewById(R.id.publishedDateTV);
        isbnsTV = findViewById(R.id.isbnsTV);
        pageCountTV = findViewById(R.id.pageCountTV);
        languageTV = findViewById(R.id.languageTV);
        previewBTN = findViewById(R.id.previewBTN);
        buyLinkBTN = findViewById(R.id.buyLinkBTN);
        averageRatingRB  = findViewById(R.id.averageRatingRB);
        maturityRatingTV = findViewById(R.id.maturityRatingTV);
        printTypeTV2 = findViewById(R.id.printTypeTV2);
        printTypeTV = findViewById(R.id.printTypeTV);
        heightTV = findViewById(R.id.heightTV);
        widthTV = findViewById(R.id.widthTV);
        thicknessTV = findViewById(R.id.thicknessTV);
        shimmer_view_container = findViewById(R.id.shimmer_view_container);
        layout_parent = findViewById(R.id.layout_parent);
        activeBookmark = findViewById(R.id.activeBookmark);
        inactiveBookmark = findViewById(R.id.inactiveBookmark);
        dialog = new ProgressDialog(BookInfoActivity.this);


        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            volume_id = bundle.getString("volume_id");
            bookmarked = bundle.getString("is_bookmarked");
            displayBookItem(volume_id);
        }

        activeBookmark.setOnClickListener(this);
        inactiveBookmark.setOnClickListener(this);
    }

    private void addToBookmark() {
        dbManager = new DBManager(this);
        dbManager.open();
        db = new DatabaseHelper(this);
        VolumeBooks volumeBooks = new VolumeBooks(volume_id,true);
        db.addBookmark(volumeBooks);
        //refresh
        displayBookItem(volume_id);
        dialog.dismiss();
        inactiveBookmark.setVisibility(View.GONE);
        activeBookmark.setVisibility(View.VISIBLE);
        Toast.makeText(BookInfoActivity.this, title+" has been added to bookmarks", Toast.LENGTH_LONG).show();
    }

    void displayBookItem(String id) {
        singleItemCall = requestService.getBookItem(id);
        singleItemCall.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                Item item = response.body();
                VolumeInfo volume = response.body().getVolumeInfo();
                SaleInfo saleInfo = response.body().getSaleInfo();
                AccessInfo accessInfo = response.body().getAccessInfo();
                Boolean isEbook=true;

                if (response.code()!=200) {

                }

                if (response.isSuccessful()) {
                    shimmer_view_container.setVisibility(View.GONE);
                    layout_parent.setVisibility(View.VISIBLE);

                    db = new DatabaseHelper(BookInfoActivity.this);
                    list = db.getAll();

                    for (int i=0; i<list.size(); i++) {
                        if (list.get(i).getVolumeId().equals(volume_id)) {
                            activeBookmark.setVisibility(View.VISIBLE);
                            inactiveBookmark.setVisibility(View.GONE);
                            break;
                        }else {
                            activeBookmark.setVisibility(View.GONE);
                            inactiveBookmark.setVisibility(View.VISIBLE);
                        }
                    }

                    title = volume.getTitle();
                    setTitle(volume.getTitle()+"");
                    titleTV.setText(volume.getTitle());
                    publisherTV.setText(volume.getPublisher());
                    publishedDateTV.setText(volume.getPublishedDate());
                    pageCountTV.setText(volume.getPageCount()+" pages");
                    languageTV.setText(volume.getLanguage().toUpperCase());

                    try {
                        averageRatingRB.setVisibility(View.VISIBLE);
                        averageRatingRB.setRating(volume.getAverageRating());
                        if (volume.getRatingsCount()==1) {
                            noRatingPlaceholderTV.setText(volume.getRatingsCount()+" review");
                        }else {
                            noRatingPlaceholderTV.setText(volume.getRatingsCount()+" reviews");
                        }
                    }catch (Exception e) {
                        averageRatingRB.setVisibility(View.GONE);
                        noRatingPlaceholderTV.setVisibility(View.VISIBLE);
                        noRatingPlaceholderTV.setText("No Rating");
                    }

                    String categories="", authors="", isbns="";

                    try {
                        for (int i=0; i<volume.getCategories().size(); i++) {
                            categories = ""+volume.getCategories().get(i)+"\n";
                            categoriesTV.append(""+categories);
                        }
                    }catch (Exception e) {
                        categoriesTV.setText("Not Available");
                    }

                    try {
                        printTypeTV2.setText(volume.getPrintType());
                        printTypeTV.setText(volume.getPrintType());
                    }catch (Exception e) {

                    }

                    try {
                        String result="";
                        //loop to get authors
                        for (int j=0; j<volume.getAuthors().size(); j++) {
                            if (volume.getAuthors().size()==1) {
                                authors = ""+volume.getAuthors().get(j);
                            }else {
                                authors = ""+volume.getAuthors().get(j)+", ";
                            }
                            ratingsTV.append(""+authors);
                        }

                        if (volume.getAuthors().size()==1) {
                            result = ratingsTV.getText().toString().trim();
                        }else {
                            result = ratingsTV.getText().toString().substring(0, ratingsTV.getText().toString().length()-2);
                        }
                        authorTV.setText("By "+ result);

                    }catch (Exception e) {
                        authorTV.setText("Not Available");
                    }

                    try {
                        for (int k=0;k<volume.getIndustryIdentifiers().size(); k++) {
                            isbns = ""+volume.getIndustryIdentifiers().get(k).getIdentifier()+"\n";
                            isbnsTV.append(""+isbns);
                        }
                    }catch (Exception e) {
                        isbnsTV.setText("Not Available");
                    }

                    try {
                        heightTV.setText(volume.getDimension().getHeight());
                        widthTV.setText(volume.getDimension().getWidth());
                        thicknessTV.setText(volume.getDimension().getThickness());
                    }catch (Exception e) {
                        heightTV.setText("-");
                        widthTV.setText("-");
                        thicknessTV.setText("-");
                    }

                    try {
                        Glide.with(BookInfoActivity.this).load(volume.getImageLinks().getThumbnail()).placeholder(R.color.colorShimmer).into(bookImageIV);
                    }catch (Exception e) {
                        Glide.with(BookInfoActivity.this).load(Constant.N0_IMAGE_PLACEHOLDER)
                                .into(bookImageIV);
                    }

                    try {
                        maturityRatingTV.setText(volume.getMaturityRating());
                    }catch (Exception e) {
                        maturityRatingTV.setText("");
                    }

                    if (accessInfo.getPdf().getIsAvailable()) {
                        previewBTN.setText("Free Sample");
                    }else {
                        previewBTN.setVisibility(View.INVISIBLE);
                        previewBTN.setEnabled(false);
                    }
                }

                previewBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getAccessInfo().getWebReaderLink()));
                        startActivity(intent);
                    }
                });

                if (item.getSaleInfo().getSaleability().equals("FOR_SALE")) {


                    if (saleInfo.getIsEbook()) {
                        try {
                            Currency currency = Currency.getInstance(saleInfo.getRetailPrice().getCurrencyCode());
                            buyLinkBTN.setText("Ebook "+currency.getSymbol()+""+saleInfo.getRetailPrice().getAmount());
                        }catch (Exception e) {
                            buyLinkBTN.setText("Ebook "+saleInfo.getRetailPrice().getCurrencyCode()+""+saleInfo.getRetailPrice().getAmount());
                        }
                    }else {
                        try {
                            Currency currency = Currency.getInstance(saleInfo.getRetailPrice().getCurrencyCode());
                            buyLinkBTN.setText("Book "+currency.getSymbol()+" "+saleInfo.getRetailPrice().getAmount());
                        }catch (Exception e) {
                            buyLinkBTN.setText("Book "+saleInfo.getRetailPrice().getCurrencyCode()+" "+saleInfo.getRetailPrice().getAmount());
                        }
                    }

                    buyLinkBTN.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(saleInfo.getBuyLink()));
                            startActivity(intent);
                        }
                    });

                }else {
                    buyLinkBTN.setText("Not For Sale");
                    buyLinkBTN.setBackground(getResources().getDrawable(R.drawable.button_bg_disabled));
                }

                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        descriptionTV.setText(Html.fromHtml(volume.getDescription(), Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        descriptionTV.setText(Html.fromHtml(volume.getDescription()));
                    }
                }catch (Exception e) {
                    descriptionTV.setText("no description available");
                }

            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activeBookmark:

                break;
            case R.id.inactiveBookmark:
                dialog.setMessage("Adding ....");
                dialog.show();
                addToBookmark();
                break;
        }
    }
}
