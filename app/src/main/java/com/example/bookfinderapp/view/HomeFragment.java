package com.example.bookfinderapp.view;


import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookfinderapp.adapters.AdventureBooksAdapter;
import com.example.bookfinderapp.adapters.ChildrensBookAdapter;
import com.example.bookfinderapp.adapters.ContemporaryBooksAdapter;
import com.example.bookfinderapp.adapters.HealthBooksAdapter;
import com.example.bookfinderapp.adapters.HistoryBooksAdapter;
import com.example.bookfinderapp.adapters.HorrorBooksAdapter;
import com.example.bookfinderapp.adapters.MysteryBooksAdapter;
import com.example.bookfinderapp.adapters.RomanceBookAdapter;
import com.example.bookfinderapp.adapters.ScienceFictionBooksAdapter;
import com.example.bookfinderapp.adapters.ThrillerBooksAdapter;
import com.example.bookfinderapp.helper.Constant;
import com.example.bookfinderapp.R;
import com.example.bookfinderapp.adapters.FantasyBooksAdapter;
import com.example.bookfinderapp.adapters.FictionBooksAdapter;
import com.example.bookfinderapp.adapters.MotivationalBooksAdapter;
import com.example.bookfinderapp.models.VolumeBooks;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    RecyclerView rvFiction, rvFantasy, rvMotivational, rvAdventure, rvRomance, rvContemporary,
                rvMystery, rvHorror, rvThriller, rvScience, rvHealth, rvHistory, rvChildren;
    private String strFiction = "fiction";
    private String strFantasy = "fantasy";
    private String strMotivational = "motivational";
    private String strAdventure = "adventure";
    private String strRomance = "romance";
    private String strContemp = "contemporary";
    private String strMystery = "mystery";
    private String strHorror = "horror";
    private String strThriller = "thriller";
    private String strScience = "science";
    private String strHealth = "health";
    private String strHistory = "history";
    private String strChildren = "children";

    private RequestQueue fictionRequest, fantasyRequest, motivationalRequest, adventureRequest,
                        romanceRequest, contempRequest, mysteryRequest, horrorRequest, thrillerRequest,
                        scienceRequest, healthRequest, historyRequest, childrenRequest;

    private ArrayList<VolumeBooks> volumeBooks;
    private ArrayList<VolumeBooks> volumeBooks1;
    private ArrayList<VolumeBooks> volumeBooks2;
    private ArrayList<VolumeBooks> volumeBooks3;
    private ArrayList<VolumeBooks> volumeBooks4;
    private ArrayList<VolumeBooks> volumeBooks5;
    private ArrayList<VolumeBooks> volumeBooks6;
    private ArrayList<VolumeBooks> volumeBooks7;
    private ArrayList<VolumeBooks> volumeBooks8;
    private ArrayList<VolumeBooks> volumeBooks9;
    private ArrayList<VolumeBooks> volumeBooks10;
    private ArrayList<VolumeBooks> volumeBooks11;
    private ArrayList<VolumeBooks> volumeBooks12;

    private FantasyBooksAdapter fantasyAdapter;
    private FictionBooksAdapter adapter;
    private MotivationalBooksAdapter motivationalAdapter;
    private AdventureBooksAdapter adventureBooksAdapter;
    private RomanceBookAdapter romanceBookAdapter;
    private ContemporaryBooksAdapter contemporaryBooksAdapter;
    private MysteryBooksAdapter mysteryBooksAdapter;
    private HorrorBooksAdapter horrorBooksAdapter;
    private ThrillerBooksAdapter thrillerBooksAdapter;
    private ScienceFictionBooksAdapter scienceFictionBooksAdapter;
    private HealthBooksAdapter healthBooksAdapter;
    private HistoryBooksAdapter historyBooksAdapter;
    private ChildrensBookAdapter childrensBookAdapter;

    ShimmerFrameLayout shimmerFrameLayout;

    LinearLayout parentLayout;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        getActivity().setTitle("Home");

        rvFiction = view.findViewById(R.id.rv_trending_books);
        rvFantasy = view.findViewById(R.id.rv_fantasy_books);
        rvMotivational = view.findViewById(R.id.rv_motivational_books);
        rvAdventure = view.findViewById(R.id.rv_adventure_books);
        rvRomance = view.findViewById(R.id.rv_romance_books);
        rvContemporary = view.findViewById(R.id.rv_contemporary_books);
        rvMystery = view.findViewById(R.id.rv_mystery_books);
        rvHorror = view.findViewById(R.id.rv_horror_books);
        rvThriller = view.findViewById(R.id.rv_thriller_books);
        rvScience = view.findViewById(R.id.rv_science_books);
        rvHealth = view.findViewById(R.id.rv_health_books);
        rvHistory = view.findViewById(R.id.rv_history_books);
        rvChildren = view.findViewById(R.id.rv_childrens_books);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);

        parentLayout = view.findViewById(R.id.layout_parent);

        //
        rvFiction.setHasFixedSize(true);
        rvFiction.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        volumeBooks = new ArrayList<>();
        fictionRequest = Volley.newRequestQueue(getActivity());

        //
        rvFantasy.setHasFixedSize(true);
        rvFantasy.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        volumeBooks1 = new ArrayList<>();
        fantasyRequest = Volley.newRequestQueue(getActivity());

        //
        rvMotivational.setHasFixedSize(true);
        rvMotivational.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        volumeBooks2 = new ArrayList<>();
        motivationalRequest = Volley.newRequestQueue(getActivity());

        rvAdventure.setHasFixedSize(true);
        rvAdventure.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        volumeBooks3 = new ArrayList<>();
        adventureRequest = Volley.newRequestQueue(getActivity());

        rvRomance.setHasFixedSize(true);
        rvRomance.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        volumeBooks4 = new ArrayList<>();
        romanceRequest = Volley.newRequestQueue(getActivity());

        rvContemporary.setHasFixedSize(true);
        rvContemporary.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        volumeBooks5 = new ArrayList<>();
        contempRequest = Volley.newRequestQueue(getActivity());

        rvMystery.setHasFixedSize(true);
        rvMystery.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        volumeBooks6 = new ArrayList<>();
        mysteryRequest = Volley.newRequestQueue(getActivity());

        rvHorror.setHasFixedSize(true);
        rvHorror.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        volumeBooks7 = new ArrayList<>();
        horrorRequest = Volley.newRequestQueue(getActivity());

        rvThriller.setHasFixedSize(true);
        rvThriller.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        volumeBooks8 = new ArrayList<>();
        thrillerRequest = Volley.newRequestQueue(getActivity());

        rvScience.setHasFixedSize(true);
        rvScience.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        volumeBooks9 = new ArrayList<>();
        scienceRequest = Volley.newRequestQueue(getActivity());

        rvHealth.setHasFixedSize(true);
        rvHealth.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        volumeBooks10 = new ArrayList<>();
        healthRequest = Volley.newRequestQueue(getActivity());

        rvHistory.setHasFixedSize(true);
        rvHistory.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        volumeBooks11 = new ArrayList<>();
        historyRequest = Volley.newRequestQueue(getActivity());

        rvChildren.setHasFixedSize(true);
        rvChildren.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        volumeBooks12 = new ArrayList<>();
        childrenRequest = Volley.newRequestQueue(getActivity());


        //clearing the results first to avoid data duplication when re-loaded
//        loadFictionBooks();

        loadFictionBooks();
        loadFantasyBooks();
        loadMotivationalBooks();
        loadAdventurelBooks();
        loadRomanceBooks();
        loadContempBooks();
        loadMysteryBooks();
        loadHorrorBooks();
        loadThrillerBooks();
        loadScienceBooks();
        loadFictionBooks();
        loadHealthBooks();
        loadHistoryBooks();
        loadChildrenBooks();

        return view;
    }

    @Override
    public void onPause() {
        shimmerFrameLayout.stopShimmer();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();
    }

    private void parseFictionRequest(String key) {

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, key.toString(), null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String volumeId = "";
                        String title = "";
                        String authors = ""; //authors from Json
                        String description = "No description";
                        String publisher = "";
                        String publishedDate = "";
                        String categories = "No Categories";
                        String thumbnail = null;
                        String price = "Not For Sale";
                        String currencyCode = "Not available";
                        String language = "Not Available";
                        int pageCount = 0;
                        int ratingsCount = 0;
                        double averageRating = 5.0;
                        String buyLink = "Not Available";

                        try {
                            JSONArray items = response.getJSONArray("items");

                            for (int i = 0 ; i< items.length() ;i++) {
                                JSONObject item = items.getJSONObject(i);
                                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                                //
                                try{
                                    title = volumeInfo.getString("title");

                                    //author === json array of authors
                                    JSONArray authorArr = volumeInfo.getJSONArray("authors");
                                    if(authorArr.length() == 1){
                                        authors = authorArr.getString(0);
                                    }else {
                                        authors = authorArr.getString(0) + ", " +authorArr.getString(1);
                                    }


                                    publisher = volumeInfo.getString("publisher");
                                    publishedDate = volumeInfo.getString("publishedDate");
                                    pageCount = volumeInfo.getInt("pageCount");


                                    JSONObject saleInfo = item.getJSONObject("saleInfo");
                                    JSONObject listPrice = saleInfo.getJSONObject("listPrice");

                                    price = listPrice.getString("amount") + " " +listPrice.getString("currencyCode");

                                    description = volumeInfo.getString("description");
                                    buyLink = saleInfo.getString("buyLink");
                                    categories = volumeInfo.getJSONArray("categories").getString(0);
                                    averageRating = volumeInfo.getDouble("averageRating");
                                    ratingsCount = volumeInfo.getInt("ratingsCount");
                                    language = volumeInfo.getString("language");

                                }catch (Exception e){

                                }

                                volumeId = item.getString("id");
                                thumbnail = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");

                                String previewLink = volumeInfo.getString("previewLink");
                                String infoLink = volumeInfo.getString("infoLink");


                                volumeBooks.add(new VolumeBooks(volumeId, title, authors,
                                        description, publisher, publishedDate,
                                        categories, thumbnail, previewLink, price, currencyCode,
                                        buyLink, language, pageCount, averageRating, ratingsCount, false)); //we set false as default value of isBookmark\

                                adapter = new FictionBooksAdapter(getActivity(), volumeBooks);
                                rvFiction.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                            }


                        } catch (Exception e) {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        fictionRequest.add(request);

    }

    private void parseFantasyRequest(String key) {

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, key.toString(), null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String title = "";
                        String authors = ""; //authors from Json
                        String description = "No description";
                        String publisher = "";
                        String publishedDate = "";
                        String categories = "No Categories";
                        String thumbnail = null;
                        String price = "Not For Sale";
                        String currencyCode = "Not available";
                        String language = "Not Available";
                        int pageCount = 0;
                        int ratingsCount = 0;
                        double averageRating = 5.0;
                        String buyLink = "Not Available";

                        try {
                            JSONArray items = response.getJSONArray("items");

                            for (int i = 0 ; i< items.length() ;i++) {
                                JSONObject item = items.getJSONObject(i);
                                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                                //
                                try{
                                    title = volumeInfo.getString("title");

                                    //author === json array of authors
                                    JSONArray authorArr = volumeInfo.getJSONArray("authors");
                                    if(authorArr.length() == 1){
                                        authors = authorArr.getString(0);
                                    }else {
                                        authors = authorArr.getString(0) + ", " +authorArr.getString(1);
                                    }


                                    publisher = volumeInfo.getString("publisher");
                                    publishedDate = volumeInfo.getString("publishedDate");
                                    pageCount = volumeInfo.getInt("pageCount");


                                    JSONObject saleInfo = item.getJSONObject("saleInfo");
                                    JSONObject listPrice = saleInfo.getJSONObject("listPrice");

                                    price = listPrice.getString("amount") + " " +listPrice.getString("currencyCode");

                                    description = volumeInfo.getString("description");
                                    buyLink = saleInfo.getString("buyLink");
                                    categories = volumeInfo.getJSONArray("categories").getString(0);
                                    averageRating = volumeInfo.getDouble("averageRating");
                                    ratingsCount = volumeInfo.getInt("ratingsCount");
                                    language = volumeInfo.getString("language");

                                }catch (Exception e){

                                }

                                thumbnail = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");

                                String previewLink = volumeInfo.getString("previewLink");
                                String infoLink = volumeInfo.getString("infoLink");


                                volumeBooks1.add(new VolumeBooks("", title, authors,
                                        description, publisher, publishedDate,
                                        categories, thumbnail, previewLink, price, currencyCode,
                                        buyLink, language, pageCount, averageRating, ratingsCount, false)); //we set false as default value of isBookmark\

                                fantasyAdapter = new FantasyBooksAdapter(getActivity(), volumeBooks1);
                                rvFantasy.setAdapter(fantasyAdapter);
                                fantasyAdapter.notifyDataSetChanged();
                            }


                        } catch (Exception e) {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        fantasyRequest.add(request);

    }

    private void parseMotivationalRequest(String key) {

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, key.toString(), null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String title = "";
                        String authors = ""; //authors from Json
                        String description = "No description";
                        String publisher = "";
                        String publishedDate = "";
                        String categories = "No Categories";
                        String thumbnail = null;
                        String price = "Not For Sale";
                        String currencyCode = "Not available";
                        String language = "Not Available";
                        int pageCount = 0;
                        int ratingsCount = 0;
                        double averageRating = 5.0;
                        String buyLink = "Not Available";

                        try {
                            JSONArray items = response.getJSONArray("items");

                            for (int i = 0 ; i< items.length() ;i++) {
                                JSONObject item = items.getJSONObject(i);
                                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                                //
                                try{
                                    title = volumeInfo.getString("title");

                                    //author === json array of authors
                                    JSONArray authorArr = volumeInfo.getJSONArray("authors");
                                    if(authorArr.length() == 1){
                                        authors = authorArr.getString(0);
                                    }else {
                                        authors = authorArr.getString(0) + ", " +authorArr.getString(1);
                                    }


                                    publisher = volumeInfo.getString("publisher");
                                    publishedDate = volumeInfo.getString("publishedDate");
                                    pageCount = volumeInfo.getInt("pageCount");


                                    JSONObject saleInfo = item.getJSONObject("saleInfo");
                                    JSONObject listPrice = saleInfo.getJSONObject("listPrice");

                                    price = listPrice.getString("amount") + " " +listPrice.getString("currencyCode");

                                    description = volumeInfo.getString("description");
                                    buyLink = saleInfo.getString("buyLink");
                                    categories = volumeInfo.getJSONArray("categories").getString(0);
                                    averageRating = volumeInfo.getDouble("averageRating");
                                    ratingsCount = volumeInfo.getInt("ratingsCount");
                                    language = volumeInfo.getString("language");

                                }catch (Exception e){

                                }

                                thumbnail = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");

                                String previewLink = volumeInfo.getString("previewLink");


                                volumeBooks2.add(new VolumeBooks("",title, authors,
                                        description, publisher, publishedDate,
                                        categories, thumbnail, previewLink, price, currencyCode,
                                        buyLink, language, pageCount, averageRating, ratingsCount, false)); //we set false as default value of isBookmark\

                                motivationalAdapter = new MotivationalBooksAdapter(getActivity(), volumeBooks2);
                                rvMotivational.setAdapter(motivationalAdapter);
                                motivationalAdapter.notifyDataSetChanged();

                            }


                        } catch (Exception e) {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        motivationalRequest.add(request);

    }

    private void parseAdventureRequest(String key) {

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, key.toString(), null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String title = "";
                        String authors = ""; //authors from Json
                        String description = "No description";
                        String publisher = "";
                        String publishedDate = "";
                        String categories = "No Categories";
                        String thumbnail = null;
                        String price = "Not For Sale";
                        String currencyCode = "Not available";
                        String language = "Not Available";
                        int pageCount = 0;
                        int ratingsCount = 0;
                        double averageRating = 5.0;
                        String buyLink = "Not Available";

                        try {
                            JSONArray items = response.getJSONArray("items");

                            for (int i = 0 ; i< items.length() ;i++) {
                                JSONObject item = items.getJSONObject(i);
                                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                                //
                                try{
                                    title = volumeInfo.getString("title");

                                    //author === json array of authors
                                    JSONArray authorArr = volumeInfo.getJSONArray("authors");
                                    if(authorArr.length() == 1){
                                        authors = authorArr.getString(0);
                                    }else {
                                        authors = authorArr.getString(0) + ", " +authorArr.getString(1);
                                    }


                                    publisher = volumeInfo.getString("publisher");
                                    publishedDate = volumeInfo.getString("publishedDate");
                                    pageCount = volumeInfo.getInt("pageCount");


                                    JSONObject saleInfo = item.getJSONObject("saleInfo");
                                    JSONObject listPrice = saleInfo.getJSONObject("listPrice");

                                    price = listPrice.getString("amount") + " " +listPrice.getString("currencyCode");

                                    description = volumeInfo.getString("description");
                                    buyLink = saleInfo.getString("buyLink");
                                    categories = volumeInfo.getJSONArray("categories").getString(0);
                                    averageRating = volumeInfo.getDouble("averageRating");
                                    ratingsCount = volumeInfo.getInt("ratingsCount");
                                    language = volumeInfo.getString("language");

                                }catch (Exception e){

                                }

                                thumbnail = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");

                                String previewLink = volumeInfo.getString("previewLink");


                                volumeBooks3.add(new VolumeBooks("",title, authors,
                                        description, publisher, publishedDate,
                                        categories, thumbnail, previewLink, price, currencyCode,
                                        buyLink, language, pageCount, averageRating, ratingsCount, false)); //we set false as default value of isBookmark\

                                adventureBooksAdapter = new AdventureBooksAdapter(getActivity(), volumeBooks3);
                                rvAdventure.setAdapter(adventureBooksAdapter);
                                adventureBooksAdapter.notifyDataSetChanged();
                            }


                        } catch (Exception e) {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        adventureRequest.add(request);

    }

    private void parseRomanceRequest(String key) {

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, key.toString(), null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String title = "";
                        String authors = ""; //authors from Json
                        String description = "No description";
                        String publisher = "";
                        String publishedDate = "";
                        String categories = "No Categories";
                        String thumbnail = null;
                        String price = "Not For Sale";
                        String currencyCode = "Not available";
                        String language = "Not Available";
                        int pageCount = 0;
                        int ratingsCount = 0;
                        double averageRating = 5.0;
                        String buyLink = "Not Available";

                        try {
                            JSONArray items = response.getJSONArray("items");

                            for (int i = 0 ; i< items.length() ;i++) {
                                JSONObject item = items.getJSONObject(i);
                                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                                //
                                try{
                                    title = volumeInfo.getString("title");

                                    //author === json array of authors
                                    JSONArray authorArr = volumeInfo.getJSONArray("authors");
                                    if(authorArr.length() == 1){
                                        authors = authorArr.getString(0);
                                    }else {
                                        authors = authorArr.getString(0) + ", " +authorArr.getString(1);
                                    }


                                    publisher = volumeInfo.getString("publisher");
                                    publishedDate = volumeInfo.getString("publishedDate");
                                    pageCount = volumeInfo.getInt("pageCount");


                                    JSONObject saleInfo = item.getJSONObject("saleInfo");
                                    JSONObject listPrice = saleInfo.getJSONObject("listPrice");

                                    price = listPrice.getString("amount") + " " +listPrice.getString("currencyCode");

                                    description = volumeInfo.getString("description");
                                    buyLink = saleInfo.getString("buyLink");
                                    categories = volumeInfo.getJSONArray("categories").getString(0);
                                    averageRating = volumeInfo.getDouble("averageRating");
                                    ratingsCount = volumeInfo.getInt("ratingsCount");
                                    language = volumeInfo.getString("language");

                                }catch (Exception e){

                                }

                                thumbnail = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");

                                String previewLink = volumeInfo.getString("previewLink");


                                volumeBooks4.add(new VolumeBooks("",title, authors,
                                        description, publisher, publishedDate,
                                        categories, thumbnail, previewLink, price, currencyCode,
                                        buyLink, language, pageCount, averageRating, ratingsCount, false)); //we set false as default value of isBookmark\

                                romanceBookAdapter = new RomanceBookAdapter(getActivity(), volumeBooks4);
                                rvRomance.setAdapter(romanceBookAdapter);
                                romanceBookAdapter.notifyDataSetChanged();

                            }


                        } catch (Exception e) {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        romanceRequest.add(request);

    }

    private void parseContempRequest(String key) {

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, key.toString(), null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String title = "";
                        String authors = ""; //authors from Json
                        String description = "No description";
                        String publisher = "";
                        String publishedDate = "";
                        String categories = "No Categories";
                        String thumbnail = null;
                        String price = "Not For Sale";
                        String currencyCode = "Not available";
                        String language = "Not Available";
                        int pageCount = 0;
                        int ratingsCount = 0;
                        double averageRating = 5.0;
                        String buyLink = "Not Available";

                        try {
                            JSONArray items = response.getJSONArray("items");

                            for (int i = 0 ; i< items.length() ;i++) {
                                JSONObject item = items.getJSONObject(i);
                                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                                //
                                try{
                                    title = volumeInfo.getString("title");

                                    //author === json array of authors
                                    JSONArray authorArr = volumeInfo.getJSONArray("authors");
                                    if(authorArr.length() == 1){
                                        authors = authorArr.getString(0);
                                    }else {
                                        authors = authorArr.getString(0) + ", " +authorArr.getString(1);
                                    }


                                    publisher = volumeInfo.getString("publisher");
                                    publishedDate = volumeInfo.getString("publishedDate");
                                    pageCount = volumeInfo.getInt("pageCount");


                                    JSONObject saleInfo = item.getJSONObject("saleInfo");
                                    JSONObject listPrice = saleInfo.getJSONObject("listPrice");

                                    price = listPrice.getString("amount") + " " +listPrice.getString("currencyCode");

                                    description = volumeInfo.getString("description");
                                    buyLink = saleInfo.getString("buyLink");
                                    categories = volumeInfo.getJSONArray("categories").getString(0);
                                    averageRating = volumeInfo.getDouble("averageRating");
                                    ratingsCount = volumeInfo.getInt("ratingsCount");
                                    language = volumeInfo.getString("language");

                                }catch (Exception e){

                                }

                                thumbnail = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");

                                String previewLink = volumeInfo.getString("previewLink");


                                volumeBooks5.add(new VolumeBooks("",title, authors,
                                        description, publisher, publishedDate,
                                        categories, thumbnail, previewLink, price, currencyCode,
                                        buyLink, language, pageCount, averageRating, ratingsCount, false)); //we set false as default value of isBookmark\

                                contemporaryBooksAdapter = new ContemporaryBooksAdapter(getActivity(), volumeBooks5);
                                rvContemporary.setAdapter(contemporaryBooksAdapter);
                                contemporaryBooksAdapter.notifyDataSetChanged();

                            }


                        } catch (Exception e) {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        contempRequest.add(request);

    }

    private void parseMysteryRequest(String key) {

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, key.toString(), null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String title = "";
                        String authors = ""; //authors from Json
                        String description = "No description";
                        String publisher = "";
                        String publishedDate = "";
                        String categories = "No Categories";
                        String thumbnail = null;
                        String price = "Not For Sale";
                        String currencyCode = "Not available";
                        String language = "Not Available";
                        int pageCount = 0;
                        int ratingsCount = 0;
                        double averageRating = 5.0;
                        String buyLink = "Not Available";

                        try {
                            JSONArray items = response.getJSONArray("items");

                            for (int i = 0 ; i< items.length() ;i++) {
                                JSONObject item = items.getJSONObject(i);
                                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                                //
                                try{
                                    title = volumeInfo.getString("title");

                                    //author === json array of authors
                                    JSONArray authorArr = volumeInfo.getJSONArray("authors");
                                    if(authorArr.length() == 1){
                                        authors = authorArr.getString(0);
                                    }else {
                                        authors = authorArr.getString(0) + ", " +authorArr.getString(1);
                                    }


                                    publisher = volumeInfo.getString("publisher");
                                    publishedDate = volumeInfo.getString("publishedDate");
                                    pageCount = volumeInfo.getInt("pageCount");


                                    JSONObject saleInfo = item.getJSONObject("saleInfo");
                                    JSONObject listPrice = saleInfo.getJSONObject("listPrice");

                                    price = listPrice.getString("amount") + " " +listPrice.getString("currencyCode");

                                    description = volumeInfo.getString("description");
                                    buyLink = saleInfo.getString("buyLink");
                                    categories = volumeInfo.getJSONArray("categories").getString(0);
                                    averageRating = volumeInfo.getDouble("averageRating");
                                    ratingsCount = volumeInfo.getInt("ratingsCount");
                                    language = volumeInfo.getString("language");

                                }catch (Exception e){

                                }

                                thumbnail = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");

                                String previewLink = volumeInfo.getString("previewLink");


                                volumeBooks6.add(new VolumeBooks("",title, authors,
                                        description, publisher, publishedDate,
                                        categories, thumbnail, previewLink, price, currencyCode,
                                        buyLink, language, pageCount, averageRating, ratingsCount, false)); //we set false as default value of isBookmark\

                                mysteryBooksAdapter = new MysteryBooksAdapter(getActivity(), volumeBooks6);
                                rvMystery.setAdapter(mysteryBooksAdapter);
                                mysteryBooksAdapter.notifyDataSetChanged();

                            }


                        } catch (Exception e) {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mysteryRequest.add(request);

    }

    private void parseHorrorRequest(String key) {

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, key.toString(), null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String title = "";
                        String authors = ""; //authors from Json
                        String description = "No description";
                        String publisher = "";
                        String publishedDate = "";
                        String categories = "No Categories";
                        String thumbnail = null;
                        String price = "Not For Sale";
                        String currencyCode = "Not available";
                        String language = "Not Available";
                        int pageCount = 0;
                        int ratingsCount = 0;
                        double averageRating = 5.0;
                        String buyLink = "Not Available";

                        try {
                            JSONArray items = response.getJSONArray("items");

                            for (int i = 0 ; i< items.length() ;i++) {
                                JSONObject item = items.getJSONObject(i);
                                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                                //
                                try{
                                    title = volumeInfo.getString("title");

                                    //author === json array of authors
                                    JSONArray authorArr = volumeInfo.getJSONArray("authors");
                                    if(authorArr.length() == 1){
                                        authors = authorArr.getString(0);
                                    }else {
                                        authors = authorArr.getString(0) + ", " +authorArr.getString(1);
                                    }


                                    publisher = volumeInfo.getString("publisher");
                                    publishedDate = volumeInfo.getString("publishedDate");
                                    pageCount = volumeInfo.getInt("pageCount");


                                    JSONObject saleInfo = item.getJSONObject("saleInfo");
                                    JSONObject listPrice = saleInfo.getJSONObject("listPrice");

                                    price = listPrice.getString("amount") + " " +listPrice.getString("currencyCode");

                                    description = volumeInfo.getString("description");
                                    buyLink = saleInfo.getString("buyLink");
                                    categories = volumeInfo.getJSONArray("categories").getString(0);
                                    averageRating = volumeInfo.getDouble("averageRating");
                                    ratingsCount = volumeInfo.getInt("ratingsCount");
                                    language = volumeInfo.getString("language");

                                }catch (Exception e){

                                }

                                thumbnail = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");

                                String previewLink = volumeInfo.getString("previewLink");


                                volumeBooks7.add(new VolumeBooks("",title, authors,
                                        description, publisher, publishedDate,
                                        categories, thumbnail, previewLink, price, currencyCode,
                                        buyLink, language, pageCount, averageRating, ratingsCount, false)); //we set false as default value of isBookmark\

                                horrorBooksAdapter = new HorrorBooksAdapter(getActivity(), volumeBooks7);
                                rvHorror.setAdapter(horrorBooksAdapter);
                                horrorBooksAdapter.notifyDataSetChanged();

                            }


                        } catch (Exception e) {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        horrorRequest.add(request);

    }

    private void parseThrillerRequest(String key) {

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, key.toString(), null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String title = "";
                        String authors = ""; //authors from Json
                        String description = "No description";
                        String publisher = "";
                        String publishedDate = "";
                        String categories = "No Categories";
                        String thumbnail = null;
                        String price = "Not For Sale";
                        String currencyCode = "Not available";
                        String language = "Not Available";
                        int pageCount = 0;
                        int ratingsCount = 0;
                        double averageRating = 5.0;
                        String buyLink = "Not Available";

                        try {
                            JSONArray items = response.getJSONArray("items");

                            for (int i = 0 ; i< items.length() ;i++) {
                                JSONObject item = items.getJSONObject(i);
                                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                                //
                                try{
                                    title = volumeInfo.getString("title");

                                    //author === json array of authors
                                    JSONArray authorArr = volumeInfo.getJSONArray("authors");
                                    if(authorArr.length() == 1){
                                        authors = authorArr.getString(0);
                                    }else {
                                        authors = authorArr.getString(0) + ", " +authorArr.getString(1);
                                    }


                                    publisher = volumeInfo.getString("publisher");
                                    publishedDate = volumeInfo.getString("publishedDate");
                                    pageCount = volumeInfo.getInt("pageCount");


                                    JSONObject saleInfo = item.getJSONObject("saleInfo");
                                    JSONObject listPrice = saleInfo.getJSONObject("listPrice");

                                    price = listPrice.getString("amount") + " " +listPrice.getString("currencyCode");

                                    description = volumeInfo.getString("description");
                                    buyLink = saleInfo.getString("buyLink");
                                    categories = volumeInfo.getJSONArray("categories").getString(0);
                                    averageRating = volumeInfo.getDouble("averageRating");
                                    ratingsCount = volumeInfo.getInt("ratingsCount");
                                    language = volumeInfo.getString("language");

                                }catch (Exception e){

                                }

                                thumbnail = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");

                                String previewLink = volumeInfo.getString("previewLink");


                                volumeBooks8.add(new VolumeBooks("",title, authors,
                                        description, publisher, publishedDate,
                                        categories, thumbnail, previewLink, price, currencyCode,
                                        buyLink, language, pageCount, averageRating, ratingsCount, false)); //we set false as default value of isBookmark\

                                thrillerBooksAdapter = new ThrillerBooksAdapter(getActivity(), volumeBooks8);
                                rvThriller.setAdapter(thrillerBooksAdapter);
                                thrillerBooksAdapter.notifyDataSetChanged();

                            }


                        } catch (Exception e) {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        thrillerRequest.add(request);

    }

    private void parseScienceRequest(String key) {

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, key.toString(), null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String title = "";
                        String authors = ""; //authors from Json
                        String description = "No description";
                        String publisher = "";
                        String publishedDate = "";
                        String categories = "No Categories";
                        String thumbnail = null;
                        String price = "Not For Sale";
                        String currencyCode = "Not available";
                        String language = "Not Available";
                        int pageCount = 0;
                        int ratingsCount = 0;
                        double averageRating = 5.0;
                        String buyLink = "Not Available";

                        try {
                            JSONArray items = response.getJSONArray("items");

                            for (int i = 0 ; i< items.length() ;i++) {
                                JSONObject item = items.getJSONObject(i);
                                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                                //
                                try{
                                    title = volumeInfo.getString("title");

                                    //author === json array of authors
                                    JSONArray authorArr = volumeInfo.getJSONArray("authors");
                                    if(authorArr.length() == 1){
                                        authors = authorArr.getString(0);
                                    }else {
                                        authors = authorArr.getString(0) + ", " +authorArr.getString(1);
                                    }


                                    publisher = volumeInfo.getString("publisher");
                                    publishedDate = volumeInfo.getString("publishedDate");
                                    pageCount = volumeInfo.getInt("pageCount");


                                    JSONObject saleInfo = item.getJSONObject("saleInfo");
                                    JSONObject listPrice = saleInfo.getJSONObject("listPrice");

                                    price = listPrice.getString("amount") + " " +listPrice.getString("currencyCode");

                                    description = volumeInfo.getString("description");
                                    buyLink = saleInfo.getString("buyLink");
                                    categories = volumeInfo.getJSONArray("categories").getString(0);
                                    averageRating = volumeInfo.getDouble("averageRating");
                                    ratingsCount = volumeInfo.getInt("ratingsCount");
                                    language = volumeInfo.getString("language");

                                }catch (Exception e){

                                }

                                thumbnail = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");

                                String previewLink = volumeInfo.getString("previewLink");


                                volumeBooks9.add(new VolumeBooks("",title, authors,
                                        description, publisher, publishedDate,
                                        categories, thumbnail, previewLink, price, currencyCode,
                                        buyLink, language, pageCount, averageRating, ratingsCount, false)); //we set false as default value of isBookmark\

                                scienceFictionBooksAdapter= new ScienceFictionBooksAdapter(getActivity(), volumeBooks9);
                                rvScience.setAdapter(scienceFictionBooksAdapter);
                                scienceFictionBooksAdapter.notifyDataSetChanged();

                            }


                        } catch (Exception e) {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        scienceRequest.add(request);

    }

    private void parseHealthRequest(String key) {

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, key.toString(), null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String title = "";
                        String authors = ""; //authors from Json
                        String description = "No description";
                        String publisher = "";
                        String publishedDate = "";
                        String categories = "No Categories";
                        String thumbnail = null;
                        String price = "Not For Sale";
                        String currencyCode = "Not available";
                        String language = "Not Available";
                        int pageCount = 0;
                        int ratingsCount = 0;
                        double averageRating = 5.0;
                        String buyLink = "Not Available";

                        try {
                            JSONArray items = response.getJSONArray("items");

                            for (int i = 0 ; i< items.length() ;i++) {
                                JSONObject item = items.getJSONObject(i);
                                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                                //
                                try{
                                    title = volumeInfo.getString("title");

                                    //author === json array of authors
                                    JSONArray authorArr = volumeInfo.getJSONArray("authors");
                                    if(authorArr.length() == 1){
                                        authors = authorArr.getString(0);
                                    }else {
                                        authors = authorArr.getString(0) + ", " +authorArr.getString(1);
                                    }


                                    publisher = volumeInfo.getString("publisher");
                                    publishedDate = volumeInfo.getString("publishedDate");
                                    pageCount = volumeInfo.getInt("pageCount");


                                    JSONObject saleInfo = item.getJSONObject("saleInfo");
                                    JSONObject listPrice = saleInfo.getJSONObject("listPrice");

                                    price = listPrice.getString("amount") + " " +listPrice.getString("currencyCode");

                                    description = volumeInfo.getString("description");
                                    buyLink = saleInfo.getString("buyLink");
                                    categories = volumeInfo.getJSONArray("categories").getString(0);
                                    averageRating = volumeInfo.getDouble("averageRating");
                                    ratingsCount = volumeInfo.getInt("ratingsCount");
                                    language = volumeInfo.getString("language");

                                }catch (Exception e){

                                }

                                thumbnail = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");

                                String previewLink = volumeInfo.getString("previewLink");


                                volumeBooks10.add(new VolumeBooks("",title, authors,
                                        description, publisher, publishedDate,
                                        categories, thumbnail, previewLink, price, currencyCode,
                                        buyLink, language, pageCount, averageRating, ratingsCount, false)); //we set false as default value of isBookmark\

                                healthBooksAdapter = new HealthBooksAdapter(getActivity(), volumeBooks10);
                                rvHealth.setAdapter(healthBooksAdapter);
                                healthBooksAdapter.notifyDataSetChanged();

                            }


                        } catch (Exception e) {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        healthRequest.add(request);

    }

    private void parseHistoryRequest(String key) {

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, key.toString(), null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String title = "";
                        String authors = ""; //authors from Json
                        String description = "No description";
                        String publisher = "";
                        String publishedDate = "";
                        String categories = "No Categories";
                        String thumbnail = null;
                        String price = "Not For Sale";
                        String currencyCode = "Not available";
                        String language = "Not Available";
                        int pageCount = 0;
                        int ratingsCount = 0;
                        double averageRating = 5.0;
                        String buyLink = "Not Available";

                        try {
                            JSONArray items = response.getJSONArray("items");

                            for (int i = 0 ; i< items.length() ;i++) {
                                JSONObject item = items.getJSONObject(i);
                                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                                //
                                try{
                                    title = volumeInfo.getString("title");

                                    //author === json array of authors
                                    JSONArray authorArr = volumeInfo.getJSONArray("authors");
                                    if(authorArr.length() == 1){
                                        authors = authorArr.getString(0);
                                    }else {
                                        authors = authorArr.getString(0) + ", " +authorArr.getString(1);
                                    }


                                    publisher = volumeInfo.getString("publisher");
                                    publishedDate = volumeInfo.getString("publishedDate");
                                    pageCount = volumeInfo.getInt("pageCount");


                                    JSONObject saleInfo = item.getJSONObject("saleInfo");
                                    JSONObject listPrice = saleInfo.getJSONObject("listPrice");

                                    price = listPrice.getString("amount") + " " +listPrice.getString("currencyCode");

                                    description = volumeInfo.getString("description");
                                    buyLink = saleInfo.getString("buyLink");
                                    categories = volumeInfo.getJSONArray("categories").getString(0);
                                    averageRating = volumeInfo.getDouble("averageRating");
                                    ratingsCount = volumeInfo.getInt("ratingsCount");
                                    language = volumeInfo.getString("language");

                                }catch (Exception e){

                                }

                                thumbnail = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");

                                String previewLink = volumeInfo.getString("previewLink");


                                volumeBooks11.add(new VolumeBooks("",title, authors,
                                        description, publisher, publishedDate,
                                        categories, thumbnail, previewLink, price, currencyCode,
                                        buyLink, language, pageCount, averageRating, ratingsCount, false)); //we set false as default value of isBookmark\

                                historyBooksAdapter = new HistoryBooksAdapter(getActivity(), volumeBooks11);
                                rvHistory.setAdapter(historyBooksAdapter);
                                historyBooksAdapter.notifyDataSetChanged();

                            }


                        } catch (Exception e) {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        historyRequest.add(request);

    }

    private void parseChildrenRequest(String key) {

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, key.toString(), null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String title = "";
                        String authors = ""; //authors from Json
                        String description = "No description";
                        String publisher = "";
                        String publishedDate = "";
                        String categories = "No Categories";
                        String thumbnail = null;
                        String price = "Not For Sale";
                        String currencyCode = "Not available";
                        String language = "Not Available";
                        int pageCount = 0;
                        int ratingsCount = 0;
                        double averageRating = 5.0;
                        String buyLink = "Not Available";

                        try {
                            JSONArray items = response.getJSONArray("items");

                            for (int i = 0 ; i< items.length() ;i++) {
                                JSONObject item = items.getJSONObject(i);
                                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                                //
                                try{
                                    title = volumeInfo.getString("title");

                                    //author === json array of authors
                                    JSONArray authorArr = volumeInfo.getJSONArray("authors");
                                    if(authorArr.length() == 1){
                                        authors = authorArr.getString(0);
                                    }else {
                                        authors = authorArr.getString(0) + ", " +authorArr.getString(1);
                                    }


                                    publisher = volumeInfo.getString("publisher");
                                    publishedDate = volumeInfo.getString("publishedDate");
                                    pageCount = volumeInfo.getInt("pageCount");


                                    JSONObject saleInfo = item.getJSONObject("saleInfo");
                                    JSONObject listPrice = saleInfo.getJSONObject("listPrice");

                                    price = listPrice.getString("amount") + " " +listPrice.getString("currencyCode");

                                    description = volumeInfo.getString("description");
                                    buyLink = saleInfo.getString("buyLink");
                                    categories = volumeInfo.getJSONArray("categories").getString(0);
                                    averageRating = volumeInfo.getDouble("averageRating");
                                    ratingsCount = volumeInfo.getInt("ratingsCount");
                                    language = volumeInfo.getString("language");

                                }catch (Exception e){

                                }

                                thumbnail = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");

                                String previewLink = volumeInfo.getString("previewLink");


                                volumeBooks12.add(new VolumeBooks("",title, authors,
                                        description, publisher, publishedDate,
                                        categories, thumbnail, previewLink, price, currencyCode,
                                        buyLink, language, pageCount, averageRating, ratingsCount, false)); //we set false as default value of isBookmark\

                                childrensBookAdapter = new ChildrensBookAdapter(getActivity(), volumeBooks12);
                                rvChildren.setAdapter(childrensBookAdapter);
                                childrensBookAdapter.notifyDataSetChanged();

                                shimmerFrameLayout.stopShimmer();
                                shimmerFrameLayout.setVisibility(View.GONE);
                                parentLayout.setVisibility(View.VISIBLE);

                            }


                        } catch (Exception e) {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        childrenRequest.add(request);

    }



    private void loadFictionBooks(){

        String final_query = strFiction.replace(" ","+");
        Uri uri = Uri.parse(Constant.BOOK_SUBJECT_URL +final_query+Constant.BOOK_MAX_RES);
        Uri.Builder builder = uri.buildUpon();

        parseFictionRequest(builder.toString());

    }

    private void loadFantasyBooks() {

        String final_query = strFantasy.replace(" ","+");
        Uri uri = Uri.parse(Constant.BOOK_SUBJECT_URL+final_query+Constant.BOOK_MAX_RES);
        Uri.Builder builder = uri.buildUpon();

        parseFantasyRequest(builder.toString());

    }
//
    private void loadMotivationalBooks() {

        String final_query = strMotivational.replace(" ","+");
        Uri uri = Uri.parse(Constant.BOOK_SUBJECT_URL+final_query+Constant.BOOK_MAX_RES);
        Uri.Builder builder = uri.buildUpon();

        parseMotivationalRequest(builder.toString());
    }

    private void loadAdventurelBooks() {

        String final_query = strAdventure.replace(" ","+");
        Uri uri = Uri.parse(Constant.BOOK_SUBJECT_URL+final_query+Constant.BOOK_MAX_RES);
        Uri.Builder builder = uri.buildUpon();

        parseAdventureRequest(builder.toString());
    }

    private void loadRomanceBooks() {

        String final_query = strRomance.replace(" ","+");
        Uri uri = Uri.parse(Constant.BOOK_SUBJECT_URL+final_query+Constant.BOOK_MAX_RES);
        Uri.Builder builder = uri.buildUpon();

        parseRomanceRequest(builder.toString());
    }

    private void loadContempBooks() {

        String final_query = strContemp.replace(" ","+");
        Uri uri = Uri.parse(Constant.BOOK_SUBJECT_URL+final_query+Constant.BOOK_MAX_RES);
        Uri.Builder builder = uri.buildUpon();

        parseContempRequest(builder.toString());
    }


    private void loadMysteryBooks() {

        String final_query = strMystery.replace(" ","+");
        Uri uri = Uri.parse(Constant.BOOK_SUBJECT_URL+final_query+Constant.BOOK_MAX_RES);
        Uri.Builder builder = uri.buildUpon();

        parseMysteryRequest(builder.toString());
    }

    private void loadHorrorBooks() {

        String final_query = strHorror.replace(" ","+");
        Uri uri = Uri.parse(Constant.BOOK_SUBJECT_URL+final_query+Constant.BOOK_MAX_RES);
        Uri.Builder builder = uri.buildUpon();

        parseHorrorRequest(builder.toString());
    }

    private void loadThrillerBooks() {

        String final_query = strThriller.replace(" ","+");
        Uri uri = Uri.parse(Constant.BOOK_SUBJECT_URL+final_query+Constant.BOOK_MAX_RES);
        Uri.Builder builder = uri.buildUpon();

        parseThrillerRequest(builder.toString());
    }

    private void loadScienceBooks() {

        String final_query = strScience.replace(" ","+");
        Uri uri = Uri.parse(Constant.BOOK_SUBJECT_URL+final_query+Constant.BOOK_MAX_RES);
        Uri.Builder builder = uri.buildUpon();

        parseScienceRequest(builder.toString());
    }

    private void loadHealthBooks() {

        String final_query = strHealth.replace(" ","+");
        Uri uri = Uri.parse(Constant.BOOK_SUBJECT_URL+final_query+Constant.BOOK_MAX_RES);
        Uri.Builder builder = uri.buildUpon();

        parseHealthRequest(builder.toString());
    }

    private void loadHistoryBooks() {

        String final_query = strHistory.replace(" ","+");
        Uri uri = Uri.parse(Constant.BOOK_SUBJECT_URL+final_query+Constant.BOOK_MAX_RES);
        Uri.Builder builder = uri.buildUpon();

        parseHistoryRequest(builder.toString());
    }

    private void loadChildrenBooks() {

        String final_query = strChildren.replace(" ","+");
        Uri uri = Uri.parse(Constant.BOOK_SUBJECT_URL+final_query+Constant.BOOK_MAX_RES);
        Uri.Builder builder = uri.buildUpon();

        parseChildrenRequest(builder.toString());
    }

}
