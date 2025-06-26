package com.example.paging3.di

import android.content.Context
import androidx.room.Room
import com.example.paging3.db.ProductDatabase
import com.example.paging3.retrofit.ProductApi
import com.example.paging3.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) : ProductDatabase{
        return Room.databaseBuilder(
            context,
            ProductDatabase::class.java,
            "ProductDatabase"
        ).build()
    }


}