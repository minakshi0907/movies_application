package com.example.moviesapplication.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesapplication.R
import com.example.moviesapplication.base.BaseActivity
import com.example.moviesapplication.datasource.State
import com.example.moviesapplication.ui.adapter.MoviesListPagedAdapter
import com.example.moviesapplication.utils.AppConstants
import com.example.moviesapplication.utils.AppConstants.MOVIE_ID
import com.example.moviesapplication.utils.AppUtils
import com.example.moviesapplication.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesActivity : BaseActivity() {

    private val viewModel: MoviesViewModel by viewModel()
    private lateinit var moviesListAdapter: MoviesListPagedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitle(AppConstants.movieListTitle)
        setUpRecyclerView()
        initAdapter()
        initState()
    }

    private fun setUpRecyclerView() {
        if (!AppUtils.checkInternetConnection())
            Toast.makeText(this, getString(R.string.error_internet), Toast.LENGTH_SHORT)
                .show()

        val gridLayoutManager = GridLayoutManager(this, 2)
        moviesRecyclerView.layoutManager = gridLayoutManager
        moviesRecyclerView.setHasFixedSize(true)
    }

    private fun initAdapter() {
        moviesListAdapter = MoviesListPagedAdapter()
        moviesRecyclerView.adapter = moviesListAdapter
        viewModel.moviePagedList.observe(this,
            Observer {
                moviesListAdapter.submitList(it)
            })

        moviesListAdapter.onItemClick = {
            it?.let { movie ->
                openMovieDetailsActivity(movie.id)
            }
        }
    }

    private fun initState() {
        txt_error.setOnClickListener { viewModel.retry() }
        viewModel.getState().observe(this, Observer { state ->
            progress_bar.visibility =
                if (viewModel.listIsEmpty() && state == State.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility =
                if (viewModel.listIsEmpty() && state == State.ERROR) View.VISIBLE else View.GONE
            if (!viewModel.listIsEmpty()) {
                moviesListAdapter.setState(state ?: State.DONE)
            }
        })
    }

    private fun openMovieDetailsActivity(movieId: Int) {
        startActivity(
            Intent(this, MovieDetailsActivity::class.java)
                .putExtra(MOVIE_ID, movieId)
        )
    }

}