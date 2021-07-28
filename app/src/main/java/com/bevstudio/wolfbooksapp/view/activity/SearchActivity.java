package com.bevstudio.wolfbooksapp.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bevstudio.wolfbooksapp.R;
import com.bevstudio.wolfbooksapp.adapters.SearchResultsRecyclerviewAdapter;
import com.bevstudio.wolfbooksapp.model.api.Books;
import com.bevstudio.wolfbooksapp.model.api.Item;
import com.bevstudio.wolfbooksapp.request.api.RequestService;
import com.bevstudio.wolfbooksapp.request.api.RetrofitClass;
import com.bevstudio.wolfbooksapp.vendor.EndlessRecyclerViewScrollListener;
import com.bevstudio.wolfbooksapp.vendor.InternetConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, EndlessRecyclerViewScrollListener.Callback {
    EditText searchQueryET;
    ImageView micIV, clearBTN, micActive;
    TextView sortRelevanceTV, sortNewest, headerTV, textTV, placeholderTitleTV, placeholderTextTV;
    RecyclerView searchResultsRV;
    ProgressBar progressBar, progressBar1;
    SearchResultsRecyclerviewAdapter searchAdapter;
    Button errorBTN;
    LinearLayoutManager layoutManager;
    View noConnectionLL;
    String search_keyword="";
    String orderBy="relevance";
    RequestService requestService;
    Call<Books> searchResultsCall;
    EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    int page=1;
    SpeechRecognizer speechRecognizer;
    public static final Integer RecordAudioRequestCode = 1;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setTitle(" ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        searchQueryET = findViewById(R.id.searchQueryET);
        micIV = findViewById(R.id.micIV);
        clearBTN = findViewById(R.id.clearBTN);
        sortRelevanceTV = findViewById(R.id.sortRelevanceTV);
        sortNewest = findViewById(R.id.sortNewest);
        searchResultsRV = findViewById(R.id.searchResultsRV);
        progressBar = findViewById(R.id.progressBar);
        noConnectionLL = findViewById(R.id.noConnectionLL);
        headerTV = findViewById(R.id.headerTV);
        textTV = findViewById(R.id.textTV);
        errorBTN = findViewById(R.id.errorBTN);
        placeholderTitleTV = findViewById(R.id.placeholderTitleTV);
        placeholderTextTV = findViewById(R.id.placeholderTextTV);
        micActive = findViewById(R.id.micActive);
        progressBar1 = findViewById(R.id.progressBar1);

        requestService = RetrofitClass.getNewBooksAPIInstance();

        InternetConnection.isInternetConnected(this,noConnectionLL,searchResultsRV);
        headerTV.setText(R.string.no_internet_header);
        textTV.setText(R.string.no_internet_text);
        errorBTN.setVisibility(View.VISIBLE);
        errorBTN.setText(R.string.try_again);

        micIV.setOnClickListener(this);
        clearBTN.setOnClickListener(this);
        sortRelevanceTV.setOnClickListener(this);
        sortNewest.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            checkPermission();
        }
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(SearchActivity.this);
        final Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please speak");

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
            }

            @Override
            public void onBeginningOfSpeech() {
                searchQueryET.setText("");
                searchQueryET.setHint("Listening ...");
            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {
                micIV.setImageResource(R.drawable.ic_mic);
                searchQueryET.setHint("Search for a book title, author, etc.");
            }

            @Override
            public void onResults(Bundle results) {
                micIV.setImageResource(R.drawable.ic_mic);
                ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                searchQueryET.setText(data.get(0));
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
            }

            @Override
            public void onEvent(int eventType, Bundle params) {
            }
        });

        micIV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    speechRecognizer.stopListening();
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    micIV.setImageResource(R.drawable.ic_mic_dark);
                    speechRecognizer.startListening(speechIntent);
                }
                return false;
            }
        });

        searchQueryET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search_keyword = searchQueryET.getText().toString().trim();
                    if (!searchQueryET.getText().toString().trim().isEmpty()) {
                        loadRelevantItems(page);
                        placeholderTitleTV.setVisibility(View.GONE);
                        placeholderTextTV.setVisibility(View.GONE);
                        searchResultsRV.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                    }else {
                        searchResultsRV.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        placeholderTitleTV.setVisibility(View.VISIBLE);
                        placeholderTextTV.setVisibility(View.VISIBLE);
                    }
                }
                return false;
            }
        });

        searchQueryET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                clearBTN.setVisibility(View.VISIBLE);
                micIV.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                search_keyword = searchQueryET.getText().toString().trim();
                if (s.length() >0 && (!searchQueryET.getText().toString().trim().isEmpty())){
                    loadRelevantItems(page);
                }else if (s.length() ==0 && searchQueryET.getText().toString().trim().isEmpty()){
                    clearBTN.setVisibility(View.GONE);
                    micIV.setVisibility(View.VISIBLE);
                    placeholderTitleTV.setVisibility(View.VISIBLE);
                    placeholderTextTV.setVisibility(View.VISIBLE);
                    searchResultsRV.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                }else {
                    clearBTN.setVisibility(View.GONE);
                    micIV.setVisibility(View.VISIBLE);
                    placeholderTitleTV.setVisibility(View.GONE);
                    placeholderTextTV.setVisibility(View.GONE);
                    searchResultsRV.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

//        nestedScrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
//                    page++;
//                    progressBar1.setVisibility(View.VISIBLE);
//                    callSearchResults("relevance",page);
//                }
//            }
//        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        speechRecognizer.destroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RecordAudioRequestCode && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(SearchActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(SearchActivity.this, "Permission is required to continue using mic", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(SearchActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, RecordAudioRequestCode);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void callSearchResults(String orderBy, int index) {
        String finalQuery = search_keyword.replace(" ","+");
        searchResultsCall = requestService.getSearchResults(finalQuery,index,orderBy,40);

        searchResultsCall.enqueue(new Callback<Books>() {
            @Override
            public void onResponse(Call<Books> call, Response<Books> response) {
                if (response.isSuccessful() || response.body() != null) {
                    placeholderTitleTV.setVisibility(View.GONE);
                    placeholderTextTV.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    searchResultsRV.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    progressBar1.setVisibility(View.GONE);
//                    totalItemsTV.setText(response.body().getTotalItems()+" results");
                    for (int i=0; i<response.body().getItems().size(); i++) {
                        setUpSearchResultslist(response.body().getItems());
                    }
                }

                if (response.code()!=200) {
                    progressBar.setVisibility(View.GONE);
//                    placeholderLL.setVisibility(View.VISIBLE);
//                    placeholderTV.setText("Error : "+response.code());
                }
            }

            @Override
            public void onFailure(Call<Books> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
//                placeholderLL.setVisibility(View.VISIBLE);
//                placeholderTV.setText("Something went wrong.");
                placeholderTitleTV.setVisibility(View.GONE);
                placeholderTextTV.setText(R.string.something_went_wrong);
                placeholderTextTV.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setUpSearchResultslist(List<Item> itemList) {
        searchAdapter = new SearchResultsRecyclerviewAdapter(SearchActivity.this,itemList);
        layoutManager = new LinearLayoutManager(SearchActivity.this,LinearLayoutManager.VERTICAL,false);
        endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(layoutManager, this);
        searchResultsRV.setLayoutManager(layoutManager);
        searchResultsRV.setAdapter(searchAdapter);
    }

    void loadRelevantItems(int page) {
        sortRelevanceTV.setAlpha((float) 0.9);
        sortNewest.setAlpha((float) 0.5);
        searchResultsRV.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        callSearchResults("relevance",page);
    }

    void loadNewestItems(int page) {
        sortRelevanceTV.setAlpha((float) 0.5);
        sortNewest.setAlpha((float) 0.9);
        searchResultsRV.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        callSearchResults("newest",page);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.micIV:
                break;
            case R.id.clearBTN:
                search_keyword="";
                searchQueryET.setText("");
                progressBar.setVisibility(View.GONE);
                break;
            case R.id.sortRelevanceTV:
                loadRelevantItems(page);
                orderBy="relevance";
                break;
            case R.id.sortNewest:
                loadNewestItems(page);
                orderBy="newest";
                break;
        }
    }

    @Override
    public void onLoadMore(int page, int totalItemsCount) {
        page++;
        callSearchResults("relevance", page);
        Toast.makeText(SearchActivity.this, "hello", Toast.LENGTH_SHORT).show();
    }
}