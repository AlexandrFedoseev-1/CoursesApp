package com.example.domain.impl

import com.example.domain.api.CoursesRepository
import com.example.domain.model.Course
import kotlinx.coroutines.flow.Flow

class GetCoursesUseCase(private val coursesRepository: CoursesRepository) {
    suspend operator fun invoke(): Flow<List<Course>> =
        coursesRepository.getCourses()
}