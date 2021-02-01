package com.example.moviesapplication.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.example.moviesapplication.model.movies.Movie
import com.example.moviesapplication.repository.MoviesRepository
import com.example.moviesapplication.datasource.State
import io.reactivex.disposables.CompositeDisposable


class MoviesViewModel(val movieRespository : MoviesRepository): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val moviePagedList: LiveData<PagedList<Movie>> =
        movieRespository.getMoviesList(compositeDisposable)


    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun getState(): LiveData<State> {
       return movieRespository.getState()
    }

    fun retry() {
        movieRespository.retry()
    }

    fun listIsEmpty(): Boolean {
        return movieRespository.listIsEmpty()
    }
}

