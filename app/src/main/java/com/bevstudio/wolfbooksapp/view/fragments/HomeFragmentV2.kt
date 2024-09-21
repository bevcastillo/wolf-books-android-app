package com.bevstudio.wolfbooksapp.view.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
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
import com.bevstudio.wolfbooksapp.adapters.CategoriesRecyclerviewAdapter
import com.bevstudio.wolfbooksapp.databinding.FragmentHomeV2Binding
import com.bevstudio.wolfbooksapp.model.api.Books
import com.bevstudio.wolfbooksapp.model.api.Item
import com.bevstudio.wolfbooksapp.request.api.RequestService
import com.bevstudio.wolfbooksapp.request.api.RetrofitClass
import com.bevstudio.wolfbooksapp.vendor.InternetConnection
import com.bevstudio.wolfbooksapp.view.activity.CategoriesListActivity
import com.facebook.shimmer.ShimmerFrameLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragmentV2 : Fragment(), OnRefreshListener, View.OnClickListener {
    private lateinit var homeSRL: SwipeRefreshLayout
    private lateinit var romanceShimmer: ShimmerFrameLayout
    private lateinit var thrillerShimmer: ShimmerFrameLayout
    private lateinit var fictionShimmer: ShimmerFrameLayout
    private lateinit var childrenShimmer: ShimmerFrameLayout
    private lateinit var selfHelpShimmer: ShimmerFrameLayout
    private lateinit var romanceErr: TextView
    private lateinit var thrillerErr: TextView
    private lateinit var fictionErr: TextView
    private lateinit var headerTV: TextView
    private lateinit var textTV: TextView
    private lateinit var errorBTN: Button
    private lateinit var artsBTN: Button
    private lateinit var bioBTN: Button
    private lateinit var businessBTN: Button
    private lateinit var childrensBTN: Button
    private lateinit var computers: Button
    private lateinit var educBTN: Button
    private lateinit var religionBTN: Button
    private lateinit var romanceBTN: Button
    private lateinit var travelBTN: Button
    private lateinit var comicsBTN: Button
    private lateinit var cookingBTN: Button
    private lateinit var fictionBTN: Button
    private lateinit var foreignBTN: Button
    private lateinit var healthBTN: Button
    private lateinit var historyBTN: Button
    private lateinit var parentingBTN: Button
    private lateinit var scienceBTN: Button
    private lateinit var fantasy: Button
    private lateinit var selfHelpBTN: Button
    private lateinit var fictionRV: RecyclerView
    private lateinit var romanceRV: RecyclerView
    private lateinit var thrillerRV: RecyclerView
    private lateinit var childrensRV: RecyclerView
    private lateinit var selfHelpRV: RecyclerView
    private lateinit var categoriesAdapter: CategoriesRecyclerviewAdapter
    private lateinit var fictionLM: LinearLayoutManager
    private lateinit var romanceLM: LinearLayoutManager
    private lateinit var thrillerLM: LinearLayoutManager
    private lateinit var selfhelpLM: LinearLayoutManager
    private lateinit var noConnectionLL: View
    private lateinit var layout_parent: View
    private lateinit var requestService: RequestService
    private lateinit var fictionCall: Call<Books>
    private lateinit var motivationCall: Call<Books>
    private lateinit var thrillerCall: Call<Books>
    private lateinit var selfHelpCall: Call<Books>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private var binding: FragmentHomeV2Binding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeV2Binding.inflate(inflater, container, false)
        val view: View = binding!!.root

        activity?.title = "Welcome to Wolf Books"

        homeSRL = view.findViewById(R.id.homeSRL)
        fictionRV = view.findViewById(R.id.fictionRV)
        romanceRV = view.findViewById(R.id.romanceRV)
        thrillerRV = view.findViewById(R.id.thrillerRV)
        childrensRV = view.findViewById(R.id.childrensRV)
        romanceShimmer = view.findViewById(R.id.romanceShimmer)
        thrillerShimmer = view.findViewById(R.id.thrillerShimmer)
        fictionShimmer = view.findViewById(R.id.fictionShimmer)
        childrenShimmer = view.findViewById(R.id.childrenShimmer)
        noConnectionLL = view.findViewById(R.id.noConnectionLL)
        layout_parent = view.findViewById(R.id.layout_parent)
        headerTV = view.findViewById(R.id.headerTV)
        textTV = view.findViewById(R.id.textTV)
        errorBTN = view.findViewById(R.id.errorBTN)
        selfHelpShimmer = view.findViewById(R.id.selfHelpShimmer)
        selfHelpRV = view.findViewById(R.id.selfHelpRV)
        artsBTN = view.findViewById(R.id.artsBTN)
        bioBTN = view.findViewById(R.id.bioBTN)
        businessBTN = view.findViewById(R.id.businessBTN)
        childrensBTN = view.findViewById(R.id.childrensBTN)
        computers = view.findViewById(R.id.computers)
        educBTN = view.findViewById(R.id.educBTN)
        religionBTN = view.findViewById(R.id.religionBTN)
        romanceBTN = view.findViewById(R.id.romanceBTN)
        travelBTN = view.findViewById(R.id.travelBTN)
        comicsBTN = view.findViewById(R.id.comicsBTN)
        cookingBTN = view.findViewById(R.id.cookingBTN)
        fictionBTN = view.findViewById(R.id.fictionBTN)
        foreignBTN = view.findViewById(R.id.foreignBTN)
        healthBTN = view.findViewById(R.id.healthBTN)
        historyBTN = view.findViewById(R.id.historyBTN)
        parentingBTN = view.findViewById(R.id.parentingBTN)
        scienceBTN = view.findViewById(R.id.scienceBTN)
        fantasy = view.findViewById(R.id.fantasy)
        selfHelpBTN = view.findViewById(R.id.selfHelpBTN)

        homeSRL.setColorSchemeColors(resources.getColor(R.color.colorPrimary))
        homeSRL.setOnRefreshListener(this)
        requestService = RetrofitClass.aPIInstance

        InternetConnection.isInternetConnected(requireContext(), noConnectionLL, layout_parent)
        headerTV.setText(R.string.no_internet_header)
        textTV.setText(R.string.no_internet_text)
        errorBTN.visibility = View.VISIBLE
        errorBTN.setText(R.string.try_again)

        errorBTN.setOnClickListener(this)
        artsBTN.setOnClickListener(this)
        bioBTN.setOnClickListener(this)
        businessBTN.setOnClickListener(this)
        childrensBTN.setOnClickListener(this)
        computers.setOnClickListener(this)
        educBTN.setOnClickListener(this)
        religionBTN.setOnClickListener(this)
        romanceBTN.setOnClickListener(this)
        travelBTN.setOnClickListener(this)
        comicsBTN.setOnClickListener(this)
        cookingBTN.setOnClickListener(this)
        fictionBTN.setOnClickListener(this)
        foreignBTN.setOnClickListener(this)
        healthBTN.setOnClickListener(this)
        historyBTN.setOnClickListener(this)
        parentingBTN.setOnClickListener(this)
        scienceBTN.setOnClickListener(this)
        fantasy.setOnClickListener(this)
        selfHelpBTN.setOnClickListener(this)

        callFiction()
        callRomance()
        callThriller()
        callSelfHelp()

        return view
    }

    private fun setupFictionList(itemList: List<Item>) {
        categoriesAdapter = CategoriesRecyclerviewAdapter(requireContext(), itemList)
        fictionLM = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        fictionRV.layoutManager = fictionLM
        fictionRV.adapter = categoriesAdapter
    }

    private fun setupRomanceList(itemList: List<Item>) {
        categoriesAdapter = CategoriesRecyclerviewAdapter(requireContext(), itemList)
        romanceLM = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        romanceRV.layoutManager = romanceLM
        romanceRV.adapter = categoriesAdapter
    }

    private fun setUpThrillerList(itemList: List<Item>) {
        categoriesAdapter = CategoriesRecyclerviewAdapter(requireContext(), itemList)
        thrillerLM = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        thrillerRV.layoutManager = thrillerLM
        thrillerRV.adapter = categoriesAdapter
    }

    private fun setUpSelfHelp(itemList: List<Item>) {
        categoriesAdapter = CategoriesRecyclerviewAdapter(requireContext(), itemList)
        selfhelpLM = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        selfHelpRV.layoutManager = selfhelpLM
        selfHelpRV.adapter = categoriesAdapter
    }

    private fun callSelfHelp() {
        selfHelpCall =
            requestService.getCategories("categories:self-help", "ebooks", "relevance", 40)
        selfHelpCall.enqueue(object : Callback<Books> {
            override fun onResponse(call: Call<Books>, response: Response<Books>) {
                selfHelpRV.visibility = View.VISIBLE
                selfHelpShimmer.visibility = View.GONE
                if (response.isSuccessful) {
                    for (i in response.body()!!.items?.indices!!) {
                        response.body()!!.items?.let { setUpSelfHelp(it) }
                    }
                }
            }

            override fun onFailure(call: Call<Books>, t: Throwable) {
                selfHelpRV.visibility = View.GONE
                selfHelpShimmer.visibility = View.GONE
            }
        })
    }

    private fun callFiction() {
        fictionCall =
            requestService.getCategories("categories:young+fiction", "ebooks", "relevance", 40)
        fictionCall.enqueue(object : Callback<Books> {
            override fun onResponse(call: Call<Books>, response: Response<Books>) {
                fictionRV.visibility = View.VISIBLE
                fictionShimmer.visibility = View.GONE
                if (response.isSuccessful) {
                    for (i in response.body()?.items?.indices!!) {
                        response.body()!!.items?.let { setupFictionList(it) }
                    }
                }
            }

            override fun onFailure(call: Call<Books>, t: Throwable) {
                fictionRV.visibility = View.GONE
                fictionShimmer.visibility = View.GONE
                fictionErr.visibility = View.VISIBLE
                fictionErr.text = t.message
            }
        })
    }

    private fun callRomance() {
        motivationCall =
            requestService!!.getCategories("categories:romance", "ebooks", "relevance", 40)
        motivationCall.enqueue(object : Callback<Books> {
            override fun onResponse(call: Call<Books>, response: Response<Books>) {
                romanceRV.visibility = View.VISIBLE
                romanceShimmer.visibility = View.GONE
                homeSRL.isRefreshing = false
                if (response.isSuccessful) {
                    for (i in response.body()!!.items?.indices!!) {
                        response.body()!!.items?.let { setupRomanceList(it) }
                    }
                }
            }

            override fun onFailure(call: Call<Books>, t: Throwable) {
                romanceRV.visibility = View.GONE
                romanceShimmer.visibility = View.GONE
                romanceErr.visibility = View.VISIBLE
                romanceErr.text = t.message
            }
        })
    }

    private fun callThriller() {
        thrillerCall = requestService.getCategories(
            "categories:thriller+horror+suspense",
            "ebooks",
            "relevance",
            40
        )
        thrillerCall.enqueue(object : Callback<Books> {
            override fun onResponse(call: Call<Books>, response: Response<Books>) {
                thrillerRV.visibility = View.VISIBLE
                thrillerShimmer.visibility = View.GONE
                homeSRL.isRefreshing = false

                if (response.isSuccessful) {
                    for (i in response.body()!!.items?.indices!!) {
                        response.body()!!.items?.let { setUpThrillerList(it) }
                    }
                }
            }

            override fun onFailure(call: Call<Books>, t: Throwable) {
                thrillerRV.visibility = View.GONE
                thrillerShimmer.visibility = View.GONE
                thrillerErr.visibility = View.VISIBLE
                thrillerErr.text = t.message
            }
        })
    }

    override fun onRefresh() {
        hideRecyclerviews()
        showShimmerLayout()
        callFiction()
        callRomance()
        callThriller()
        callSelfHelp()
    }

    fun hideRecyclerviews() {
        fictionRV.visibility = View.GONE
        romanceRV.visibility = View.GONE
        thrillerRV.visibility = View.GONE
        selfHelpRV.visibility = View.GONE
    }

    fun showShimmerLayout() {
        romanceShimmer.visibility = View.VISIBLE
        thrillerShimmer.visibility = View.VISIBLE
        fictionShimmer.visibility = View.VISIBLE
        selfHelpShimmer.visibility = View.VISIBLE
    }

    fun intentToViewCategory(context: Context, category_query: String?, category_name: String?) {
        val intent = Intent(context, CategoriesListActivity::class.java)
        intent.putExtra("category_query", category_query)
        intent.putExtra("category_name", category_name)
        context.startActivity(intent)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.errorBTN -> {
                InternetConnection.isInternetConnected(v.context, noConnectionLL, layout_parent)
                onRefresh()
            }

            R.id.artsBTN -> intentToViewCategory(
                v.context,
                "categories:music",
                "Arts & entertainment"
            )

            R.id.bioBTN -> intentToViewCategory(
                v.context,
                "categories:biography&autobiography",
                "Biography & memoirs"
            )

            R.id.businessBTN -> intentToViewCategory(
                v.context,
                "categories:business, investing+business+investing",
                "Business & investing"
            )

            R.id.childrensBTN -> intentToViewCategory(
                v.context,
                "categories:childrens",
                "Children's"
            )

            R.id.computers -> intentToViewCategory(
                v.context,
                "categories:computers+technology, computers, technology",
                "Computers & technology"
            )

            R.id.educBTN -> intentToViewCategory(v.context, "categories:education", "Education")
            R.id.religionBTN -> intentToViewCategory(
                v.context,
                "categories:religion",
                "Religion & spirituality"
            )

            R.id.romanceBTN -> intentToViewCategory(v.context, "categories:romance", "Romance")
            R.id.travelBTN -> intentToViewCategory(v.context, "categories:travel", "Travel")
            R.id.comicsBTN -> intentToViewCategory(
                v.context,
                "categories:comics&graphic novels",
                "Comics"
            )

            R.id.cookingBTN -> intentToViewCategory(
                v.context,
                "categories:cooking",
                "Cooking, food & wine"
            )

            R.id.fictionBTN -> intentToViewCategory(
                v.context,
                "categories:fiction",
                "Fiction & literary collections"
            )

            R.id.foreignBTN -> intentToViewCategory(
                v.context,
                "categories:language",
                "Foreign language & study aids"
            )

            R.id.healthBTN -> intentToViewCategory(
                v.context,
                "categories:health",
                "Health, mind & body"
            )

            R.id.historyBTN -> intentToViewCategory(v.context, "categories:history", "History")
            R.id.parentingBTN -> intentToViewCategory(
                v.context,
                "categories:parenting",
                "Parenting & families"
            )

            R.id.scienceBTN -> intentToViewCategory(
                v.context,
                "categories:science,math",
                "Science & Math"
            )

            R.id.fantasy -> intentToViewCategory(
                v.context,
                "categories:science fiction+fantasy",
                "Sci-fi & fantasy"
            )

            R.id.selfHelpBTN -> intentToViewCategory(v.context, "categories:self-help", "Self-help")
        }
    }
}