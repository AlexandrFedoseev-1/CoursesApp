package com.example.data.mapper

import com.example.data.database.CourseEntity
import com.example.data.dto.CourseDto
import com.example.domain.model.Course
import kotlin.collections.map

object CourseMapper {
    fun CourseDto.toEntity(): CourseEntity =
        CourseEntity(
            id = id,
            title = title,
            text = text,
            price = price,
            rate = rate,
            startDate = startDate,
            hasLike = hasLike,
            publishDate = publishDate
        )


    fun CourseEntity.toDomain(): Course =
        Course(
            id = id,
            title = title,
            text = text,
            price = price,
            rate = rate,
            startDate = startDate,
            hasLike = hasLike,
            publishDate = publishDate
        )


     fun List<CourseEntity>.toDomainList(): List<Course> =
        this.map { it.toDomain() }
}