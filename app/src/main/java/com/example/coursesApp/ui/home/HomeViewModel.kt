package com.example.coursesApp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursesApp.ScreenState
import com.example.coursesApp.ScreenState.*
import com.example.domain.impl.AddToFavoritesUseCase
import com.example.domain.impl.GetCoursesUseCase
import com.example.domain.impl.SortCoursesByPublishDateUseCase
import com.example.domain.model.Course
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCoursesUseCase: GetCoursesUseCase,
    private val sortCoursesByPublishDateUseCase: SortCoursesByPublishDateUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase
) : ViewModel() {
    private val _screenState = MutableLiveData<ScreenState>(Loading)
    val courses: LiveData<ScreenState> = _screenState

    private var isSortedDeprecated = false
    private var isSorted = false

    init {
        loadCourses()
    }

    private fun loadCourses() {
        _screenState.value = Loading
        viewModelScope.launch(Dispatchers.IO) {
            getCoursesUseCase.invoke()
                .collect { courses ->

                    _screenState.postValue(
                        Content(
                            sortCoursesByPublishDateUseCase.invoke(
                                courses, isSorted, isSortedDeprecated
                            )
                        )
                    )

                }
        }
    }

    fun onFavoriteClick(course: Course) {
        viewModelScope.launch(Dispatchers.IO) {
            addToFavoritesUseCase.invoke(course.id)
        }
    }

    fun onSortClick() {
        isSortedDeprecated = !isSortedDeprecated
        isSorted = true
        val currentState = _screenState.value
        when (currentState) {
            is Content -> {
                if (currentState.courses.isEmpty()) return
                else {
                    val sortedCourses = sortCoursesByPublishDateUseCase.invoke(
                        currentState.courses,
                        isSorted,
                        isSortedDeprecated
                    )
                    _screenState.value = Content(sortedCourses)

                }
            }

            else -> return
        }
    }

}