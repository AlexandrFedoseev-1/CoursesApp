package com.example.coursesApp.di

import androidx.room.Room
import com.example.data.api.CoursesApi
import com.example.data.api.CoursesApi.Companion.BASE_URL
import com.example.data.database.AppDatabase
import com.example.data.repository.CoursesRepositoryImpl
import com.example.domain.api.CoursesRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

val dataModule = module {

//    NetworkClient
    single<CoursesApi> {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(CoursesApi::class.java)

    }


    // DataBaseClient

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "courses_db")
            .fallbackToDestructiveMigration(false).build()
    }

    // Repository

    single<CoursesRepository> {
        CoursesRepositoryImpl(get(), get())
    }
}
