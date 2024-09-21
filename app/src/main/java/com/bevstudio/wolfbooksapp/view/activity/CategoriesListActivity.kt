package com.bevstudio.wolfbooksapp.view.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bevstudio.wolfbooksapp.R
import com.bevstudio.wolfbooksapp.adapters.AuthorResultsRecyclerviewAdapter
import com.bevstudio.wolfbooksapp.model.api.Books
import com.bevstudio.wolfbooksapp.model.api.Item
import com.bevstudio.wolfbooksapp.request.api.RequestService
import com.bevstudio.wolfbooksapp.request.api.RetrofitClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesListActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var sortRelevanceTV: TextView
    private lateinit var sortNewest: TextView
    private lateinit var authorListRV: RecyclerView
    var orderBy: String = "relevance"
    var category_name: String? = ""
    var category_query: String? = ""
    var page: Int = 40
    private lateinit var searchAdapter: AuthorResultsRecyclerviewAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var progressBar: ProgressBar
    var requestService: RequestService? = null
    var authorResultsCall: Call<Books>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_author_list)

        sortRelevanceTV = findViewById(R.id.sortRelevanceTV)
        sortNewest = findViewById(R.id.sortNewest)
        authorListRV = findViewById(R.id.authorListRV)
        progressBar = findViewById(R.id.progressBar)

        requestService = RetrofitClass.newBooksAPIInstance
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val bundle = this.intent.extras
        if (bundle != null) {
            category_query = bundle.getString("category_query")
            category_name = bundle.getString("category_name")
            title = category_name

            loadRelevantItems(page)
        }

        sortRelevanceTV?.setOnClickListener(this)
        sortNewest.setOnClickListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpCategoriesList(itemList: List<Item>) {
        searchAdapter = AuthorResultsRecyclerviewAdapter(this@CategoriesListActivity, itemList)
        layoutManager =
            LinearLayoutManager(this@CategoriesListActivity, LinearLayoutManager.VERTICAL, false)
        authorListRV.layoutManager = layoutManager
        authorListRV.adapter = searchAdapter
    }

    private fun callCategoriesList(orderBy: String, page: Int) {
        authorResultsCall = requestService!!.getSearchResults(category_query, 0, orderBy, 40)
        authorResultsCall?.enqueue(object : Callback<Books> {
            override fun onResponse(call: Call<Books>, response: Response<Books>) {
                authorListRV!!.visibility = View.VISIBLE
                progressBar!!.visibility = View.GONE
                if (response.isSuccessful) {
                    for (i in response.body()!!.items?.indices!!) {
                        response.body()!!.items?.let { setUpCategoriesList(it) }
                    }
                }
            }

            override fun onFailure(call: Call<Books>, t: Throwable) {
            }
        })
    }

    fun loadRelevantItems(page: Int) {
        sortRelevanceTV.alpha = 0.9.toFloat()
        sortNewest.alpha = 0.5.toFloat()
        progressBar.visibility = View.VISIBLE
        authorListRV.visibility = View.GONE
        callCategoriesList("relevance", page)
    }

    fun loadNewestItems(page: Int) {
        sortRelevanceTV.alpha = 0.5.toFloat()
        sortNewest.alpha = 0.9.toFloat()
        progressBar.visibility = View.VISIBLE
        authorListRV.visibility = View.GONE
        callCategoriesList("newest", page)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.sortRelevanceTV -> {
                loadRelevantItems(page)
                orderBy = "relevance"
            }

            R.id.sortNewest -> {
                loadNewestItems(page)
                orderBy = "newest"
            }
        }
    }
}