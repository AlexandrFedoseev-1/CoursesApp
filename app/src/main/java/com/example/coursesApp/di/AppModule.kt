package com.example.coursesApp.di

import com.example.coursesApp.ui.favorite.FavoriteViewModel
import com.example.coursesApp.ui.home.HomeViewModel
import com.example.domain.impl.AddToFavoritesUseCase
import com.example.domain.impl.GetCoursesUseCase
import com.example.domain.impl.GetFavoritesUseCase
import com.example.domain.impl.SortCoursesByPublishDateUseCase
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    // Use cases
    factory<GetCoursesUseCase> {
        GetCoursesUseCase(coursesRepository = get())
    }
    factory<GetFavoritesUseCase> {
        GetFavoritesUseCase(repository = get())
    }
    factory<AddToFavoritesUseCase> {
        AddToFavoritesUseCase(repository = get())
    }
    factory<SortCoursesByPublishDateUseCase> {
        SortCoursesByPublishDateUseCase()
    }

    // viewModels
    viewModel {
        HomeViewModel(
            getCoursesUseCase = get(),
            sortCoursesByPublishDateUseCase = get(),
            addToFavoritesUseCase = get()
        )
    }
    viewModel {
        FavoriteViewModel(getFavoritesUseCase = get(), addToFavoritesUseCase = get())
    }
}
