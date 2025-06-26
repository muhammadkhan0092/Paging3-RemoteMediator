package com.example.paging3.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Product")
data class Product(
    val description: String="",
    @PrimaryKey(autoGenerate = false)
    val id: Int=1,
)