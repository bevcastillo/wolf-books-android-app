package com.example.bookfinderapp.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bookfinderapp.R;
import com.example.bookfinderapp.adapters.CategoriesRecyclerviewAdapter;
import com.example.bookfinderapp.helper.InternetConnection;
import com.example.bookfinderapp.model.api.Books;
import com.example.bookfinderapp.model.api.Item;
import com.example.bookfinderapp.request.RequestService;
import com.example.bookfinderapp.request.RetrofitClass;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragmentV2 extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    SwipeRefreshLayout homeSRL;
    ShimmerFrameLayout fantasyShimmer, adventureShimmer, romanceShimmer, horrorShimmer, thrillerShimmer, fictionShimmer, healthShimmer, historyShimmer, childrenShimmer;
    TextView fantasyErr, adventureErr, romanceErr, horrorErr, thrillerErr, fictionErr, healthErr, historyErr, childrenErr, retryTV;
    RecyclerView fictionRV, fantasyRV, romanceRV, adventureRV, horrorRV, thrillerRV, healthRV, historyRV, childrensRV;
    CategoriesRecyclerviewAdapter categoriesAdapter;
    LinearLayoutManager fictionLM, fantasyLM, romanceLM, adventureLM, horrorLM, thrillerLM, healthLM, historyLM, childrenLM;
    LinearLayout noConnectionLL, layout_parent;
    RequestService requestService;
    Call<Books> fictionCall, fantasyCall, motivationCall, adventureCall, horrorCall, thrillerCall, healthCall, historyCall, childrenCall;

    public HomeFragmentV2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_v2, container, false);

        getActivity().setTitle("Home");

        homeSRL = view.findViewById(R.id.homeSRL);
        fictionRV = view.findViewById(R.id.fictionRV);
        fantasyRV = view.findViewById(R.id.fantasyRV);
        romanceRV = view.findViewById(R.id.romanceRV);
        adventureRV = view.findViewById(R.id.adventureRV);
        horrorRV = view.findViewById(R.id.horrorRV);
        thrillerRV = view.findViewById(R.id.thrillerRV);
        healthRV = view.findViewById(R.id.healthRV);
        historyRV = view.findViewById(R.id.historyRV);
        childrensRV = view.findViewById(R.id.childrensRV);
        fantasyShimmer = view.findViewById(R.id.fantasyShimmer);
        adventureShimmer = view.findViewById(R.id.adventureShimmer);
        romanceShimmer = view.findViewById(R.id.romanceShimmer);
        horrorShimmer = view.findViewById(R.id.horrorShimmer);
        thrillerShimmer = view.findViewById(R.id.thrillerShimmer);
        fictionShimmer = view.findViewById(R.id.fictionShimmer);
        healthShimmer = view.findViewById(R.id.healthShimmer);
        historyShimmer = view.findViewById(R.id.historyShimmer);
        childrenShimmer = view.findViewById(R.id.childrenShimmer);
        fantasyErr = view.findViewById(R.id.fantasyErr);
        adventureErr = view.findViewById(R.id.adventureErr);
        romanceErr = view.findViewById(R.id.romanceErr);
        horrorErr = view.findViewById(R.id.horrorErr);
        thrillerErr = view.findViewById(R.id.thrillerErr);
        fictionErr = view.findViewById(R.id.fictionErr);
        healthErr = view.findViewById(R.id.healthErr);
        historyErr = view.findViewById(R.id.historyErr);
        childrenErr = view.findViewById(R.id.childrenErr);
        noConnectionLL = view.findViewById(R.id.noConnectionLL);
        retryTV = view.findViewById(R.id.retryTV);
        layout_parent = view.findViewById(R.id.layout_parent);

        homeSRL.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        homeSRL.setOnRefreshListener(this);
        requestService = RetrofitClass.getAPIInstance();

        InternetConnection.isInternetConnected(getContext(),noConnectionLL,layout_parent);
        retryTV.setOnClickListener(this);

        callFiction();
        callFantasy();
        callRomance();
        callAdventure();
        callHorror();
        callThriller();
        callHealth();
        callHistory();
        callChildren();

        return view;
    }

    private void setupFictionList(List<Item> itemList) {
        categoriesAdapter = new CategoriesRecyclerviewAdapter(getContext(),itemList);
        fictionLM = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        fictionRV.setLayoutManager(fictionLM);
        fictionRV.setAdapter(categoriesAdapter);
    }

    private void setupFantasyList(List<Item> itemList) {
        categoriesAdapter = new CategoriesRecyclerviewAdapter(getContext(),itemList);
        fantasyLM = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        fantasyRV.setLayoutManager(fantasyLM);
        fantasyRV.setAdapter(categoriesAdapter);
    }

    private void setupRomanceList(List<Item> itemList) {
        categoriesAdapter = new CategoriesRecyclerviewAdapter(getContext(),itemList);
        romanceLM = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        romanceRV.setLayoutManager(romanceLM);
        romanceRV.setAdapter(categoriesAdapter);
    }

    private void setupAdventureList(List<Item> itemList) {
        categoriesAdapter = new CategoriesRecyclerviewAdapter(getContext(),itemList);
        adventureLM = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        adventureRV.setLayoutManager(adventureLM);
        adventureRV.setAdapter(categoriesAdapter);
    }

    private void setupHorrorList(List<Item> itemList) {
        categoriesAdapter = new CategoriesRecyclerviewAdapter(getContext(),itemList);
        horrorLM = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        horrorRV.setLayoutManager(horrorLM);
        horrorRV.setAdapter(categoriesAdapter);
    }

    private void setUpThrillerList(List<Item> itemList) {
        categoriesAdapter = new CategoriesRecyclerviewAdapter(getContext(),itemList);
        thrillerLM = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        thrillerRV.setLayoutManager(thrillerLM);
        thrillerRV.setAdapter(categoriesAdapter);
    }

    private void setUpHealthList(List<Item> itemList) {
        categoriesAdapter = new CategoriesRecyclerviewAdapter(getContext(),itemList);
        healthLM = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        healthRV.setLayoutManager(healthLM);
        healthRV.setAdapter(categoriesAdapter);
    }

    private void setupHistoryList(List<Item> itemList) {
        categoriesAdapter = new CategoriesRecyclerviewAdapter(getContext(),itemList);
        historyLM = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        historyRV.setLayoutManager(historyLM);
        historyRV.setAdapter(categoriesAdapter);
    }

    private void setupChildrenList(List<Item> itemList) {
        categoriesAdapter = new CategoriesRecyclerviewAdapter(getContext(),itemList);
        childrenLM = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        childrensRV.setLayoutManager(childrenLM);
        childrensRV.setAdapter(categoriesAdapter);
    }

    private void callFiction() {
        fictionCall = requestService.getVolumeBooks("subject:fiction");
        fictionCall.enqueue(new Callback<Books>() {
            @Override
            public void onResponse(Call<Books> call, Response<Books> response) {
                fictionRV.setVisibility(View.VISIBLE);
                fictionShimmer.setVisibility(View.GONE);
                //todo check response code
                for (int i=0; i<response.body().getItems().size(); i++) {
                    setupFictionList(response.body().getItems());
                }
            }

            @Override
            public void onFailure(Call<Books> call, Throwable t) {
                fictionRV.setVisibility(View.GONE);
                fictionShimmer.setVisibility(View.GONE);
                fictionErr.setVisibility(View.VISIBLE);
                fictionErr.setText(t.getMessage());
                Log.e("FICTION CATEGORY @@ : ",t.getMessage());
            }
        });
    }

    private void callFantasy() {
        fantasyCall = requestService.getVolumeBooks("subject:fantasy");
        fantasyCall.enqueue(new Callback<Books>() {
            @Override
            public void onResponse(Call<Books> call, Response<Books> response) {
                fantasyRV.setVisibility(View.VISIBLE);
                fantasyShimmer.setVisibility(View.GONE);
                homeSRL.setRefreshing(false);
                if (response.isSuccessful()) {
                    for (int i=0; i<response.body().getItems().size(); i++) {
                        setupFantasyList(response.body().getItems());
                    }
                }
            }

            @Override
            public void onFailure(Call<Books> call, Throwable t) {
                fantasyRV.setVisibility(View.GONE);
                fantasyShimmer.setVisibility(View.VISIBLE);
                Log.e("FANTASY CATEGORY @@ : ",t.getMessage());
            }
        });
    }

    private void callRomance() {
        motivationCall = requestService.getVolumeBooks("subject:romance");
        motivationCall.enqueue(new Callback<Books>() {
            @Override
            public void onResponse(Call<Books> call, Response<Books> response) {
                romanceRV.setVisibility(View.VISIBLE);
                romanceShimmer.setVisibility(View.GONE);
                homeSRL.setRefreshing(false);
                for (int i=0; i<response.body().getItems().size(); i++) {
                    setupRomanceList(response.body().getItems());
                }
            }

            @Override
            public void onFailure(Call<Books> call, Throwable t) {
                romanceRV.setVisibility(View.GONE);
                romanceShimmer.setVisibility(View.GONE);
                romanceErr.setVisibility(View.VISIBLE);
                romanceErr.setText(t.getMessage());
                Log.e("ROMANCE CATEGORY @@ : ",t.getMessage());
            }
        });
    }

    private void callAdventure() {
        adventureCall = requestService.getVolumeBooks("subject:adventure");
        adventureCall.enqueue(new Callback<Books>() {
            @Override
            public void onResponse(Call<Books> call, Response<Books> response) {
                adventureRV.setVisibility(View.VISIBLE);
                adventureShimmer.setVisibility(View.GONE);
                homeSRL.setRefreshing(false);
                for (int i=0; i<response.body().getItems().size(); i++) {
                    setupAdventureList(response.body().getItems());
                }
            }

            @Override
            public void onFailure(Call<Books> call, Throwable t) {
                adventureRV.setVisibility(View.GONE);
                adventureShimmer.setVisibility(View.GONE);
                adventureErr.setVisibility(View.VISIBLE);
                adventureErr.setText(t.getMessage());
                Log.e("ADVENTURE CATEGORY @@ :", t.getMessage());
            }
        });
    }

    private void callHorror() {
        horrorCall = requestService.getVolumeBooks("subject:horror");
        horrorCall.enqueue(new Callback<Books>() {
            @Override
            public void onResponse(Call<Books> call, Response<Books> response) {
                horrorRV.setVisibility(View.VISIBLE);
                horrorShimmer.setVisibility(View.GONE);
                homeSRL.setRefreshing(false);
                for (int i=0; i<response.body().getItems().size(); i++) {
                    setupHorrorList(response.body().getItems());
                }
            }

            @Override
            public void onFailure(Call<Books> call, Throwable t) {
                horrorRV.setVisibility(View.GONE);
                horrorShimmer.setVisibility(View.GONE);
                horrorErr.setVisibility(View.VISIBLE);
                horrorErr.setText(t.getMessage());
                Log.e("HORROR CATEGORY @@ : ",t.getMessage());
            }
        });
    }

    private void callThriller() {
        thrillerCall = requestService.getVolumeBooks("subject:thriller");
        thrillerCall.enqueue(new Callback<Books>() {
            @Override
            public void onResponse(Call<Books> call, Response<Books> response) {
                thrillerRV.setVisibility(View.VISIBLE);
                thrillerShimmer.setVisibility(View.GONE);
                homeSRL.setRefreshing(false);
                if (response.code()==200) {
                    for (int i=0; i<response.body().getItems().size(); i++) {
                        setUpThrillerList(response.body().getItems());
                    }
                }else {

                }
            }

            @Override
            public void onFailure(Call<Books> call, Throwable t) {
                thrillerRV.setVisibility(View.GONE);
                thrillerShimmer.setVisibility(View.GONE);
                thrillerErr.setVisibility(View.VISIBLE);
                thrillerErr.setText(t.getMessage());
                Log.e("THRILLER CATEGORY @@ : ",t.getMessage());
            }
        });
    }

    private void callHealth() {
        healthCall = requestService.getVolumeBooks("subject:health");
        healthCall.enqueue(new Callback<Books>() {
            @Override
            public void onResponse(Call<Books> call, Response<Books> response) {
                healthRV.setVisibility(View.VISIBLE);
                healthShimmer.setVisibility(View.GONE);
                homeSRL.setRefreshing(false);
                for (int i=0; i<response.body().getItems().size(); i++) {
                    setUpHealthList(response.body().getItems());
                }
            }

            @Override
            public void onFailure(Call<Books> call, Throwable t) {
                healthRV.setVisibility(View.GONE);
                healthShimmer.setVisibility(View.GONE);
                healthErr.setVisibility(View.VISIBLE);
                healthErr.setText(t.getMessage());
                Log.e("HEALTH CATEGORY @@ : ",t.getMessage());
            }
        });
    }

    private void callHistory() {
        historyCall = requestService.getVolumeBooks("subject:history");
        historyCall.enqueue(new Callback<Books>() {
            @Override
            public void onResponse(Call<Books> call, Response<Books> response) {
                historyShimmer.setVisibility(View.GONE);
                historyRV.setVisibility(View.VISIBLE);
                homeSRL.setRefreshing(false);
                for (int i=0; i<response.body().getItems().size(); i++) {
                    setupHistoryList(response.body().getItems());
                }
            }

            @Override
            public void onFailure(Call<Books> call, Throwable t) {
                historyShimmer.setVisibility(View.GONE);
                historyRV.setVisibility(View.GONE);
                historyErr.setVisibility(View.VISIBLE);
                historyErr.setText(t.getMessage());
                Log.e("HISTORY CATEGORY @@ : ",t.getMessage());
            }
        });
    }

    private void callChildren() {
        childrenCall = requestService.getVolumeBooks("subject:children");
        childrenCall.enqueue(new Callback<Books>() {
            @Override
            public void onResponse(Call<Books> call, Response<Books> response) {
                childrensRV.setVisibility(View.VISIBLE);
                childrenShimmer.setVisibility(View.GONE);
                homeSRL.setRefreshing(false);
                for (int i=0; i<response.body().getItems().size(); i++) {
                    setupChildrenList(response.body().getItems());
                }
            }

            @Override
            public void onFailure(Call<Books> call, Throwable t) {
                childrenShimmer.setVisibility(View.GONE);
                childrensRV.setVisibility(View.GONE);
                childrenErr.setVisibility(View.VISIBLE);
                childrenErr.setText(t.getMessage());
                Log.e("CHILDREN CATEGORY @@ : ",t.getMessage());
            }
        });
    }

    @Override
    public void onRefresh() {
        hideRecyclerviews();
        showShimmerLayout();
        callFiction();
        callFantasy();
        callRomance();
        callAdventure();
        callHorror();
        callThriller();
        callHealth();
        callHistory();
        callChildren();
    }

    public void hideRecyclerviews() {
        fictionRV.setVisibility(View.GONE);
        fantasyRV.setVisibility(View.GONE);
        romanceRV.setVisibility(View.GONE);
        adventureRV.setVisibility(View.GONE);
        horrorRV.setVisibility(View.GONE);
        thrillerRV.setVisibility(View.GONE);
        healthRV.setVisibility(View.GONE);
        historyRV.setVisibility(View.GONE);
        childrensRV.setVisibility(View.GONE);

        fantasyErr.setVisibility(View.GONE);
        adventureErr.setVisibility(View.GONE);
        romanceErr.setVisibility(View.GONE);
        horrorErr.setVisibility(View.GONE);
        thrillerErr.setVisibility(View.GONE);
        fictionErr.setVisibility(View.GONE);
        healthErr.setVisibility(View.GONE);
        historyErr.setVisibility(View.GONE);
        childrenErr.setVisibility(View.GONE);
    }

    public void showShimmerLayout() {
        fantasyShimmer.setVisibility(View.VISIBLE);
        adventureShimmer.setVisibility(View.VISIBLE);
        romanceShimmer.setVisibility(View.VISIBLE);
        horrorShimmer.setVisibility(View.VISIBLE);
        thrillerShimmer.setVisibility(View.VISIBLE);
        fictionShimmer.setVisibility(View.VISIBLE);
        healthShimmer.setVisibility(View.VISIBLE);
        historyShimmer.setVisibility(View.VISIBLE);
        childrenShimmer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.retryTV:
                InternetConnection.isInternetConnected(v.getContext(), noConnectionLL, layout_parent);
                break;
        }
    }
}