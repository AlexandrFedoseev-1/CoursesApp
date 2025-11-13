package com.example.domain.impl

import com.example.domain.api.CoursesRepository

class AddToFavoritesUseCase(private val repository: CoursesRepository) {
    suspend operator fun invoke(courseId: Int) = repository.addToFavorites(courseId)
}