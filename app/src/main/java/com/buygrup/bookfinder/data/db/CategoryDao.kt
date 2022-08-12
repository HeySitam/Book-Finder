package com.buygrup.bookfinder.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.buygrup.bookfinder.data.model.BookCategory

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category:BookCategory)

    @Query("SELECT * FROM category")
    fun getCategories():LiveData<List<BookCategory>>
}