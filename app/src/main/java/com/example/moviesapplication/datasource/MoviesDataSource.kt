package com.example.moviesapplication.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.moviesapplication.api.ApiInterface
import com.example.moviesapplication.model.movies.Movie
import com.example.moviesapplication.utils.AppConstants.pageNo
import com.example.moviesapplication.utils.EspressoIdlingResource
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers


class MoviesDataSource constructor(
    private val apiServices: ApiInterface,
    private val compositeDisposable: CompositeDisposable
) :
    PageKeyedDataSource<Int, Movie>() {

    var state: MutableLiveData<State> = MutableLiveData()
    private var retryCompletable: Completable? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        EspressoIdlingResource.increment()
        updateState(State.LOADING)
        compositeDisposable.add(
            apiServices.getMoviesList(pageNo)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { response ->
                        updateState(State.DONE)
                        EspressoIdlingResource.decrement()
                        callback.onResult(
                            response.movies,
                            null,
                            pageNo+1
                        )
                    },
                    {
                        updateState(State.ERROR)
                        setRetry(Action { loadInitial(params, callback) })
                    }
                )
        )
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Movie>
    ) {
        updateState(State.LOADING)
        compositeDisposable.add(
            apiServices.getMoviesList(params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { response ->
                        if (response.total_pages >= params.key) {
                            callback.onResult(response.movies, params.key + 1)
                            updateState(State.DONE)
                        } else {
                            //output.endOfList.postValue(true)
                        }
                    }, {
                        updateState(State.DONE)
                    }
                )
        )

    }

    override fun loadBefore(
        params:LoadParams<Int>,
        callback: LoadCallback<Int, Movie>
    ) {
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(
                retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            )
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }
}