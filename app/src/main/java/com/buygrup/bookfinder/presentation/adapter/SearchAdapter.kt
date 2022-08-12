package com.buygrup.bookfinder.presentation.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.buygrup.bookfinder.R
import com.buygrup.bookfinder.data.model.ItemsItem

class SearchAdapter(private val context: Context, private val navController: NavController): RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    private val list: ArrayList<ItemsItem?> = ArrayList()
    inner class SearchViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imgBook: ImageView = itemView.findViewById(R.id.imgBook)
        val txtBookName: TextView = itemView.findViewById(R.id.txtBookName)
        val txtAuthor: TextView = itemView.findViewById(R.id.txtAuthor)
        val txtBookPage: TextView = itemView.findViewById(R.id.txtBookPage)
        val txtPublisher: TextView = itemView.findViewById(R.id.txtPublisher)
        val llBackground: LinearLayout = itemView.findViewById(R.id.llBackground)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_book_recycler_item,parent,false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.txtBookName.text = list[position]?.volumeInfo?.title ?: ""
        holder.txtAuthor.text = list[position]?.volumeInfo?.authors?.get(0) ?: "no data"
        holder.txtBookPage.text = list[position]?.volumeInfo?.pageCount.toString()
        holder.txtPublisher.text = list[position]?.volumeInfo?.publisher
        Glide.with(context).load(list[position]?.volumeInfo?.imageLinks?.thumbnail ?: "").into(holder.imgBook)
        holder.llBackground.setOnClickListener {
            navController.navigate(R.id.action_searchFragment_to_bookDetailFragment,sendPosViaBundle(position))
        }
    }

    private fun sendPosViaBundle(pos:Int): Bundle {
        val bundle = Bundle()
        bundle.putInt("pos",pos)
        bundle.putBoolean("isSearch",true)
        bundle.putString("type","search")
        return bundle
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(newList: List<ItemsItem?>){
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}