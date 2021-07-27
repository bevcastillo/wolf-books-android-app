package com.example.bookfinderapp.view.fragments;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bookfinderapp.R;
import com.example.bookfinderapp.adapters.CategoriesRecyclerviewAdapter;
import com.example.bookfinderapp.vendor.InternetConnection;
import com.example.bookfinderapp.model.api.Books;
import com.example.bookfinderapp.model.api.Item;
import com.example.bookfinderapp.request.api.RequestService;
import com.example.bookfinderapp.request.api.RetrofitClass;
import com.example.bookfinderapp.view.activity.CategoriesListActivity;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragmentV2 extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    SwipeRefreshLayout homeSRL;
    ShimmerFrameLayout romanceShimmer, thrillerShimmer,
            fictionShimmer, childrenShimmer, selfHelpShimmer;
    TextView romanceErr, thrillerErr, fictionErr, headerTV, textTV;
    Button errorBTN, artsBTN, bioBTN, businessBTN, childrensBTN, computers, educBTN, religionBTN, romanceBTN, travelBTN,
            comicsBTN, cookingBTN, fictionBTN, foreignBTN, healthBTN, historyBTN, parentingBTN, scienceBTN, fantasy;
    RecyclerView fictionRV, romanceRV, thrillerRV, childrensRV, topAuthorRV, selfHelpRV;
    CategoriesRecyclerviewAdapter categoriesAdapter;
    LinearLayoutManager fictionLM, romanceLM, thrillerLM, authorsLM, selfhelpLM;
    View noConnectionLL, layout_parent;
    RequestService requestService;
    Call<Books> fictionCall, motivationCall, thrillerCall, selfHelpCall;

    ArrayList<Integer> authImage = new ArrayList<Integer>();
    ArrayList<String> authName = new ArrayList<>();

    public HomeFragmentV2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_v2, container, false);

        getActivity().setTitle("Welcome to Fox Books");

        homeSRL = view.findViewById(R.id.homeSRL);
        fictionRV = view.findViewById(R.id.fictionRV);
        romanceRV = view.findViewById(R.id.romanceRV);
        thrillerRV = view.findViewById(R.id.thrillerRV);
        childrensRV = view.findViewById(R.id.childrensRV);
        romanceShimmer = view.findViewById(R.id.romanceShimmer);
        thrillerShimmer = view.findViewById(R.id.thrillerShimmer);
        fictionShimmer = view.findViewById(R.id.fictionShimmer);
        childrenShimmer = view.findViewById(R.id.childrenShimmer);
        noConnectionLL = view.findViewById(R.id.noConnectionLL);
        layout_parent = view.findViewById(R.id.layout_parent);
        headerTV = view.findViewById(R.id.headerTV);
        textTV = view.findViewById(R.id.textTV);
        errorBTN = view.findViewById(R.id.errorBTN);
        selfHelpShimmer = view.findViewById(R.id.selfHelpShimmer);
        selfHelpRV = view.findViewById(R.id.selfHelpRV);
        artsBTN = view.findViewById(R.id.artsBTN);
        bioBTN = view.findViewById(R.id.bioBTN);
        businessBTN = view.findViewById(R.id.businessBTN);
        childrensBTN = view.findViewById(R.id.childrensBTN);
        computers = view.findViewById(R.id.computers);
        educBTN = view.findViewById(R.id.educBTN);
        religionBTN = view.findViewById(R.id.religionBTN);
        romanceBTN = view.findViewById(R.id.romanceBTN);
        travelBTN = view.findViewById(R.id.travelBTN);
        comicsBTN = view.findViewById(R.id.comicsBTN);
        cookingBTN = view.findViewById(R.id.cookingBTN);
        fictionBTN = view.findViewById(R.id.fictionBTN);
        foreignBTN = view.findViewById(R.id.foreignBTN);
        healthBTN = view.findViewById(R.id.healthBTN);
        historyBTN = view.findViewById(R.id.historyBTN);
        parentingBTN = view.findViewById(R.id.parentingBTN);
        scienceBTN = view.findViewById(R.id.scienceBTN);
        fantasy = view.findViewById(R.id.fantasy);

        homeSRL.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        homeSRL.setOnRefreshListener(this);
        requestService = RetrofitClass.getAPIInstance();

        InternetConnection.isInternetConnected(getContext(), noConnectionLL, layout_parent);
        headerTV.setText(R.string.no_internet_header);
        textTV.setText(R.string.no_internet_text);
        errorBTN.setVisibility(View.VISIBLE);
        errorBTN.setText(R.string.try_again);

        errorBTN.setOnClickListener(this);
        artsBTN.setOnClickListener(this);
        bioBTN.setOnClickListener(this);
        businessBTN.setOnClickListener(this);
        childrensBTN.setOnClickListener(this);
        computers.setOnClickListener(this);
        educBTN.setOnClickListener(this);
        religionBTN.setOnClickListener(this);
        romanceBTN.setOnClickListener(this);
        travelBTN.setOnClickListener(this);
        comicsBTN.setOnClickListener(this);
        cookingBTN.setOnClickListener(this);
        fictionBTN.setOnClickListener(this);
        foreignBTN.setOnClickListener(this);
        healthBTN.setOnClickListener(this);
        historyBTN.setOnClickListener(this);
        parentingBTN.setOnClickListener(this);
        scienceBTN.setOnClickListener(this);
        fantasy.setOnClickListener(this);

        callFiction();
        callRomance();
        callThriller();
        callSelfHelp();

        return view;
    }

    private void setupFictionList(List<Item> itemList) {
        categoriesAdapter = new CategoriesRecyclerviewAdapter(getContext(), itemList);
        fictionLM = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        fictionRV.setLayoutManager(fictionLM);
        fictionRV.setAdapter(categoriesAdapter);
    }

    private void setupRomanceList(List<Item> itemList) {
        categoriesAdapter = new CategoriesRecyclerviewAdapter(getContext(), itemList);
        romanceLM = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        romanceRV.setLayoutManager(romanceLM);
        romanceRV.setAdapter(categoriesAdapter);
    }

    private void setUpThrillerList(List<Item> itemList) {
        categoriesAdapter = new CategoriesRecyclerviewAdapter(getContext(), itemList);
        thrillerLM = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        thrillerRV.setLayoutManager(thrillerLM);
        thrillerRV.setAdapter(categoriesAdapter);
    }

    private void setUpSelfHelp(List<Item> itemList) {
        categoriesAdapter = new CategoriesRecyclerviewAdapter(getContext(), itemList);
        selfhelpLM = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        selfHelpRV.setLayoutManager(selfhelpLM);
        selfHelpRV.setAdapter(categoriesAdapter);
    }

    private void callSelfHelp() {
        selfHelpCall = requestService.getCategories("categories:self-help", "ebooks", "relevance", 40);
        selfHelpCall.enqueue(new Callback<Books>() {
            @Override
            public void onResponse(Call<Books> call, Response<Books> response) {
                selfHelpRV.setVisibility(View.VISIBLE);
                selfHelpShimmer.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().getItems().size(); i++) {
                        setUpSelfHelp(response.body().getItems());
                    }
                }
            }

            @Override
            public void onFailure(Call<Books> call, Throwable t) {
                selfHelpRV.setVisibility(View.GONE);
                selfHelpShimmer.setVisibility(View.GONE);
            }
        });
    }

    private void callFiction() {
        fictionCall = requestService.getCategories("categories:young+fiction", "ebooks", "relevance", 40);
        fictionCall.enqueue(new Callback<Books>() {
            @Override
            public void onResponse(Call<Books> call, Response<Books> response) {
                fictionRV.setVisibility(View.VISIBLE);
                fictionShimmer.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().getItems().size(); i++) {
                        setupFictionList(response.body().getItems());
                    }
                }
            }

            @Override
            public void onFailure(Call<Books> call, Throwable t) {
                fictionRV.setVisibility(View.GONE);
                fictionShimmer.setVisibility(View.GONE);
                fictionErr.setVisibility(View.VISIBLE);
                fictionErr.setText(t.getMessage());
            }
        });
    }

    private void callRomance() {
        motivationCall = requestService.getCategories("categories:romance", "ebooks", "relevance", 40);
        motivationCall.enqueue(new Callback<Books>() {
            @Override
            public void onResponse(Call<Books> call, Response<Books> response) {
                romanceRV.setVisibility(View.VISIBLE);
                romanceShimmer.setVisibility(View.GONE);
                homeSRL.setRefreshing(false);
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().getItems().size(); i++) {
                        setupRomanceList(response.body().getItems());
                    }
                }
            }

            @Override
            public void onFailure(Call<Books> call, Throwable t) {
                romanceRV.setVisibility(View.GONE);
                romanceShimmer.setVisibility(View.GONE);
                romanceErr.setVisibility(View.VISIBLE);
                romanceErr.setText(t.getMessage());
            }
        });
    }

    private void callThriller() {
        thrillerCall = requestService.getCategories("categories:thriller+horror+suspense", "ebooks", "relevance", 40);
        thrillerCall.enqueue(new Callback<Books>() {
            @Override
            public void onResponse(Call<Books> call, Response<Books> response) {
                thrillerRV.setVisibility(View.VISIBLE);
                thrillerShimmer.setVisibility(View.GONE);
                homeSRL.setRefreshing(false);

                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().getItems().size(); i++) {
                        setUpThrillerList(response.body().getItems());
                    }
                }
            }

            @Override
            public void onFailure(Call<Books> call, Throwable t) {
                thrillerRV.setVisibility(View.GONE);
                thrillerShimmer.setVisibility(View.GONE);
                thrillerErr.setVisibility(View.VISIBLE);
                thrillerErr.setText(t.getMessage());
            }
        });

    }

    @Override
    public void onRefresh() {
        hideRecyclerviews();
        showShimmerLayout();
        callFiction();
        callRomance();
        callThriller();
        callSelfHelp();
    }

    public void hideRecyclerviews() {
        fictionRV.setVisibility(View.GONE);
        romanceRV.setVisibility(View.GONE);
        thrillerRV.setVisibility(View.GONE);
        selfHelpRV.setVisibility(View.GONE);
    }

    public void showShimmerLayout() {
        romanceShimmer.setVisibility(View.VISIBLE);
        thrillerShimmer.setVisibility(View.VISIBLE);
        fictionShimmer.setVisibility(View.VISIBLE);
        selfHelpShimmer.setVisibility(View.VISIBLE);
    }

    void intentToViewCategory(Context context, String category_query, String category_name) {
        Intent intent = new Intent(context, CategoriesListActivity.class);
        intent.putExtra("category_query", category_query);
        intent.putExtra("category_name", category_name);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.errorBTN:
                InternetConnection.isInternetConnected(v.getContext(), noConnectionLL, layout_parent);
                onRefresh();
                break;
            case R.id.artsBTN:
                intentToViewCategory(v.getContext(),"categories:arts+arts,entertainment", "Arts & entertainment");
                break;
            case R.id.bioBTN:
                intentToViewCategory(v.getContext(), "categories:biography", "Biography & memoirs");
                break;
            case R.id.businessBTN:
                intentToViewCategory(v.getContext(), "categories:business, investing+business+investing", "Business & investing");
                break;
            case R.id.childrensBTN:
                intentToViewCategory(v.getContext(), "categories:childrens", "Children's");
                break;
            case R.id.computers:
                intentToViewCategory(v.getContext(), "categories:computers+technology, computers, technology", "Computers & technology");
                break;
            case R.id.educBTN:
                intentToViewCategory(v.getContext(), "categories:education", "Education");
                break;
            case R.id.religionBTN:
                intentToViewCategory(v.getContext(), "categories:religion", "Religion & spirituality");
                break;
            case R.id.romanceBTN:
                intentToViewCategory(v.getContext(), "categories:romance", "Romance");
                break;
            case R.id.travelBTN:
                intentToViewCategory(v.getContext(), "categories:travel", "Travel");
                break;
            case R.id.comicsBTN:
                intentToViewCategory(v.getContext(), "categories:comics+graphicnovel+manga", "Comics");
                break;
            case R.id.cookingBTN:
                intentToViewCategory(v.getContext(), "categories:cooking", "Cooking, food & wine");
                break;
            case R.id.fictionBTN:
                intentToViewCategory(v.getContext(), "categories:fiction", "Fiction & literary collections");
                break;
            case R.id.foreignBTN:
                intentToViewCategory(v.getContext(), "categories:language", "Foreign language & study aids");
                break;
            case R.id.healthBTN:
                intentToViewCategory(v.getContext(), "categories:health", "Health, mind & body");
                break;
            case R.id.historyBTN:
                intentToViewCategory(v.getContext(), "categories:history", "History");
                break;
            case R.id.parentingBTN:
                intentToViewCategory(v.getContext(), "categories:parenting", "Parenting & families");
                break;
            case R.id.scienceBTN:
                intentToViewCategory(v.getContext(), "categories:science,math", "Science & Math");
                break;
            case R.id.fantasy:
                intentToViewCategory(v.getContext(), "categories:science fiction+fantasy", "Sci-fi & fantasy");
                break;

        }
    }
}