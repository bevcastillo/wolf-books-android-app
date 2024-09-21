package com.bevstudio.wolfbooksapp.view.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.View.OnClickListener
import android.view.View.OnTouchListener
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bevstudio.wolfbooksapp.R
import com.bevstudio.wolfbooksapp.adapters.SearchResultsRecyclerviewAdapter
import com.bevstudio.wolfbooksapp.model.api.Books
import com.bevstudio.wolfbooksapp.model.api.Item
import com.bevstudio.wolfbooksapp.request.api.RequestService
import com.bevstudio.wolfbooksapp.request.api.RetrofitClass
import com.bevstudio.wolfbooksapp.vendor.EndlessRecyclerViewScrollListener
import com.bevstudio.wolfbooksapp.vendor.InternetConnection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class SearchActivity : AppCompatActivity(), OnClickListener,
    EndlessRecyclerViewScrollListener.Callback {
    private lateinit var searchQueryET: EditText
    private lateinit var micIV: ImageView
    private lateinit var clearBTN: ImageView
    private lateinit var micActive: ImageView
    private lateinit var sortRelevanceTV: TextView
    private lateinit var sortNewest: TextView
    private lateinit var headerTV: TextView
    private lateinit var textTV: TextView
    private lateinit var placeholderTitleTV: TextView
    private lateinit var placeholderTextTV: TextView
    private lateinit var searchResultsRV: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var progressBar1: ProgressBar
    private lateinit var searchAdapter: SearchResultsRecyclerviewAdapter
    private lateinit var errorBTN: Button
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var noConnectionLL: View
    private lateinit var search_keyword: String
    private var orderBy: String = "relevance"
    private var requestService: RequestService? = null
    private var searchResultsCall: Call<Books>? = null
    private var endlessRecyclerViewScrollListener: EndlessRecyclerViewScrollListener? = null
    private var page: Int = 1
    private var speechRecognizer: SpeechRecognizer? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        title = " "
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        searchQueryET = findViewById(R.id.searchQueryET)
        micIV = findViewById(R.id.micIV)
        clearBTN = findViewById(R.id.clearBTN)
        sortRelevanceTV = findViewById(R.id.sortRelevanceTV)
        sortNewest = findViewById(R.id.sortNewest)
        searchResultsRV = findViewById(R.id.searchResultsRV)
        progressBar = findViewById(R.id.progressBar)
        noConnectionLL = findViewById(R.id.noConnectionLL)
        headerTV = findViewById(R.id.headerTV)
        textTV = findViewById(R.id.textTV)
        errorBTN = findViewById(R.id.errorBTN)
        placeholderTitleTV = findViewById(R.id.placeholderTitleTV)
        placeholderTextTV = findViewById(R.id.placeholderTextTV)
        micActive = findViewById(R.id.micActive)
        progressBar1 = findViewById(R.id.progressBar1)

        requestService = RetrofitClass.newBooksAPIInstance

        InternetConnection.isInternetConnected(this, noConnectionLL, searchResultsRV)
        headerTV.setText(R.string.no_internet_header)
        textTV.setText(R.string.no_internet_text)
        errorBTN.visibility = VISIBLE
        errorBTN.setText(R.string.try_again)

        micIV.setOnClickListener(this)
        clearBTN.setOnClickListener(this)
        sortRelevanceTV.setOnClickListener(this)
        sortNewest.setOnClickListener(this)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            checkPermission()
        }
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this@SearchActivity)
        val speechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please speak")

        speechRecognizer?.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle) {
            }

            override fun onBeginningOfSpeech() {
                searchQueryET.setText("")
                searchQueryET.setHint("Listening ...")
            }

            override fun onRmsChanged(rmsdB: Float) {
            }

            override fun onBufferReceived(buffer: ByteArray) {
            }

            override fun onEndOfSpeech() {
            }

            override fun onError(error: Int) {
                micIV.setImageResource(R.drawable.ic_mic)
                searchQueryET.setHint("Search for a book title, author, etc.")
            }

            override fun onResults(results: Bundle) {
                micIV.setImageResource(R.drawable.ic_mic)
                val data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                searchQueryET.setText(data!![0])
            }

            override fun onPartialResults(partialResults: Bundle) {
            }

            override fun onEvent(eventType: Int, params: Bundle) {
            }
        })

        micIV.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                speechRecognizer?.stopListening()
            }
            if (event.action == MotionEvent.ACTION_DOWN) {
                micIV.setImageResource(R.drawable.ic_mic_dark)
                speechRecognizer?.startListening(speechIntent)
            }
            false
        }

        searchQueryET.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search_keyword = searchQueryET.getText().toString().trim { it <= ' ' }
                if (searchQueryET.getText().toString().trim { it <= ' ' }.isNotEmpty()) {
                    loadRelevantItems(page)
                    placeholderTitleTV.visibility = GONE
                    placeholderTextTV.visibility = GONE
                    searchResultsRV.visibility = GONE
                    progressBar.visibility = GONE
                } else {
                    searchResultsRV.visibility = GONE
                    progressBar.visibility = GONE
                    placeholderTitleTV.visibility = VISIBLE
                    placeholderTextTV.visibility = VISIBLE
                }
            }
            false
        }

        searchQueryET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                clearBTN.setVisibility(VISIBLE)
                micIV.setVisibility(GONE)
            }

            override fun afterTextChanged(s: Editable) {
                search_keyword = searchQueryET.getText().toString().trim { it <= ' ' }
                if (s.length > 0 && (!searchQueryET.getText().toString().trim { it <= ' ' }
                        .isEmpty())) {
                    loadRelevantItems(page)
                } else if (s.length == 0 && searchQueryET.getText().toString().trim { it <= ' ' }
                        .isEmpty()) {
                    clearBTN.visibility = GONE
                    micIV.visibility = VISIBLE
                    placeholderTitleTV.visibility = VISIBLE
                    placeholderTextTV.visibility = VISIBLE
                    searchResultsRV.visibility = GONE
                    progressBar.visibility = GONE
                } else {
                    clearBTN.visibility = GONE
                    micIV.visibility = VISIBLE
                    placeholderTitleTV.visibility = GONE
                    placeholderTextTV.visibility = GONE
                    searchResultsRV.visibility = GONE
                    progressBar.visibility = GONE
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer!!.destroy()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RecordAudioRequestCode && grantResults.size > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@SearchActivity, "Permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this@SearchActivity,
                    "Permission is required to continue using mic",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun checkPermission() {
        ActivityCompat.requestPermissions(
            this@SearchActivity,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            RecordAudioRequestCode
        )
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

    private fun callSearchResults(orderBy: String, index: Int) {
        val finalQuery = search_keyword.replace(" ", "+")
        searchResultsCall = requestService?.getSearchResults(finalQuery, index, orderBy, 40)

        searchResultsCall?.enqueue(object : Callback<Books?> {
            override fun onResponse(call: Call<Books?>, response: Response<Books?>) {
                if (response.isSuccessful || response.body() != null) {
                    placeholderTitleTV.visibility = GONE
                    placeholderTextTV.visibility = GONE
                    progressBar.visibility = GONE
                    searchResultsRV.visibility = VISIBLE
                    progressBar.visibility = GONE
                    progressBar1.visibility = GONE
                    for (i in response.body()!!.items?.indices!!) {
                        response.body()!!.items?.let { setUpSearchResultslist(it) }
                    }
                }

                if (response.code() != 200) {
                    progressBar.visibility = GONE
                }
            }

            override fun onFailure(call: Call<Books?>, t: Throwable) {
                progressBar.visibility = GONE
                placeholderTitleTV.visibility = GONE
                placeholderTextTV.setText(R.string.something_went_wrong)
                placeholderTextTV.visibility = VISIBLE
            }
        })
    }

    private fun setUpSearchResultslist(itemList: List<Item>) {
        searchAdapter = SearchResultsRecyclerviewAdapter(this@SearchActivity, itemList)
        layoutManager =
            LinearLayoutManager(this@SearchActivity, LinearLayoutManager.VERTICAL, false)
        endlessRecyclerViewScrollListener = EndlessRecyclerViewScrollListener(layoutManager, this)
        searchResultsRV.layoutManager = layoutManager
        searchResultsRV.adapter = searchAdapter
    }

    fun loadRelevantItems(page: Int) {
        sortRelevanceTV.alpha = 0.9.toFloat()
        sortNewest.alpha = 0.5.toFloat()
        searchResultsRV.visibility = GONE
        progressBar.visibility = VISIBLE
        callSearchResults("relevance", page)
    }

    fun loadNewestItems(page: Int) {
        sortRelevanceTV.alpha = 0.5.toFloat()
        sortNewest.alpha = 0.9.toFloat()
        searchResultsRV.visibility = GONE
        progressBar.visibility = VISIBLE
        callSearchResults("newest", page)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.micIV -> {}
            R.id.clearBTN -> {
                search_keyword = ""
                searchQueryET.setText("")
                progressBar.visibility = GONE
            }

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

    override fun onLoadMore(page: Int, totalItemsCount: Int) {
        var page = page
        page++
        callSearchResults("relevance", page)
        Toast.makeText(this@SearchActivity, "hello", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val RecordAudioRequestCode: Int = 1
    }
}