package com.example.domain.api

import com.example.domain.model.Course
import kotlinx.coroutines.flow.Flow

interface CoursesRepository {
    suspend fun getCoursesFromApi(): Result<List<Course>>
    suspend fun getCourses(): Flow<List<Course>>
    suspend fun getFavorites(): Flow<List<Course>>
    suspend fun addToFavorites(courseId: Int): Result<Unit>

}