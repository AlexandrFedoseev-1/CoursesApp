package com.example.coursesApp.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.impl.AddToFavoritesUseCase
import com.example.domain.impl.GetFavoritesUseCase
import com.example.domain.model.Course
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase
) : ViewModel() {

    private val _courses = MutableLiveData<List<Course>>(emptyList())
    val courses: LiveData<List<Course>> = _courses

    init {
        loadFavCourses()
    }

    private fun loadFavCourses() {
        viewModelScope.launch(Dispatchers.IO) {
            getFavoritesUseCase.invoke().collect { courses -> _courses.postValue(courses) }
        }
    }

    fun onFavoriteClick(course: Course) {
        viewModelScope.launch(Dispatchers.IO) {
            addToFavoritesUseCase.invoke(course.id)
        }
    }


}