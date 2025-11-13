package com.example.data.repository

import com.example.data.api.CoursesApi
import com.example.data.database.AppDatabase
import com.example.data.mapper.CourseMapper.toDomain
import com.example.data.mapper.CourseMapper.toEntity
import com.example.domain.api.CoursesRepository
import com.example.domain.model.Course
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CoursesRepositoryImpl(
    private val api: CoursesApi,
    private val appDatabase: AppDatabase
) : CoursesRepository {

    override suspend fun getCoursesFromApi(): Result<List<Course>> {
        return try {
            val response = api.getCourses()
            val entities = response.courses.map { it.toEntity() }
            appDatabase.courseDao().insertAll(entities)
            Result.success(entities.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addToFavorites(courseId: Int): Result<Unit> {
        return try {
            val course = appDatabase.courseDao().getById(courseId) ?: return Result.failure(
                NoSuchElementException("Course with id $courseId not found")
            )
            appDatabase.courseDao().setLiked(courseId, !course.hasLike)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getFavorites(): Flow<List<Course>> =
        appDatabase.courseDao().getLikedFlow().map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun getCourses(): Flow<List<Course>> {
        getCoursesFromApi()
        return appDatabase.courseDao().getAll().map { list -> list.map { it.toDomain() }}

    }




}