package com.wiryadev.home.presentation.adapter.home

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.wiryadev.home.databinding.ItemHomeHeaderBinding
import com.wiryadev.home.presentation.viewparam.homeitem.HomeUiItem
import com.wiryadev.shared.utils.CommonUtils

class HomeHeaderViewHolder(
    private val binding: ItemHomeHeaderBinding,
    private val listener: HomeAdapterClickListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bindView(item: HomeUiItem.HeaderSectionItem) {
        val movie = item.movieViewParam
        with(binding) {
            tvAddToWatchlistHeader.setCompoundDrawablesWithIntrinsicBounds(
                0,
                CommonUtils.getWatchlistIcon(movie.isUserWatchlist), 0, 0
            )
            ivHeaderMovie.load(movie.posterUrl)
            tvTitleMovie.text = movie.title
            tvInfoHeader.setOnClickListener {
                listener.onMovieClicked(movie)
            }
            tvAddToWatchlistHeader.setOnClickListener {
                listener.onMyListClicked(movie)
            }
            cvPlayHeader.setOnClickListener {
                listener.onPlayMovieClicked(movie)
            }
        }
    }

}