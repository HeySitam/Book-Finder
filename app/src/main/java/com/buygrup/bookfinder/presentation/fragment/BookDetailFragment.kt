package com.buygrup.bookfinder.presentation.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.buygrup.bookfinder.R
import com.buygrup.bookfinder.databinding.FragmentBookDetailBinding

class BookDetailFragment : Fragment() {
    lateinit var binding:FragmentBookDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itemPosition:Int = arguments?.getInt("pos") ?: 0
        val isNavigateFromSearch:Boolean = arguments?.getBoolean("isSearch") ?: false
        var itemBook = ShowBookFragment.list[itemPosition]!!
        if(isNavigateFromSearch){
            itemBook = SearchFragment.list[itemPosition]!!
        }

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        // loading image
        Glide.with(requireContext()).load(itemBook.volumeInfo?.imageLinks?.thumbnail).into(binding.imgBook)
        binding.txtBookName.text = itemBook.volumeInfo?.title
        binding.txtAuthors.text = itemBook.volumeInfo?.authors?.get(0)!!
        binding.txtPublishDate.text = itemBook.volumeInfo!!.publishedDate?.substring(0,4) ?: ""
        binding.txtPages.text = itemBook.volumeInfo!!.pageCount.toString()
        if(itemBook.saleInfo?.isEbook!!){
            binding.txtEbookStatus.text = "Available"
        } else {
            binding.txtEbookStatus.text = "Unavailable"
        }

        binding.txtDesc.text = itemBook.volumeInfo!!.description
        binding.btnPreview.setOnClickListener {
            val uri = Uri.parse(itemBook.volumeInfo!!.previewLink)
            val intent = Intent(Intent.ACTION_VIEW,uri)
            startActivity(intent)
        }

    }
}