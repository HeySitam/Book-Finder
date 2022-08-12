package com.buygrup.bookfinder.presentation.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.buygrup.bookfinder.R
import com.buygrup.bookfinder.data.model.ItemsItem
import com.buygrup.bookfinder.data.repository.ShowBookRepository
import com.buygrup.bookfinder.databinding.FragmentSearchBinding
import com.buygrup.bookfinder.presentation.adapter.SearchAdapter
import com.buygrup.bookfinder.presentation.viewModel.ShowBookViewModel
import com.buygrup.bookfinder.presentation.viewModel.ShowBookViewModelFactory
import com.buygrup.bookfinder.util.ConnectionLiveData


class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
    lateinit var viewModel: ShowBookViewModel

    companion object {
        val list: ArrayList<ItemsItem?> = ArrayList<ItemsItem?>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            requireActivity(),
            ShowBookViewModelFactory(ShowBookRepository())
        ).get(ShowBookViewModel::class.java)

        ConnectionLiveData(requireContext()).observe(viewLifecycleOwner){ isAvailable ->
            if(isAvailable){
                binding.imgBack.visibility = View.VISIBLE
                binding.llSearchBar.visibility = View.VISIBLE
                binding.txtBookNo.visibility = View.VISIBLE
                binding.rvSearch.visibility = View.VISIBLE
                binding.animationView.visibility = View.GONE
                actionWhenNetworkAvailable()
            } else {
                binding.imgBack.visibility = View.GONE
                binding.llSearchBar.visibility = View.GONE
                binding.txtBookNo.visibility = View.GONE
                binding.rvSearch.visibility = View.GONE
                binding.animationView.visibility = View.VISIBLE
            }
        }


    }

    private fun actionWhenNetworkAvailable(){
        binding.etSearch.requestFocus()
        val adapter = SearchAdapter(requireContext(), findNavController())
        binding.rvSearch.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setAdapter(adapter)
        }

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.toString().isEmpty()) {
                    list.clear()
                    binding.pbLoading.visibility = View.GONE
                    binding.txtBookNo.text = "No Book Found"
                    adapter.updateList(list)
                } else {
                    // getting random books remotely
                    binding.pbLoading.visibility = View.VISIBLE
                    viewModel.getBooks(s.toString())
                    viewModel.getBookResponse.observe(viewLifecycleOwner) { showBookResponse ->

                        list.clear()
                        try {

                            list.addAll(showBookResponse?.items!!)
                            Log.d("chkStatus", list.toString())
                        } catch (e: NullPointerException) {
                            Log.d("chkStatus", "Item not fetched")
                        }
                        if(s.toString().isNotEmpty()) {
                            adapter.updateList(list)
                            binding.pbLoading.visibility = View.GONE
                            binding.txtBookNo.text = getString(R.string.book_cnt, list.size)
                        } else {
                            list.clear()
                            binding.pbLoading.visibility = View.GONE
                            binding.txtBookNo.text = "No Book Found"
                            adapter.updateList(list)
                        }
                    }
                }
            }
        })
    }
}