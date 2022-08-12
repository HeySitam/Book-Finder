package com.buygrup.bookfinder.data.api

import com.buygrup.bookfinder.data.model.ShowBookResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ShowBookService {
    @GET("volumes")
    suspend fun getBookResponse(@Query("q") q:String): Response<ShowBookResponse>
}