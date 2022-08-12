package com.buygrup.bookfinder.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buygrup.bookfinder.data.model.ShowBookResponse
import com.buygrup.bookfinder.data.repository.ShowBookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShowBookViewModel(private val repository: ShowBookRepository) : ViewModel() {
    /**
     * to get Book Response
     */
    private val _getBookResponse = MutableLiveData<ShowBookResponse?>(null)
    val getBookResponse: LiveData<ShowBookResponse?> = _getBookResponse

    /**
     * to get Book Category wise
     */
    private val _getBookTopicResponse = MutableLiveData<ShowBookResponse?>(null)
    val getBookTopicResponse: LiveData<ShowBookResponse?> = _getBookTopicResponse

    fun getBooks(topic: String) {
        viewModelScope.launch {
            var result: ShowBookResponse? = null
            withContext(Dispatchers.IO) {
                result = repository.getBooks(topic).body()
            }
            Log.d("chkStatus", result.toString())
            _getBookResponse.value = result
        }
    }

    fun getTopicWiseBooks(topic: String) {
        viewModelScope.launch {
            var result: ShowBookResponse? = null
            withContext(Dispatchers.IO) {
                result = repository.getBooks(topic).body()
            }
            Log.d("chkStatus", result.toString())
            _getBookTopicResponse.value = result
        }
    }

}