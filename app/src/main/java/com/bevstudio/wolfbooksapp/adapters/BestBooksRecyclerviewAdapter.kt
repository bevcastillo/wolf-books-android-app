package com.bevstudio.wolfbooksapp.adapters

import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bevstudio.wolfbooksapp.R
import com.bevstudio.wolfbooksapp.helper.Constant
import com.bevstudio.wolfbooksapp.model.statics.BestBooks
import com.bevstudio.wolfbooksapp.view.activity.BookInfoActivity
import com.bumptech.glide.Glide
import com.github.siyamed.shapeimageview.RoundedImageView

class BestBooksRecyclerviewAdapter(private val context: Context) :
    RecyclerView.Adapter<BestBooksRecyclerviewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_best_books, parent, false)
        val viewHolder = ViewHolder(view)

        view.setOnClickListener { v ->
            val volume_id = BestBooks.volume_id[viewHolder.adapterPosition]
            val intent = Intent(v.context, BookInfoActivity::class.java)
            intent.putExtra("volume_id", volume_id)
            v.context.startActivity(intent)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = (position + 1).toString() + ""
        holder.titleTV.text = BestBooks.title[position]
        holder.authorTV.text = "By " + BestBooks.author[position]

        try {
            Glide.with(context).load(BestBooks.image[position]).into(holder.imageView)
        } catch (e: Exception) {
            Glide.with(context).load(Constant.N0_IMAGE_PLACEHOLDER).into(holder.imageView)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.descriptionTV.text =
                Html.fromHtml(BestBooks.desc[position], Html.FROM_HTML_MODE_COMPACT)
        } else {
            holder.descriptionTV.text = Html.fromHtml(BestBooks.desc[position])
        }
    }

    override fun getItemCount(): Int {
        return BestBooks.title.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.textView)
        var titleTV: TextView = itemView.findViewById(R.id.titleTV)
        var authorTV: TextView = itemView.findViewById(R.id.authorTV)
        var descriptionTV: TextView = itemView.findViewById(R.id.descriptionTV)
        var imageView: RoundedImageView = itemView.findViewById(R.id.imageView)
    }
}
