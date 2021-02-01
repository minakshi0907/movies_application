package com.example.moviesapplication.repository

import androidx.lifecycle.MutableLiveData
import com.example.moviesapplication.api.ApiInterface
import com.example.moviesapplication.datasource.State
import com.example.moviesapplication.model.moviedetails.MovieDetails
import com.example.moviesapplication.utils.EspressoIdlingResource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDetailsRespository(private val apiServices: ApiInterface){

    var state: MutableLiveData<State> = MutableLiveData()
    var movieDetailsData: MutableLiveData<MovieDetails> = MutableLiveData()

    fun getMovieDetails(compositeDisposable: CompositeDisposable, movieId: Int) {
        EspressoIdlingResource.increment()
        updateState(State.LOADING)
        try {
            compositeDisposable.add(
                apiServices.getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            EspressoIdlingResource.decrement()
                            updateState(State.DONE)
                            postData(it)
                        }, {
                            updateState(State.ERROR)
                            it.message?.let { it1 -> error(it1) }
                        })
            )
        } catch (e: Exception) {
            error(e.localizedMessage)
        }
    }

    private fun postData(movieDetails: MovieDetails) {
        this.movieDetailsData.postValue(movieDetails)
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    private fun error(errMessage : String) {
    }

}