package com.example.moviesapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapplication.BuildConfig
import com.example.moviesapplication.R
import com.example.moviesapplication.datasource.State
import com.example.moviesapplication.model.movies.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie.view.*
import com.example.moviesapplication.databinding.ItemMovieBinding


class MoviesListPagedAdapter : PagedListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffCallback){

    var onItemClick : ((Movie?) -> Unit)? = null
    private var state = State.LOADING

    companion object {
        val MovieDiffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }

        @JvmStatic
        @BindingAdapter("loadImage")
        fun loadImage(image: ImageView, url: String) {
            if (!url.isNullOrEmpty())
                Picasso.get().load("${BuildConfig.POSTER_URL}$url").into(image)
        }

        @JvmStatic
        @BindingAdapter("showPopularity")
        fun showPopularity(percentage: TextView, voteAvg: Double) {
            percentage.text = String.format("%d%s", voteAvg.times(10).toInt(), "%")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val movieListItemBinding:ItemMovieBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.getContext()),
            R.layout.item_movie, parent, false
        )
        return MovieItemViewHolder(movieListItemBinding);
    }

    inner class MovieItemViewHolder(val movieListItemBinding:ItemMovieBinding) : RecyclerView.ViewHolder(movieListItemBinding.root) {
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MovieItemViewHolder).movieListItemBinding.movie = getItem(position)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(getItem(position))
        }
    }

    fun setState(state: State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }

}