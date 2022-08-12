package com.buygrup.bookfinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.buygrup.bookfinder.data.db.BookDatabase
import com.buygrup.bookfinder.data.repository.ShowBookRepository
import com.buygrup.bookfinder.presentation.viewModel.ShowBookViewModel
import com.buygrup.bookfinder.presentation.viewModel.ShowBookViewModelFactory

class MainNavActivity : AppCompatActivity() {
    lateinit var viewModel: ShowBookViewModel
    lateinit var db: BookDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = BookDatabase.getDatabase(this)
        viewModel = ViewModelProvider(
            this,
            ShowBookViewModelFactory(ShowBookRepository(db.categoryDao()))
        ).get(ShowBookViewModel::class.java)
    }
}