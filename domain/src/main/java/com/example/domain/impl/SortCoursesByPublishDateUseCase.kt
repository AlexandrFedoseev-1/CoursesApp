package com.example.domain.impl

import com.example.domain.model.Course

class SortCoursesByPublishDateUseCase {
    operator fun invoke(courses: List<Course>, sorted: Boolean , descending: Boolean = true): List<Course> =
       if (!sorted) courses
       else courses.sortedByDescending { it.publishDate }.let {
            if (descending) it else it.reversed()
        }
}