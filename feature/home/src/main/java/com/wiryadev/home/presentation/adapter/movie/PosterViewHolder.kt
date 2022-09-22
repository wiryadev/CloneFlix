package com.wiryadev.home.presentation.adapter.movie

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.wiryadev.shared.data.model.viewparam.MovieViewParam
import com.wiryadev.styling.databinding.ItemMoviePosterBinding
import com.wiryadev.styling.databinding.ItemMoviePosterGridBinding

interface PosterViewHolder {
    fun bindView(movie: MovieViewParam)
}

class PosterViewHolderImpl(
    private val binding: ItemMoviePosterBinding,
    private val onItemClicked: (MovieViewParam) -> Unit
) : RecyclerView.ViewHolder(binding.root), PosterViewHolder {
    override fun bindView(movie: MovieViewParam) {
        with(binding) {
            ivPoster.load(movie.posterUrl)
            root.setOnClickListener { onItemClicked.invoke(movie) }
        }
    }
}

class GridPosterViewHolderImpl(
    private val binding: ItemMoviePosterGridBinding,
    private val onItemClicked: (MovieViewParam) -> Unit
) : RecyclerView.ViewHolder(binding.root), PosterViewHolder {
    override fun bindView(movie: MovieViewParam) {
        with(binding) {
            ivPoster.load(movie.posterUrl)
            root.setOnClickListener { onItemClicked.invoke(movie) }
        }
    }
}