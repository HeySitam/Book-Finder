package com.buygrup.bookfinder.presentation.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.buygrup.bookfinder.R
import com.buygrup.bookfinder.data.model.ItemsItem
import com.buygrup.bookfinder.databinding.FragmentBookDetailBinding
import com.buygrup.bookfinder.util.ConnectionLiveData

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

        ConnectionLiveData(requireContext()).observe(viewLifecycleOwner){ isAvailable ->
            if(isAvailable){
                binding.flIcons.visibility = View.VISIBLE
                binding.imgBack.visibility = View.VISIBLE
                binding.mcvBookImg.visibility = View.VISIBLE
                binding.txtBookName.visibility = View.VISIBLE
                binding.txtAuthors.visibility = View.VISIBLE
                binding.llData.visibility = View.VISIBLE
                binding.txtDesc.visibility = View.VISIBLE
                binding.btnPreview.visibility = View.VISIBLE
                binding.animationView.visibility = View.GONE
                actionWhenNetworkAvailable()
            } else {
                binding.flIcons.visibility = View.GONE
                binding.imgBack.visibility = View.GONE
                binding.mcvBookImg.visibility = View.GONE
                binding.txtBookName.visibility = View.GONE
                binding.txtAuthors.visibility = View.GONE
                binding.llData.visibility = View.GONE
                binding.txtDesc.visibility = View.GONE
                binding.btnPreview.visibility = View.GONE
                binding.animationView.visibility = View.VISIBLE
            }
        }



    }

    private fun actionWhenNetworkAvailable(){
        val itemPosition:Int = arguments?.getInt("pos") ?: 0
        val itemType:String = arguments?.getString("type").toString()
        //var itemBook = ShowBookFragment.list[itemPosition]!!
        Log.d("chkType",itemType)
        var itemBook: ItemsItem? = ShowBookFragment.listTopicWise[itemPosition]!!
        when(itemType){
            "random" -> {
                itemBook = ShowBookFragment.listRandom[itemPosition]!!
            }
            "search" -> {
                itemBook = SearchFragment.list[itemPosition]!!
            }
            "topicwise" -> {
                itemBook = ShowBookFragment.listTopicWise[itemPosition]!!
            }
        }

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        // loading image
        Glide.with(requireContext()).load(itemBook?.volumeInfo?.imageLinks?.thumbnail).into(binding.imgBook)
        binding.txtBookName.text = itemBook?.volumeInfo?.title
        binding.txtAuthors.text = itemBook?.volumeInfo?.authors?.get(0) ?: ""
        binding.txtPublishDate.text = itemBook?.volumeInfo!!.publishedDate?.substring(0,4) ?: ""
        binding.txtPages.text = itemBook?.volumeInfo!!.pageCount.toString()
        if(itemBook?.saleInfo?.isEbook!!){
            binding.txtEbookStatus.text = "Available"
        } else {
            binding.txtEbookStatus.text = "Unavailable"
        }

        binding.txtDesc.text = itemBook?.volumeInfo!!.description
        binding.btnPreview.setOnClickListener {
            val uri = Uri.parse(itemBook?.volumeInfo!!.previewLink)
            val intent = Intent(Intent.ACTION_VIEW,uri)
            startActivity(intent)
        }
    }
}