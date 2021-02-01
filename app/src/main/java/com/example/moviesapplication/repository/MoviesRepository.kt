package com.example.moviesapplication.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.moviesapplication.api.ApiInterface
import com.example.moviesapplication.datasource.MovieDataSourceFactory
import com.example.moviesapplication.datasource.MoviesDataSource
import com.example.moviesapplication.datasource.State
import com.example.moviesapplication.model.movies.Movie
import com.example.moviesapplication.utils.AppConstants.pageSize
import io.reactivex.disposables.CompositeDisposable


class MoviesRepository (private val apiServices: ApiInterface) {

    private lateinit var moviePagedList: LiveData<PagedList<Movie>>
    private lateinit var moviesDataSourceFactory: MovieDataSourceFactory



    fun getMoviesList( compositeDisposable :CompositeDisposable): LiveData<PagedList<Movie>> {
        moviesDataSourceFactory =
            MovieDataSourceFactory(
                apiServices,
                compositeDisposable
            )
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()
        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()
        return moviePagedList
    }

    fun getState(): LiveData<State> = Transformations.switchMap(
        moviesDataSourceFactory.moviesLiveDataSource,
        MoviesDataSource::state
    )

    fun retry() {
        moviesDataSourceFactory.moviesLiveDataSource.value?.retry()
    }

    fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }


}