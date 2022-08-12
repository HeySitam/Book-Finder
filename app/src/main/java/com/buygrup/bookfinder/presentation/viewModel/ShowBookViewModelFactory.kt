package com.buygrup.bookfinder.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.buygrup.bookfinder.data.repository.ShowBookRepository

class ShowBookViewModelFactory (private val repository: ShowBookRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ShowBookViewModel::class.java)) {
            ShowBookViewModel(repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}