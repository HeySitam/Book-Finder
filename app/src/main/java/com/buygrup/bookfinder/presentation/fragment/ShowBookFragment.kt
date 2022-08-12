package com.buygrup.bookfinder.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.buygrup.bookfinder.R
import com.buygrup.bookfinder.data.db.BookDatabase
import com.buygrup.bookfinder.databinding.FragmentShowBookBinding
import com.buygrup.bookfinder.data.model.ItemsItem
import com.buygrup.bookfinder.data.repository.ShowBookRepository
import com.buygrup.bookfinder.presentation.adapter.BookCategoryAdapter
import com.buygrup.bookfinder.presentation.adapter.ShowBookAdapter
import com.buygrup.bookfinder.presentation.adapter.ShowTopicWiseBookAdapter
import com.buygrup.bookfinder.presentation.viewModel.ShowBookViewModel
import com.buygrup.bookfinder.presentation.viewModel.ShowBookViewModelFactory
import java.lang.NullPointerException

class ShowBookFragment : Fragment() {
    lateinit var binding: FragmentShowBookBinding
    lateinit var viewModel: ShowBookViewModel
    lateinit var db: BookDatabase

    companion object {
        val list: ArrayList<ItemsItem?> = ArrayList<ItemsItem?>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_show_book, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // creating the database instance
        db = BookDatabase.getDatabase(requireContext())
        // Creating the View model instance
        viewModel = ViewModelProvider(
            requireActivity(),
            ShowBookViewModelFactory(ShowBookRepository(db.categoryDao()))
        ).get(ShowBookViewModel::class.java)
        val bookAdapter = ShowBookAdapter(requireContext(), findNavController())
        val topicAdapter = ShowTopicWiseBookAdapter(requireContext(), findNavController())
        val categoryAdapter =
            BookCategoryAdapter(requireContext(), viewModel, viewLifecycleOwner, topicAdapter,binding.pbLower)

        // getting random books remotely
        viewModel.getBooks("DSA")
        viewModel.getBookResponse.observe(viewLifecycleOwner) { showBookResponse ->

            list.clear()
            try {
                list.addAll(showBookResponse?.items!!)
            } catch (e: NullPointerException) {
                Log.d("chkStatus", "Item not fetched")
            }
            if(list.isNotEmpty()) {
                binding.pbUpper.visibility = View.GONE
                bookAdapter.updateList(list)
            }
        }

        // getting 1st topic books remotely
        viewModel.getTopicWiseBooks("Networking")
        viewModel.getBookTopicResponse.observe(viewLifecycleOwner) { showBookResponse ->

            list.clear()
            try {

                list.addAll(showBookResponse?.items!!)
                Log.d("chkStatus", list.toString())
            } catch (e: NullPointerException) {
                Log.d("chkStatus", "Item not fetched")
            }
            if(list.isNotEmpty()) {
                binding.pbLower.visibility = View.GONE
                topicAdapter.updateList(list)
            }
        }

        // getting category locally
//        viewModel.getCategories()
//        viewModel.getCategory.observe(viewLifecycleOwner){ list ->
//            categoryAdapter.updateList(list)
//        }

        categoryAdapter.updateList(
            listOf(
                "Networking",
                "OS",
                "C",
                "C++",
                "Java",
                "Python",
                "Android",
                "Web Dev",
                "DSA",
                "Machine Learning"
            )
        )

        binding.rvNewBooks.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = bookAdapter
        }
        binding.rvTopics.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }
        binding.rvTopicWiseBooks.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = topicAdapter
        }

        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_showBookFragment_to_searchFragment)
        }
    }

}