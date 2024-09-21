package com.bevstudio.wolfbooksapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bevstudio.wolfbooksapp.R
import com.bevstudio.wolfbooksapp.helper.Constant
import com.bevstudio.wolfbooksapp.model.api.Item
import com.bevstudio.wolfbooksapp.vendor.NumberFormatter.format
import com.bevstudio.wolfbooksapp.view.activity.BookInfoActivity
import com.bumptech.glide.Glide

class SearchResultsRecyclerviewAdapter(
    private val context: Context,
    private val items: List<Item>
) : RecyclerView.Adapter<SearchResultsRecyclerviewAdapter.ViewHolder>() {
    private val bookmarkedStatus = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_book_card_horizontal, parent, false)
        val viewHolder = ViewHolder(view)

        view.setOnClickListener { v ->
            val volumeId = items[viewHolder.adapterPosition].id
            val intent = Intent(v.context, BookInfoActivity::class.java)
            intent.putExtra("volume_id", volumeId)
            intent.putExtra("is_bookmarked", bookmarkedStatus)
            v.context.startActivity(intent)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.titleTV.text = item.volumeInfo!!.title
        holder.RatingRB.visibility = View.VISIBLE

        //        holder.bookmarkIV.setVisibility(View.GONE);
//        holder.bookmarkActiveIV.setVisibility(View.GONE);

//        for (int i=0; i<list.size(); i++) {
//            if (list.get(i).getVolumeId().equals(item.getId())) {
//                holder.bookmarkActiveIV.setVisibility(View.VISIBLE);
//                holder.bookmarkIV.setVisibility(View.GONE);
//                bookmarkedStatus="yes";
//                break;
//            }else {
//                holder.bookmarkActiveIV.setVisibility(View.GONE);
//                holder.bookmarkIV.setVisibility(View.VISIBLE);
//                bookmarkedStatus="no";
//            }
//        }
        try {
            holder.publisherTV.text = item.volumeInfo!!.publisher
        } catch (e: Exception) {
            holder.publisherTV.setText(R.string.dash)
        }

        try {
            Glide.with(context).load(item.volumeInfo!!.imageLinks!!.smallThumbnail).centerCrop()
                .into(holder.imageView)
        } catch (e: Exception) {
            Glide.with(context).load(Constant.N0_IMAGE_PLACEHOLDER)
                .centerCrop().into(holder.imageView)
        }
        try {
            if (item.volumeInfo!!.ratingsCount == 1) {
                holder.ratingsTV.text =
                    item.volumeInfo!!.averageRating.toString() + " avg rating — " + format(
                        item.volumeInfo!!.ratingsCount!!.toFloat()
                    ) + " rating"
            } else {
                holder.ratingsTV.text =
                    item.volumeInfo!!.averageRating.toString() + " avg rating — " + format(
                        item.volumeInfo!!.ratingsCount!!.toFloat()
                    ) + " ratings"
            }
        } catch (e: Exception) {
            holder.ratingsTV.setText(R.string.no_reviews)
        }

        try {
            when (item.volumeInfo!!.authors!!.size) {
                1 -> holder.authorTV.text = "By " + item.volumeInfo!!.authors!![0]
                2 -> holder.authorTV.text =
                    "By " + item.volumeInfo!!.authors!![0] + ", " + item.volumeInfo!!.authors!![1]

                3 -> holder.authorTV.text =
                    "By " + item.volumeInfo!!.authors!![0] + ", " + item.volumeInfo!!.authors!![1] + ", " + item.volumeInfo!!.authors!![2]

                4 -> holder.authorTV.text = "By " + item.volumeInfo!!.authors!![0] + ", " +
                        item.volumeInfo!!.authors!![1] + ", " +
                        item.volumeInfo!!.authors!![2] + ", " + item.volumeInfo!!.authors!![3]

                5 -> holder.authorTV.text =
                    "By " + item.volumeInfo!!.authors!![0] + ", " + item.volumeInfo!!.authors!![1] + ", " + item.volumeInfo!!.authors!![2] +
                            ", " + item.volumeInfo!!.authors!![3] + ", " + item.volumeInfo!!.authors!![4]

                else -> holder.authorTV.text = "By " + item.volumeInfo!!.authors!![0]
            }
        } catch (e: Exception) {
            holder.authorTV.setText(R.string.dash)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.imageView)
        var bookmarkActiveIV: ImageView = itemView.findViewById(R.id.bookmarkActiveIV)
        var bookmarkIV: ImageView = itemView.findViewById(R.id.bookmarkIV)
        var publisherTV: TextView = itemView.findViewById(R.id.publisherTV)
        var titleTV: TextView = itemView.findViewById(R.id.titleTV)
        var authorTV: TextView = itemView.findViewById(R.id.authorTV)
        var ratingsTV: TextView = itemView.findViewById(R.id.ratingsTV)
        var noRatingTV: TextView = itemView.findViewById(R.id.noRatingTV)
        var RatingRB: RatingBar = itemView.findViewById(R.id.RatingRB)
    }
}
