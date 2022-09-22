package com.wiryadev.home.presentation.adapter.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wiryadev.shared.data.model.viewparam.MovieViewParam
import com.wiryadev.styling.databinding.ItemMoviePosterBinding
import com.wiryadev.styling.databinding.ItemMoviePosterGridBinding

class MovieAdapter(
    private val isGridLayout: Boolean = false,
    private val onItemClicked: (MovieViewParam) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val movies = mutableListOf<MovieViewParam>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (isGridLayout) {
            GridPosterViewHolderImpl(
                binding = ItemMoviePosterGridBinding.inflate(layoutInflater, parent, false),
                onItemClicked = onItemClicked
            )
        } else {
            PosterViewHolderImpl(
                binding = ItemMoviePosterBinding.inflate(layoutInflater, parent, false),
                onItemClicked = onItemClicked
            )
        }
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PosterViewHolder).bindView(movies[position])
    }

    fun setItems(movies : List<MovieViewParam>){
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

}