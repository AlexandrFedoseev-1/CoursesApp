package com.example.coursesApp

import com.example.domain.model.Course

sealed class ScreenState {
    data object Loading : ScreenState()
    data class Content(val courses: List<Course>) : ScreenState()
}