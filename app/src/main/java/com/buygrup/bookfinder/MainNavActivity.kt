package com.buygrup.bookfinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.buygrup.bookfinder.data.repository.ShowBookRepository
import com.buygrup.bookfinder.databinding.ActivityMainBinding
import com.buygrup.bookfinder.presentation.viewModel.ShowBookViewModel
import com.buygrup.bookfinder.presentation.viewModel.ShowBookViewModelFactory
import com.buygrup.bookfinder.util.ConnectionLiveData

class MainNavActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: ShowBookViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        viewModel = ViewModelProvider(
            this,
            ShowBookViewModelFactory(ShowBookRepository())
        ).get(ShowBookViewModel::class.java)


    }
}