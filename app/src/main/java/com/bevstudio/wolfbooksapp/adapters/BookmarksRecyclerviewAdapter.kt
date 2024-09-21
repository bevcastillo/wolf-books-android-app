package com.bevstudio.wolfbooksapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bevstudio.wolfbooksapp.R
import com.bevstudio.wolfbooksapp.helper.Constant
import com.bevstudio.wolfbooksapp.model.api.Item
import com.bevstudio.wolfbooksapp.model.db.VolumeBooks
import com.bevstudio.wolfbooksapp.request.api.RetrofitClass.aPIInstance
import com.bevstudio.wolfbooksapp.request.db.DatabaseHelper
import com.bevstudio.wolfbooksapp.vendor.NumberFormatter.format
import com.bevstudio.wolfbooksapp.view.activity.BookInfoActivity
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerFrameLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookmarksRecyclerviewAdapter //    DBManager dbManager;
    (private val context: Context, private val localVolumeBooks: List<VolumeBooks>) :
    RecyclerView.Adapter<BookmarksRecyclerviewAdapter.ViewHolder>() {
    private lateinit var itemCall: Call<Item>
    private lateinit var itemCall1: Call<Item>
    private lateinit var itemCall2: Call<Item>
    private val requestService = aPIInstance
    var db: DatabaseHelper? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_bookmark_horizontal, parent, false)
        val viewHolder = ViewHolder(view)

        viewHolder.bookmarkActiveIV.setOnClickListener {
            db = DatabaseHelper(context)

            val volumeBooks1 = localVolumeBooks[viewHolder.adapterPosition]
            itemCall2 = requestService.getBookItem(volumeBooks1.volumeId)
            itemCall2!!.enqueue(object : Callback<Item> {
                override fun onResponse(call: Call<Item>, response: Response<Item>) {
                    db!!.removeBookmark(volumeBooks1.id)
//                    localVolumeBooks.removeAt(viewHolder.adapterPosition) // TODO - To fix
                    notifyItemRemoved(viewHolder.adapterPosition)
                    notifyItemChanged(viewHolder.adapterPosition, localVolumeBooks.size)
                    Toast.makeText(
                        context,
                        response.body()!!.volumeInfo!!.title + " has been removed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onFailure(call: Call<Item>, t: Throwable) {
                }
            })
        }

        view.setOnClickListener { v ->
            val volumeBooks1 = localVolumeBooks[viewHolder.adapterPosition]
            itemCall1 = requestService.getBookItem(volumeBooks1.volumeId)
            itemCall1!!.enqueue(object : Callback<Item> {
                override fun onResponse(call: Call<Item>, response: Response<Item>) {
                    if (response.isSuccessful) {
                        //passing data from adapter to activity using intent
                        val intent = Intent(v.context, BookInfoActivity::class.java)
                        intent.putExtra("volume_id", response.body()!!.id)
                        intent.putExtra("bookmark_id", volumeBooks1.id)
                        v.context.startActivity(intent)
                    }
                }

                override fun onFailure(call: Call<Item>, t: Throwable) {
                }
            })
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val volumeBooks = localVolumeBooks[position]
        itemCall = requestService.getBookItem(volumeBooks.volumeId)

        itemCall!!.enqueue(object : Callback<Item?> {
            override fun onResponse(call: Call<Item?>, response: Response<Item?>) {
                val item = response.body()
                if (response.isSuccessful) {
                    holder.shimmerFrameLayout.visibility = View.GONE
                    holder.parentLL.visibility = View.VISIBLE
                    holder.RatingRB.visibility = View.VISIBLE
                    holder.titleTV.text = item!!.volumeInfo!!.title
                    holder.bookmarkIV.visibility = View.GONE
                    holder.bookmarkActiveIV.visibility = View.VISIBLE
                    try {
                        holder.publisherTV.text = item.volumeInfo!!.publisher
                    } catch (e: Exception) {
                        holder.publisherTV.setText(R.string.dash)
                    }

                    try {
                        Glide.with(context).load(item.volumeInfo!!.imageLinks!!.smallThumbnail)
                            .centerCrop().into(holder.imageView)
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

                            4 -> holder.authorTV.text =
                                "By " + item.volumeInfo!!.authors!![0] + ", " +
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
            }

            override fun onFailure(call: Call<Item?>, t: Throwable) {
            }
        })
    }

    override fun getItemCount(): Int {
        return localVolumeBooks.size
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
        var parentLL: View = itemView.findViewById(R.id.parentLL)
        var shimmerFrameLayout: ShimmerFrameLayout = itemView.findViewById(R.id.shimmerFrameLayout)
    }
}
