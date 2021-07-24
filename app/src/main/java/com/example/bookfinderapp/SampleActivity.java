package com.example.bookfinderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookfinderapp.adapters.BookSubjectRecyclerviewAdapter;
import com.example.bookfinderapp.adapterV2.CategoriesRecyclerviewAdapter;
import com.example.bookfinderapp.modelV2.Books;
import com.example.bookfinderapp.modelV2.Item;
import com.example.bookfinderapp.request.RequestService;
import com.example.bookfinderapp.request.RetrofitClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SampleActivity extends AppCompatActivity {
    CategoriesRecyclerviewAdapter categoriesRecyclerviewAdapter;
    BookSubjectRecyclerviewAdapter subjectAdapter;
    LinearLayoutManager layoutManager, layoutManager1;
    RecyclerView recyclerView;
    TextView responseTXT, volumeInfoTXT;
    ProgressDialog progressDoalog;
    List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        itemList = new ArrayList<>();
        progressDoalog = new ProgressDialog(SampleActivity.this);
        progressDoalog.setMessage("Loading ....");
        progressDoalog.show();

        responseTXT = findViewById(R.id.responseTXT);
        volumeInfoTXT = findViewById(R.id.volumeInfoTXT);
        recyclerView = findViewById(R.id.customRecyclerView);
        recyclerView.setHasFixedSize(true);

        RequestService requestService = RetrofitClass.getAPIInstance();
        Call<Books> itemCall = requestService.getVolumeBooks("subject:fiction");

        itemCall.enqueue(new Callback<Books>() {
            @Override
            public void onResponse(Call<Books> call, Response<Books> response) {
                Log.e("@@@@@@@@@@@@@", response.message());
                progressDoalog.dismiss();
                Books books = response.body();
                progressDoalog.dismiss();

                for (int i=0; i<books.getItems().size(); i++) {
                    setUplist(response.body().getItems());
//                    setUpSubjectList(response.body().getItems());
                }

            }

            @Override
            public void onFailure(Call<Books> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(SampleActivity.this, "Something went wrong"+t.getMessage(), Toast.LENGTH_LONG).show();

                Log.e("@@@@@@@@@@@@@", t.getMessage());
            }
        });
    }

    private void setUplist(List<Item> itemList) {
        categoriesRecyclerviewAdapter = new CategoriesRecyclerviewAdapter(SampleActivity.this,itemList);
        layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(categoriesRecyclerviewAdapter);
    }

    private void setUpSubjectList(List<Item> subjectList) {
        subjectAdapter = new BookSubjectRecyclerviewAdapter(SampleActivity.this,subjectList);
        layoutManager1 = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager1);
        recyclerView.setAdapter(subjectAdapter);
    }

}