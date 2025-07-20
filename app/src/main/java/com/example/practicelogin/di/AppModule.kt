package com.example.practicelogin.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.practicelogin.data.AppDatabase
import com.example.practicelogin.data.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context : Context) : AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun providesUserDao(database : AppDatabase) : UserDao {
        return database.userDao()
    }

}