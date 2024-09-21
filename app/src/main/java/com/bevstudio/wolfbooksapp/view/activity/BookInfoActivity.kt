package com.bevstudio.wolfbooksapp.view.activity

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bevstudio.wolfbooksapp.R
import com.bevstudio.wolfbooksapp.helper.Constant
import com.bevstudio.wolfbooksapp.model.api.Item
import com.bevstudio.wolfbooksapp.model.db.VolumeBooks
import com.bevstudio.wolfbooksapp.request.api.RequestService
import com.bevstudio.wolfbooksapp.request.api.RetrofitClass
import com.bevstudio.wolfbooksapp.request.db.DBManager
import com.bevstudio.wolfbooksapp.request.db.DatabaseHelper
import com.bevstudio.wolfbooksapp.vendor.CurrencyConverter
import com.bevstudio.wolfbooksapp.vendor.NumberFormatter
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerFrameLayout
import com.github.siyamed.shapeimageview.RoundedImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.util.Locale

class BookInfoActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var bookImageIV: RoundedImageView
    private lateinit var publisherTV: TextView
    private lateinit var titleTV: TextView
    private lateinit var authorTV: TextView
    private lateinit var noRatingPlaceholderTV: TextView
    private lateinit var descriptionTV: TextView
    private lateinit var categoriesTV: TextView
    private lateinit var publishedDateTV: TextView
    private lateinit var pageCountTV: TextView
    private lateinit var languageTV: TextView
    private lateinit var isbnsTV: TextView
    private lateinit var ratingsTV: TextView
    private lateinit var maturityRatingTV: TextView
    private lateinit var previewBTN: TextView
    private lateinit var printTypeTV2: TextView
    private lateinit var printTypeTV: TextView
    private lateinit var heightTV: TextView
    private lateinit var widthTV: TextView
    private lateinit var thicknessTV: TextView
    private lateinit var shimmer_view_container: ShimmerFrameLayout
    private lateinit var layout_parent: ConstraintLayout
    private lateinit var buyLinkBTN: Button
    private lateinit var activeBookmark: Button
    private lateinit var inactiveBookmark: Button
    private lateinit var averageRatingRB: RatingBar
    private lateinit var requestService: RequestService
    private lateinit var singleItemCall: Call<Item>
    var title: String = ""
    var volume_id: String? = ""
    var bookmarked: String? = ""
    var bookmark_id: Int = 0
    var list: List<VolumeBooks> = ArrayList()
    var db: DatabaseHelper? = null
    var dbManager: DBManager? = null
    var dialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_info)

        requestService = RetrofitClass.aPIInstance
        bookImageIV = findViewById(R.id.bookImageIV)
        ratingsTV = findViewById(R.id.ratingsTV)
        publisherTV = findViewById(R.id.publisherTV)
        titleTV = findViewById(R.id.titleTV)
        authorTV = findViewById(R.id.authorTV)
        descriptionTV = findViewById(R.id.descriptionTV)
        categoriesTV = findViewById(R.id.categoriesTV)
        publishedDateTV = findViewById(R.id.publishedDateTV)
        noRatingPlaceholderTV = findViewById(R.id.noRatingPlaceholderTV)
        isbnsTV = findViewById(R.id.isbnsTV)
        pageCountTV = findViewById(R.id.pageCountTV)
        languageTV = findViewById(R.id.languageTV)
        previewBTN = findViewById(R.id.previewBTN)
        buyLinkBTN = findViewById(R.id.buyLinkBTN)
        averageRatingRB = findViewById(R.id.averageRatingRB)
        maturityRatingTV = findViewById(R.id.maturityRatingTV)
        printTypeTV2 = findViewById(R.id.printTypeTV2)
        printTypeTV = findViewById(R.id.printTypeTV)
        heightTV = findViewById(R.id.heightTV)
        widthTV = findViewById(R.id.widthTV)
        thicknessTV = findViewById(R.id.thicknessTV)
        shimmer_view_container = findViewById(R.id.shimmer_view_container)
        layout_parent = findViewById(R.id.layout_parent)
        activeBookmark = findViewById(R.id.activeBookmark)
        inactiveBookmark = findViewById(R.id.inactiveBookmark)

        dialog = ProgressDialog(this@BookInfoActivity)

        val bundle = this.intent.extras
        if (bundle != null) {
            volume_id = bundle.getString("volume_id")
            bookmarked = bundle.getString("is_bookmarked")
            displayBookItem(volume_id)

            try {
                bookmark_id = bundle.getInt("bookmark_id")
            } catch (e: Exception) {
                Toast.makeText(
                    this@BookInfoActivity,
                    R.string.something_went_wrong,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        activeBookmark.setOnClickListener(this)
        inactiveBookmark.setOnClickListener(this)
    }

    private fun addToBookmark() {
        dbManager = DBManager(this)
        dbManager!!.open()
        db = DatabaseHelper(this)
        val volumeBooks = VolumeBooks(volume_id, true)
        db!!.addBookmark(volumeBooks)

        inactiveBookmark.visibility = View.GONE
        activeBookmark.visibility = View.VISIBLE
        Toast.makeText(
            this@BookInfoActivity,
            "$title has been added to bookmarks.",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun removeFromBookmark() {
        dbManager = DBManager(this)
        dbManager!!.open()
        db = DatabaseHelper(this)

        db?.removeBookmark(bookmark_id)
        inactiveBookmark.visibility = View.VISIBLE
        activeBookmark.visibility = View.GONE
        Toast.makeText(
            this@BookInfoActivity,
            "$title has been removed from bookmarks.",
            Toast.LENGTH_LONG
        ).show()
    }

    fun displayBookItem(id: String?) {
        singleItemCall = requestService.getBookItem(id)
        singleItemCall.enqueue(object : Callback<Item> {
            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                val item = response.body()
                val volume = response.body()?.volumeInfo
                val saleInfo = response.body()?.saleInfo
                if (response.isSuccessful) {
                    shimmer_view_container.visibility = View.GONE
                    layout_parent.visibility = View.VISIBLE

                    db = DatabaseHelper(this@BookInfoActivity)
                    list = db?.all ?: emptyList()

                    for (i in list.indices) {
                        if ((list[i].volumeId == volume_id)) {
                            activeBookmark.visibility = View.VISIBLE
                            inactiveBookmark.visibility = View.GONE
                            break
                        } else {
                            activeBookmark.visibility = View.GONE
                            inactiveBookmark.visibility = View.VISIBLE
                        }
                    }

                    title = volume?.title ?: ""
                    setTitle(volume?.title + "")
                    titleTV.text = volume?.title

                    languageTV.text = volume?.language?.uppercase(Locale.getDefault())

                    try {
                        publisherTV.text = volume?.publisher
                    } catch (e: Exception) {
                        publisherTV.setText(R.string.dash)
                    }

                    try {
                        publishedDateTV.text = volume?.publishedDate
                    } catch (e: Exception) {
                        publishedDateTV.setText(R.string.dash)
                    }

                    try {
                        pageCountTV.text = volume?.pageCount.toString() + " pages"
                    } catch (e: Exception) {
                        pageCountTV.setText(R.string.dash)
                    }

                    try {
                        noRatingPlaceholderTV.text =
                            volume?.averageRating.toString() + " avg rating — " + NumberFormatter.format(
                                volume?.ratingsCount?.toFloat() ?: 0.0f
                            ) + " ratings"
                    } catch (e: Exception) {
                        noRatingPlaceholderTV.setText(R.string.no_reviews)
                    }

                    var categories = ""
                    var authors = ""
                    var isbns = ""

                    try {
                        for (i in volume?.categories?.indices!!) {
                            categories = "" + volume.categories!![i] + "\n"
                            categoriesTV.append("" + categories)
                        }
                    } catch (e: Exception) {
                        categoriesTV.setText(R.string.dash)
                    }

                    try {
                        printTypeTV2.text = volume?.printType
                        printTypeTV.text = volume?.printType
                    } catch (e: Exception) {
                        printTypeTV2.setText(R.string.dash)
                        printTypeTV.setText(R.string.dash)
                    }

                    try {
                        var result = ""
                        //loop to get authors
                        if (volume != null) {
                            for (j in volume.authors?.indices!!) {
                                if (volume?.authors?.size == 1) {
                                    authors = "" + volume.authors!![j]
                                } else {
                                    authors = "" + volume.authors!![j] + ", "
                                }
                                ratingsTV.append("" + authors)
                            }
                        }

                        if (volume != null) {
                            if (volume.authors?.size ?: 0 == 1) {
                                result = ratingsTV.text.toString().trim { it <= ' ' }
                            } else {
                                result = ratingsTV.text.toString()
                                    .substring(0, ratingsTV.text.toString().length - 2)
                            }
                        }
                        authorTV.text = "By $result"
                    } catch (e: Exception) {
                        authorTV.setText(R.string.dash)
                    }

                    try {
                        if (volume != null) {
                            for (k in volume.industryIdentifiers?.indices!!) {
                                isbns = "" + volume.industryIdentifiers!![k].identifier + "\n"
                                isbnsTV.append("" + isbns)
                            }
                        }
                    } catch (e: Exception) {
                        isbnsTV.setText(R.string.dash)
                    }

                    try {
                        heightTV.text = volume?.dimension?.height ?: ""
                        widthTV.text = volume?.dimension?.width
                        thicknessTV.text = volume?.dimension?.thickness
                    } catch (e: Exception) {
                        heightTV.setText(R.string.dash)
                        widthTV.setText(R.string.dash)
                        thicknessTV.setText(R.string.dash)
                    }

                    try {
                        Glide.with(this@BookInfoActivity).load(volume?.imageLinks?.thumbnail)
                            .placeholder(R.color.colorShimmer).into((bookImageIV))
                    } catch (e: Exception) {
                        Glide.with(this@BookInfoActivity).load(Constant.N0_IMAGE_PLACEHOLDER)
                            .into((bookImageIV))
                    }

                    try {
                        maturityRatingTV.text = volume?.maturityRating
                    } catch (e: Exception) {
                        maturityRatingTV.setText(R.string.dash)
                    }
                }

                try {
                    previewBTN.text = "Read Ebook"
                    previewBTN.setOnClickListener(View.OnClickListener {
                        val intent = Intent(
                            Intent.ACTION_VIEW, Uri.parse(
                                item!!.accessInfo?.webReaderLink ?: ""
                            )
                        )
                        startActivity(intent)
                    })
                } catch (e: Exception) {
                    previewBTN.visibility = View.INVISIBLE
                }


                if ((item!!.saleInfo?.saleability == "FOR_SALE")) {
                    val decimalFormat = DecimalFormat("#,##0.00")

                    try {
                        val type = if (saleInfo?.isEbook == true) {
                            "eBook"
                        } else {
                            "Book"
                        }

                        if (saleInfo != null) {
                            buyLinkBTN.text = "$type ${saleInfo.retailPrice?.currencyCode?.let {
                                CurrencyConverter.convertCurrency(it)
                            }} ${decimalFormat.format(saleInfo.retailPrice?.amount)}"

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    buyLinkBTN.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(v: View) {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(saleInfo?.buyLink ?: ""))
                            startActivity(intent)
                        }
                    })
                } else {
                    buyLinkBTN.text = "Not For Sale"
                    buyLinkBTN.background = resources.getDrawable(R.drawable.button_bg_disabled)
                }

                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        descriptionTV.text =
                            Html.fromHtml(volume?.description ?: "", Html.FROM_HTML_MODE_COMPACT)
                    } else {
                        descriptionTV.text = Html.fromHtml(volume?.description ?: "")
                    }
                } catch (e: Exception) {
                    descriptionTV.setText(R.string.dash)
                }
            }

            override fun onFailure(call: Call<Item>, t: Throwable) {
            }
        })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.activeBookmark -> removeFromBookmark()
            R.id.inactiveBookmark -> addToBookmark()
        }
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}
