package com.bevstudio.wolfbooksapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.bevstudio.wolfbooksapp.R
import com.bevstudio.wolfbooksapp.adapters.BestBooksRecyclerviewAdapter
import com.bevstudio.wolfbooksapp.model.api.Books
import com.bevstudio.wolfbooksapp.request.api.RequestService
import com.bevstudio.wolfbooksapp.request.api.RetrofitClass
import com.bevstudio.wolfbooksapp.vendor.InternetConnection
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import retrofit2.Call

/**
 * A simple [Fragment] subclass.
 */
class TopBooksFragment : Fragment(), OnRefreshListener {
    private lateinit var bestBooksdAdapter: BestBooksRecyclerviewAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var mAdView: AdView
    private lateinit var newBooksRV: RecyclerView
    private lateinit var shimmer_view_container: ShimmerFrameLayout
    private lateinit var searchSRL: SwipeRefreshLayout
    private lateinit var newReleaseBooksCall: Call<Books>
    private lateinit var requestService: RequestService
    private lateinit var noConnectionLL: View
    private lateinit var parentLL: View
    private lateinit var headerTV: TextView
    private lateinit var textTV: TextView
    private lateinit var errorBTN: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_trending, container, false)

        setHasOptionsMenu(true)
        mAdView = view.findViewById(R.id.adView)
        newBooksRV = view.findViewById(R.id.newBooksRV)
        shimmer_view_container = view.findViewById(R.id.shimmer_view_container)
        searchSRL = view.findViewById(R.id.searchSRL)
        parentLL = view.findViewById(R.id.parentLL)
        noConnectionLL = view.findViewById(R.id.noConnectionLL)
        headerTV = view.findViewById(R.id.headerTV)
        textTV = view.findViewById(R.id.textTV)
        errorBTN = view.findViewById(R.id.errorBTN)

        requestService = RetrofitClass.aPIInstance

        InternetConnection.isInternetConnected(requireContext(), noConnectionLL, parentLL)
        headerTV.setText(R.string.no_internet_header)
        textTV.setText(R.string.no_internet_text)
        errorBTN.setVisibility(View.VISIBLE)
        errorBTN.setText(R.string.try_again)

        searchSRL.setOnRefreshListener(this)
        searchSRL.setColorSchemeColors(resources.getColor(R.color.colorPrimary))
//        MobileAds.initialize(context) { }

        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        activity?.title = "Best Books Ever"
        displayBestBooksEver()

        return view
    }

    private fun displayBestBooksEver() {
        shimmer_view_container.visibility = View.GONE
        newBooksRV.visibility = View.VISIBLE
        searchSRL.isRefreshing = false
        bestBooksdAdapter = BestBooksRecyclerviewAdapter(requireContext())
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        newBooksRV.layoutManager = layoutManager
        newBooksRV.adapter = bestBooksdAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun onPause() {
        shimmer_view_container.stopShimmer()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        shimmer_view_container.startShimmer()
    }

    override fun onRefresh() {
        shimmer_view_container.visibility = View.VISIBLE
        newBooksRV.visibility = View.GONE
        displayBestBooksEver()
    }
}
