package com.buygrup.bookfinder.presentation.adapter

import android.content.ClipData
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.buygrup.bookfinder.R
import com.buygrup.bookfinder.data.model.ItemsItem

class ShowBookAdapter(private val context: Context,private val navController: NavController): RecyclerView.Adapter<ShowBookAdapter.ShowBookViewHolder>() {
    private val list: ArrayList<ItemsItem?> = ArrayList()
    inner class ShowBookViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imgBook: ImageView = itemView.findViewById(R.id.imgBook)
        val bookName: TextView = itemView.findViewById(R.id.txtBookName)
        val rlBookItem: RelativeLayout = itemView.findViewById(R.id.rlBookItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowBookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_list_item,parent,false)
        return ShowBookViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShowBookViewHolder, position: Int) {
        holder.bookName.text = list[position]?.volumeInfo?.title ?: ""
        Glide.with(context).load(list[position]?.volumeInfo?.imageLinks?.thumbnail!!).into(holder.imgBook)
        holder.rlBookItem.setOnClickListener {
            navController.navigate(R.id.action_showBookFragment_to_bookDetailFragment,sendPosViaBundle(position))
        }
    }

    private fun sendPosViaBundle(pos:Int):Bundle{
        val bundle = Bundle()
        bundle.putInt("pos",pos)
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