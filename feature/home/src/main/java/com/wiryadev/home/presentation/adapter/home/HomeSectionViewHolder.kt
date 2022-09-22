package com.wiryadev.home.presentation.adapter.home

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wiryadev.home.databinding.ItemHomeSectionBinding
import com.wiryadev.home.presentation.adapter.movie.MovieAdapter
import com.wiryadev.home.presentation.viewparam.homeitem.HomeUiItem
import com.wiryadev.shared.data.model.viewparam.MovieViewParam

class HomeSectionViewHolder(
    private val binding: ItemHomeSectionBinding,
    private val recyclerViewPool: RecyclerView.RecycledViewPool,
    private val onMovieClicked: (MovieViewParam) -> Unit,
//    private val listener: HomeAdapterClickListener,
) : RecyclerView.ViewHolder(binding.root) {

    private val movieAdapter: MovieAdapter by lazy {
        MovieAdapter { onMovieClicked(it) }
    }

    fun bindView(item: HomeUiItem.MovieSectionItem) {
        val section = item.sectionViewParam
        with(binding) {
            tvTitleSection.text = section.name

            rvContents.apply {
                setRecycledViewPool(recyclerViewPool)
                adapter = movieAdapter
                layoutManager = LinearLayoutManager(
                    root.context, LinearLayoutManager.HORIZONTAL, false
                )
            }
            movieAdapter.setItems(section.contents)
        }
    }
}