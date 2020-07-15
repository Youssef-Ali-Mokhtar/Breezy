package com.example.breezy.ui.home.home_fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.breezy.R
import com.example.breezy.model.remote.pojo.contents.ArticlesItem
import com.example.breezy.model.remote.pojo.titles.SourcesItem
import com.example.breezy.ui.bookmarks.bookmarks_fragment.BookmarksViewModel
import com.example.breezy.ui.home.details.HomeDetailsActivity
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_home.*

const val API_KEY = "ca836b90f3024f56b0d0b7abfde07517"
class HomeFragment : Fragment(){

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var bookmarkViewModel: BookmarksViewModel
    companion object{
        public var shit = "Nothing"
        private var homeAdapter = HomeAdapter()
        private var category = ""
        private var country = ""
        private var language = ""
        private var numberOfTabs = 0
    }
    private lateinit var recyclerView : RecyclerView
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        bookmarkViewModel = ViewModelProvider(this).get(BookmarksViewModel::class.java)
        recyclerView = view.findViewById(R.id.recycler_view_id)
        recyclerView.adapter = homeAdapter

        setRemoteObservers()
        setLocalObservers()
        homeViewModel.getNewsSources(API_KEY, "", "", "")
        homeAdapter.clickData(object : HomeAdapter.OnItemClickListener{
            override fun onItemClick(position : Int) {
                val intent = Intent(activity, HomeDetailsActivity::class.java)
                intent.putExtra("item_position", position)
                startActivity(intent)
            }

        })

        homeAdapter.longClickData(object : HomeAdapter.OnItemLongClickListener{
            override fun onItemLongClick(item : ArticlesItem) {
                shit = item.title
                activity?.let { bookmarkViewModel.addBookmark(item, it) }

              //  activity?.let { addBookmark(item, it) }
            }

        })

    }

    //Setting up the tablayout
    private fun setupTablayout(newsTitles : MutableList<SourcesItem>){
        for(item in newsTitles){
            val tabItem = tab_layout_id.newTab()
            tabItem.text = item.name
            tabItem.tag = item.id
            tab_layout_id.addTab(tabItem)
        }
        tab_layout_id.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab) {
                homeViewModel.getNewsContent(API_KEY, tab.tag.toString())
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                homeViewModel.getNewsContent(API_KEY, tab.tag.toString())
            }

        })
        tab_layout_id.getTabAt(0)?.select()
    }

    private fun setRemoteObservers(){
        homeViewModel.homeTitlesMutableLiveData.observe(viewLifecycleOwner, Observer {
            setupTablayout(it)
            numberOfTabs = it.size
        })
        homeViewModel.homeContentMutableLiveData.observe(viewLifecycleOwner, Observer {
            homeAdapter.swapData(it)
        })

        homeViewModel.homeErrorMutableLiveData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity,it,Toast.LENGTH_LONG).show()
        })

    }

    private fun setLocalObservers() {
        bookmarkViewModel.addBookmarkMutableLiveData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity,it,Toast.LENGTH_LONG).show()
        })
    }


    public fun getItem(position : Int) : ArticlesItem{
        return homeAdapter.getItem(position)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.filter_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.search){
            search()
        }else if(item.itemId == R.id.category_default){
            category = ""
        }else if(item.itemId == R.id.category_business){
            category = "business"
        }else if(item.itemId == R.id.category_entertainment){
            category = "entertainment"
        }else if(item.itemId == R.id.category_general){
            category = "general"
        }else if(item.itemId == R.id.category_health){
            category = "health"
        }else if(item.itemId == R.id.category_science){
            category = "science"
        }else if(item.itemId == R.id.category_sports){
            category = "sports"
        }else if(item.itemId == R.id.category_technology){
            category = "technology"
        }else if(item.itemId == R.id.language_default){
            language = ""
        }else if(item.itemId == R.id.language_arabic){
            language = "ar"
        }else if(item.itemId == R.id.language_english){
            language = "en"
        }else if(item.itemId == R.id.language_french){
            language = "fr"
        }else if(item.itemId == R.id.language_german){
            language = "de"
        }else if(item.itemId == R.id.language_spanish){
            language = "es"
        }else if(item.itemId == R.id.country_default){
            country = ""
        }else if(item.itemId == R.id.country_egypt){
            country = "eg"
        }else if(item.itemId == R.id.country_canada){
            country = "ca"
        }else if(item.itemId == R.id.country_france){
            country = "fr"
        }else if(item.itemId == R.id.country_germany){
            country = "de"
        }else if(item.itemId == R.id.country_usa){
            country = "us"
        }
        return super.onOptionsItemSelected(item)
    }
//    tab_layout_id.removeAllTabs()
//    homeAdapter.destroyData()
//    recyclerView.removeAllViews()
//    homeViewModel.getNewsSources(API_KEY, category, country, language)
    private fun search() {
        homeAdapter.destroyData()
        recyclerView.removeAllViews()
        tab_layout_id.removeAllTabs()
        homeViewModel.getNewsSources(API_KEY, category, country, language)
    }




}