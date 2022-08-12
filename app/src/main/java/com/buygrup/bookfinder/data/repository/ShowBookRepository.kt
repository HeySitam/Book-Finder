package com.buygrup.bookfinder.data.repository


import com.buygrup.bookfinder.data.api.ShowBookClient

class ShowBookRepository() {
    suspend fun getBooks(q: String) = ShowBookClient.api.getBookResponse(q)

}