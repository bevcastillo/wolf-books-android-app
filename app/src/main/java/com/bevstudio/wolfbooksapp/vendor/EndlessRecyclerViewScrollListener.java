package com.bevstudio.wolfbooksapp.vendor;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * Created by Beverly May Castillo on 07/20/2021
 */

public class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 3;
    // The current offset index of data you have loaded
    private int currentPage = 0;
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    // True if we are still waiting for the last set of data to load.
    private boolean loading = true;
    // Sets the starting page index
    private int startingPageIndex = 0;

    private static final int HIDE_THRESHOLD = 8;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;
    private int mTotalScrolled = 0;

    RecyclerView.LayoutManager mLayoutManager;
    private Callback callback;
    private HideShowListener hideShowListener;

    public EndlessRecyclerViewScrollListener(LinearLayoutManager layoutManager, Callback callback) {
        this.callback = callback;
        this.mLayoutManager = layoutManager;
    }

    public EndlessRecyclerViewScrollListener(GridLayoutManager layoutManager, Callback callback) {
        this.callback = callback;
        this.mLayoutManager = layoutManager;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
    }

    public EndlessRecyclerViewScrollListener(GridLayoutManager layoutManager, Callback callback, HideShowListener hideShowListener) {
        this.callback = callback;
        this.hideShowListener = hideShowListener;
        this.mLayoutManager = layoutManager;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
    }

    public EndlessRecyclerViewScrollListener(StaggeredGridLayoutManager layoutManager, Callback callback) {
        this.callback = callback;
        this.mLayoutManager = layoutManager;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
    }

    public EndlessRecyclerViewScrollListener(LinearLayoutManager layoutManager, Callback callback, HideShowListener hideShowListener) {
        this.callback = callback;
        this.mLayoutManager = layoutManager;
        this.hideShowListener = hideShowListener;
    }

    public EndlessRecyclerViewScrollListener(StaggeredGridLayoutManager layoutManager, Callback callback, HideShowListener hideShowListener) {
        this.callback = callback;
        this.mLayoutManager = layoutManager;
        this.hideShowListener = hideShowListener;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
    }


    public int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            }
            else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    public void reset() {
        visibleThreshold = 3;
        currentPage = 0;
        previousTotalItemCount = 0;
        loading = true;
        startingPageIndex = 0;
    }

    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.
    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        int lastVisibleItemPosition = 0;
        int totalItemCount = mLayoutManager.getItemCount();
        mTotalScrolled += dy;

        if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(null);
            // get maximum element within the list
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
        } else if (mLayoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        } else if (mLayoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        }

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }
        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many total columns there are too
        if (!loading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount) {
            currentPage++;
            if(callback != null){
                callback.onLoadMore(currentPage, totalItemCount);
            }
            loading = true;
        }

        if(hideShowListener != null){
            hideShowListener.onScrolled(view, dx, dy, mTotalScrolled);
            if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
                hideShowListener.onHide();
                controlsVisible = false;
                scrolledDistance = 0;
            } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
                hideShowListener.onShow();
                controlsVisible = true;
                scrolledDistance = 0;
            }

            if((controlsVisible && dy>0) || (!controlsVisible && dy<0)) {
                scrolledDistance += dy;
            }
        }
    }


    public interface Callback{
        void onLoadMore(int page, int totalItemsCount);
    }

    public interface HideShowListener{
        void onHide();
        void onShow();
        void onScrolled(RecyclerView view, int dx, int dy, int totalScrolled);
    }
}
