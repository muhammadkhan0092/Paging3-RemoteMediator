package com.example.paging3.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.paging3.data.RemoteKey

@Dao
interface RemotekeyDao {

    @Query("SELECT * FROM RemoteKey where id==:id")
    suspend fun getRemoteKey(id : Int) : RemoteKey


    @Insert
    suspend fun addRemoteKeys(remoteKeys : List<RemoteKey>)

    @Query("DELETE FROM RemoteKey")
    suspend fun deleteRemoteKeys()
}