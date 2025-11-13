package com.example.data.api

import com.example.data.dto.CoursesResponseDto
import retrofit2.http.GET

interface CoursesApi {
    @GET("uc?id=15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q&export=download")
    suspend fun getCourses(): CoursesResponseDto

    companion object{
        const val BASE_URL = "https://drive.usercontent.google.com/u/0/"
    }
}
