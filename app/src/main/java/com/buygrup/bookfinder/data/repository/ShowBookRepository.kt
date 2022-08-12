package com.buygrup.bookfinder.data.repository

import androidx.lifecycle.LiveData
import com.buygrup.bookfinder.data.api.ShowBookClient
import com.buygrup.bookfinder.data.db.CategoryDao
import com.buygrup.bookfinder.data.model.BookCategory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShowBookRepository(private val categoryDao: CategoryDao) {
    suspend fun getBooks(q: String) = ShowBookClient.api.getBookResponse(q)


    suspend fun insertCategory(category: BookCategory) {
        categoryDao.insertCategory(category)
    }

    fun getCategories(): LiveData<List<BookCategory>> {
        return categoryDao.getCategories()
    }
}