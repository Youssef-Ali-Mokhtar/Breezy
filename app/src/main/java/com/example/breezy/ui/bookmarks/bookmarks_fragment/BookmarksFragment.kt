package com.example.breezy.ui.bookmarks.bookmarks_fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.breezy.R
import com.example.breezy.model.local.pojo.Bookmark
import com.example.breezy.model.remote.pojo.contents.ArticlesItem
import com.example.breezy.ui.bookmarks.bookmarks_details.BookmarksDetailsActivity
import com.example.breezy.ui.home.details.HomeDetailsActivity
import com.example.breezy.ui.home.home_fragment.HomeFragment
import kotlinx.android.synthetic.main.fragment_bookmarks.*


class BookmarksFragment : Fragment() {

    private lateinit var bookmarkViewModel: BookmarksViewModel
    private lateinit var recyclerView : RecyclerView
    companion object{
        private var bookmarksAdapter =
            BookmarksAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_bookmarks, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookmarkViewModel = ViewModelProvider(this).get(BookmarksViewModel::class.java)
        recyclerView = view.findViewById(R.id.bookmark_recycler_view_id)
        recyclerView.adapter = bookmarksAdapter
        bookmarksAdapter.clickData(object : BookmarksAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(activity, BookmarksDetailsActivity::class.java)
                intent.putExtra("item_position", position)
                startActivity(intent)
            }

        })
        setLocalObserver()
        activity?.let { bookmarkViewModel.getAllBookmarks(it) }
        ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(bookmark_recycler_view_id)

    }

    private fun setLocalObserver(){
        bookmarkViewModel.getAllBookmarksMutableLiveData.observe(viewLifecycleOwner, Observer {
            bookmarksAdapter.swapData(it)
        })
        bookmarkViewModel.deleteBookmarkMutableLiveData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity,it,Toast.LENGTH_LONG).show()
        })
    }

    val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            //bookmarksAdapter.getItemById(viewHolder.adapterPosition).id

            activity?.let {
                bookmarkViewModel.deleteBookmark(bookmarksAdapter.getItemById(viewHolder.adapterPosition).id, it)
            }
            bookmarksAdapter.getAllData().removeAt(viewHolder.adapterPosition)
            bookmarksAdapter.notifyDataSetChanged()

        }

    }

    public fun getItem(position : Int) : Bookmark {
        return bookmarksAdapter.getItemById(position)
    }

}

