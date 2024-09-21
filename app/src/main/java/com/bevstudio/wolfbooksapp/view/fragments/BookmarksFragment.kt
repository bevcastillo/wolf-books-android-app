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
import com.bevstudio.wolfbooksapp.adapters.BookmarksRecyclerviewAdapter
import com.bevstudio.wolfbooksapp.model.db.VolumeBooks
import com.bevstudio.wolfbooksapp.request.db.DatabaseHelper
import com.bevstudio.wolfbooksapp.vendor.InternetConnection
import com.facebook.shimmer.ShimmerFrameLayout


/**
 * A simple [Fragment] subclass.
 */
class BookmarksFragment : Fragment(), OnRefreshListener {
    private lateinit var tvBookmarkCount: TextView
    private lateinit var placeholderTV: TextView
    private lateinit var headerTV: TextView
    private lateinit var textTV: TextView
    private lateinit var bookmarksRV: RecyclerView
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout
    private lateinit var bookmarksSRL: SwipeRefreshLayout
    private lateinit var noConnectionLL: View
    private lateinit var errorBTN: Button
    private lateinit var bookmarksAdapter: BookmarksRecyclerviewAdapter
    private lateinit var list: List<VolumeBooks>
    private lateinit var db: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bookmarks, container, false)

        setHasOptionsMenu(true)

        tvBookmarkCount = view.findViewById(R.id.tv_bookmarks_count)
        bookmarksRV = view.findViewById(R.id.bookmarksRV)
        noConnectionLL = view.findViewById(R.id.noConnectionLL)
        shimmerFrameLayout = view.findViewById(R.id.shimmerFrameLayout)
        bookmarksSRL = view.findViewById(R.id.bookmarksSRL)
        headerTV = view.findViewById(R.id.headerTV)
        textTV = view.findViewById(R.id.textTV)
        errorBTN = view.findViewById(R.id.errorBTN)

        activity?.title = "My Bookmarks"
        bookmarksSRL.setColorSchemeColors(resources.getColor(R.color.colorPrimary))
        bookmarksSRL.setOnRefreshListener(this)
        shimmerFrameLayout.setVisibility(View.VISIBLE)

        checkInternetConnection()
        loadBookmarks()

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    private fun checkInternetConnection() {
        InternetConnection.isInternetConnected(requireContext(), noConnectionLL, bookmarksRV)
        headerTV.setText(R.string.no_internet_header)
        textTV.setText(R.string.no_internet_text)
        errorBTN.visibility = View.VISIBLE
        errorBTN.setText(R.string.try_again)
    }

    private fun loadBookmarks() {
        bookmarksSRL.isRefreshing = false
        db = DatabaseHelper(context)
        list = ArrayList()

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        bookmarksRV.layoutManager = layoutManager
        list = db.all

        if (list.size == 1) {
            tvBookmarkCount.text = list.size.toString() + " item found"
        } else if (list.size == 0) {
            tvBookmarkCount.text = ""
        } else {
            tvBookmarkCount.text = list.size.toString() + " items found"
        }

        bookmarksAdapter = BookmarksRecyclerviewAdapter(requireContext(), list)
        shimmerFrameLayout.visibility = View.GONE
        bookmarksRV.adapter = bookmarksAdapter
        bookmarksAdapter.notifyDataSetChanged()

        if (list.isEmpty()) {
            noConnectionLL.visibility = View.VISIBLE
            bookmarksRV.visibility = View.GONE
            headerTV.setText(R.string.no_bookmarks)
            textTV.setText(R.string.no_bookmarks_placeholder)
            errorBTN.visibility = View.GONE
        } else {
            bookmarksRV.visibility = View.VISIBLE
            noConnectionLL.visibility = View.GONE
            shimmerFrameLayout.visibility = View.GONE
        }
    }

    override fun onPause() {
        shimmerFrameLayout.stopShimmer()
        super.onPause()
    }

    override fun onRefresh() {
        shimmerFrameLayout.visibility = View.VISIBLE
        bookmarksRV.visibility = View.GONE
        loadBookmarks()
    }

    override fun onDestroyView() {
        db.close()
        super.onDestroyView()
    }
}
