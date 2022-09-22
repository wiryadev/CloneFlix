package com.wiryadev.home.presentation.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wiryadev.home.R
import com.wiryadev.home.databinding.ItemHomeHeaderBinding
import com.wiryadev.home.databinding.ItemHomeSectionBinding
import com.wiryadev.home.presentation.viewparam.homeitem.HomeUiItem
import com.wiryadev.shared.data.model.viewparam.MovieViewParam

class HomeAdapter(
    private val listener: HomeAdapterClickListener,
    private val onMovieClicked: (MovieViewParam) -> Unit,
    private val recyclerViewPool: RecyclerView.RecycledViewPool,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<HomeUiItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_home_header -> {
                val binding = ItemHomeHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HomeHeaderViewHolder(binding, listener)
            }

            else -> {
                val binding = ItemHomeSectionBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HomeSectionViewHolder(binding, recyclerViewPool, onMovieClicked)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when (holder) {
            is HomeHeaderViewHolder -> {
                holder.bindView(item as HomeUiItem.HeaderSectionItem)
            }
            is HomeSectionViewHolder -> {
                holder.bindView(item as HomeUiItem.MovieSectionItem)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is HomeUiItem.HeaderSectionItem -> R.layout.item_home_header
            is HomeUiItem.MovieSectionItem -> R.layout.item_home_section
        }
    }

    fun setItems(items: List<HomeUiItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

}