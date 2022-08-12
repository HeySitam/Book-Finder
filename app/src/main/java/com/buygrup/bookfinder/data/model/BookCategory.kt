package com.buygrup.bookfinder.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class BookCategory(
    @ColumnInfo(name = "category")
    val category:String,
    @PrimaryKey(autoGenerate = true) var id: Long= 0L
)