package com.example.domain.impl

import com.example.domain.api.CoursesRepository
import com.example.domain.model.Course
import kotlinx.coroutines.flow.Flow

class GetFavoritesUseCase(private val repository: CoursesRepository) {
    suspend operator fun invoke(): Flow<List<Course>> = repository.getFavorites()
}