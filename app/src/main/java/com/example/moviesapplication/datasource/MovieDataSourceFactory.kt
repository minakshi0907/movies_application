package com.example.moviesapplication.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.moviesapplication.api.ApiInterface
import com.example.moviesapplication.datasource.MoviesDataSource
import com.example.moviesapplication.model.movies.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory(private val apiService: ApiInterface,
                             private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource = MutableLiveData<MoviesDataSource>()


    override fun create(): DataSource<Int, Movie> {
        val moviesDataSource =
            MoviesDataSource(
                apiService,
                compositeDisposable
            )
        moviesLiveDataSource.postValue(moviesDataSource)
        return moviesDataSource
    }
}