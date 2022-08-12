package com.buygrup.bookfinder.presentation.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.buygrup.bookfinder.R
import com.buygrup.bookfinder.data.model.BookCategory
import com.buygrup.bookfinder.presentation.fragment.ShowBookFragment
import com.buygrup.bookfinder.presentation.viewModel.ShowBookViewModel
import java.lang.NullPointerException

class BookCategoryAdapter(
    private val context: Context,
    private val viewModel: ShowBookViewModel,
    private val lifecycleOwner: LifecycleOwner,
    private val adapter: ShowTopicWiseBookAdapter,
    private val pbLower: ProgressBar
) : RecyclerView.Adapter<BookCategoryAdapter.BookCategoryViewHolder>() {
    private val list: ArrayList<String> = ArrayList()

    inner class BookCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtCategoryTopic: TextView = itemView.findViewById(R.id.txtCategoryTopic)
        val llCategory: LinearLayout = itemView.findViewById(R.id.llCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookCategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_category_list_item, parent, false)
        return BookCategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookCategoryViewHolder, position: Int) {
        holder.txtCategoryTopic.text = list[position]
        holder.llCategory.setOnClickListener {
            Toast.makeText(context, "Searching Books on ${list[position]}", Toast.LENGTH_SHORT).show()
            pbLower.visibility = View.VISIBLE
            // getting books remotely
            viewModel.getTopicWiseBooks(holder.txtCategoryTopic.text.toString())
            viewModel.getBookTopicResponse.observe(lifecycleOwner) { showBookResponse ->

                ShowBookFragment.list.clear()
                try {
                    ShowBookFragment.list.addAll(showBookResponse?.items!!)
                    Log.d("chkStatus", showBookResponse?.items!!.toString())
                } catch (e: NullPointerException) {
                    Log.d("chkStatus", "Item not fetched")
                }

                if(ShowBookFragment.list.isNotEmpty()) {
                    pbLower.visibility = View.GONE
                    adapter.updateList(ShowBookFragment.list)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(newList: List<String>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}