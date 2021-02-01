package com.example.moviesapplication.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.moviesapplication.BuildConfig
import com.example.moviesapplication.R
import com.example.moviesapplication.base.BaseActivity
import com.example.moviesapplication.utils.AppConstants.MOVIE_ID
import com.example.moviesapplication.utils.AppUtils.genreToCommaSeparatedString
import com.example.moviesapplication.utils.AppUtils.languageToCommaSeparatedString
import com.example.moviesapplication.viewmodel.MovieDetailsViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.moviesapplication.databinding.ActivityMovieDetailsBinding
import com.example.moviesapplication.datasource.State
import com.example.moviesapplication.model.moviedetails.Genre
import com.example.moviesapplication.model.moviedetails.SpokenLanguage
import com.example.moviesapplication.utils.AppConstants


class MovieDetailsActivity : BaseActivity() {

    private val viewModel: MovieDetailsViewModel by viewModel()
    private var movieId: Int = 0
    lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_movie_details)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);
        movieId = intent.getIntExtra(MOVIE_ID, 0)

        setTitle(AppConstants.movieDetailsTitle)
        showBackBtn()
        fetchMovieDetails()
        initListner()
        initState()

    }

    fun fetchMovieDetails() {
        viewModel.movieDetails.observe(this,
            Observer {
                binding.moviedetail = it
            })
        viewModel.getMovieDetails(movieId)
    }

    private fun initState() {
        txt_error_moviedetails.setOnClickListener { }
        viewModel.getState().observe(this, Observer { state ->
            progress_bar_moviedetails.visibility =
                if ( state == State.LOADING) View.VISIBLE else View.GONE
            txt_error_moviedetails.visibility =
                if (state == State.ERROR) View.VISIBLE else View.GONE

        })
    }

    private fun initListner() {
        btn_bookNow.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("${BuildConfig.MOVIE_WEB_URL}${movieId}")
            startActivity(openURL)
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("loadDetailImage")
        fun loadDetailImage(image: ImageView, url: String?) {
            if (!url.isNullOrEmpty())
                Picasso.get().load("${BuildConfig.POSTER_URL}$url").into(image)
        }

        @JvmStatic
        @BindingAdapter("showLanguage")
        fun showLanguage(language: TextView, listLanguage: List<SpokenLanguage> ?) {
            if (listLanguage != null) {
                language.text = listLanguage.languageToCommaSeparatedString()
            }
        }

        @JvmStatic
        @BindingAdapter("showGenre")
        fun showGenre(genre: TextView, listGenre: List<Genre> ?) {
            if (listGenre != null) {
                genre.text = listGenre.genreToCommaSeparatedString()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}