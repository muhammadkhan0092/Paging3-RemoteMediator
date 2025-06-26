package com.example.paging3.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "RemoteKey")
data class RemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id : Int=1,
    val prev : Int? = null,
    val next : Int? = null
)
