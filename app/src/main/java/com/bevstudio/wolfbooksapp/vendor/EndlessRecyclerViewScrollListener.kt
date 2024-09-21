package com.bevstudio.wolfbooksapp.vendor

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Created by Beverly May Castillo on 07/20/2021
 */
class EndlessRecyclerViewScrollListener : RecyclerView.OnScrollListener {
    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private var visibleThreshold = 3

    // The current offset index of data you have loaded
    private var currentPage = 0

    // The total number of items in the dataset after the last load
    private var previousTotalItemCount = 0

    // True if we are still waiting for the last set of data to load.
    private var loading = true

    // Sets the starting page index
    private var startingPageIndex = 0

    private var scrolledDistance = 0
    private var controlsVisible = true
    private var mTotalScrolled = 0

    var mLayoutManager: RecyclerView.LayoutManager
    private var callback: Callback?
    private var hideShowListener: HideShowListener? = null

    constructor(layoutManager: LinearLayoutManager, callback: Callback?) {
        this.callback = callback
        this.mLayoutManager = layoutManager
    }

    constructor(layoutManager: GridLayoutManager, callback: Callback?) {
        this.callback = callback
        this.mLayoutManager = layoutManager
        visibleThreshold = visibleThreshold * layoutManager.spanCount
    }

    constructor(
        layoutManager: GridLayoutManager,
        callback: Callback?,
        hideShowListener: HideShowListener?
    ) {
        this.callback = callback
        this.hideShowListener = hideShowListener
        this.mLayoutManager = layoutManager
        visibleThreshold = visibleThreshold * layoutManager.spanCount
    }

    constructor(layoutManager: StaggeredGridLayoutManager, callback: Callback?) {
        this.callback = callback
        this.mLayoutManager = layoutManager
        visibleThreshold = visibleThreshold * layoutManager.spanCount
    }

    constructor(
        layoutManager: LinearLayoutManager,
        callback: Callback?,
        hideShowListener: HideShowListener?
    ) {
        this.callback = callback
        this.mLayoutManager = layoutManager
        this.hideShowListener = hideShowListener
    }

    constructor(
        layoutManager: StaggeredGridLayoutManager,
        callback: Callback?,
        hideShowListener: HideShowListener?
    ) {
        this.callback = callback
        this.mLayoutManager = layoutManager
        this.hideShowListener = hideShowListener
        visibleThreshold = visibleThreshold * layoutManager.spanCount
    }


    fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    fun reset() {
        visibleThreshold = 3
        currentPage = 0
        previousTotalItemCount = 0
        loading = true
        startingPageIndex = 0
    }

    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.
    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        var lastVisibleItemPosition = 0
        val totalItemCount = mLayoutManager.itemCount
        mTotalScrolled += dy

        if (mLayoutManager is StaggeredGridLayoutManager) {
            val lastVisibleItemPositions =
                (mLayoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
            // get maximum element within the list
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
        } else if (mLayoutManager is LinearLayoutManager) {
            lastVisibleItemPosition =
                (mLayoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        } else if (mLayoutManager is GridLayoutManager) {
            lastVisibleItemPosition =
                (mLayoutManager as GridLayoutManager).findLastVisibleItemPosition()
        }

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex
            this.previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                this.loading = true
            }
        }
        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many total columns there are too
        if (!loading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount) {
            currentPage++
            if (callback != null) {
                callback!!.onLoadMore(currentPage, totalItemCount)
            }
            loading = true
        }

        if (hideShowListener != null) {
            hideShowListener!!.onScrolled(view, dx, dy, mTotalScrolled)
            if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
                hideShowListener!!.onHide()
                controlsVisible = false
                scrolledDistance = 0
            } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
                hideShowListener!!.onShow()
                controlsVisible = true
                scrolledDistance = 0
            }

            if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
                scrolledDistance += dy
            }
        }
    }


    interface Callback {
        fun onLoadMore(page: Int, totalItemsCount: Int)
    }

    interface HideShowListener {
        fun onHide()
        fun onShow()
        fun onScrolled(view: RecyclerView?, dx: Int, dy: Int, totalScrolled: Int)
    }

    companion object {
        private const val HIDE_THRESHOLD = 8
    }
}
