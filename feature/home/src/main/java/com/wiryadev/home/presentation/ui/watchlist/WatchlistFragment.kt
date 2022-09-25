package com.wiryadev.home.presentation.ui.watchlist

import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.wiryadev.core.base.BaseFragment
import com.wiryadev.home.R
import com.wiryadev.home.databinding.FragmentWatchlistBinding
import com.wiryadev.home.presentation.adapter.movie.MovieAdapter
import com.wiryadev.home.presentation.ui.home.HomeViewModel
import com.wiryadev.shared.utils.ext.subscribe
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class WatchlistFragment :
    BaseFragment<FragmentWatchlistBinding, HomeViewModel>(FragmentWatchlistBinding::inflate) {

    override val viewModel: HomeViewModel by sharedViewModel()

    private val movieAdapter: MovieAdapter by lazy {
        MovieAdapter(
            isGridLayout = true,
            onItemClicked = {},
        )
    }

    override fun initView() {
        setupRecyclerView()
    }

    override fun observeData() {
        super.observeData()
        initData()

        viewModel.watchlistResult.observe(this) {
            it.subscribe(
                doOnSuccess = { result ->
                    showLoading(false)
                    result.payload?.let { data ->
                        movieAdapter.setItems(data)
                    }
                },
                doOnLoading = {
                    showLoading(true)
                },
                doOnError = { error ->
                    showLoading(false)
                    error.exception?.let { e -> showError(true, e) }
                },
                doOnEmpty = {
                    showLoading(false)
                    binding.tvErrorWatchlist.isVisible = true
                    binding.tvErrorWatchlist.text = getString(R.string.text_empty_watchlist)
                },
            )
        }
    }

    private fun setupRecyclerView() {
        binding.rvWatchlist.apply {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
        }

        binding.srlWatchlist.setOnRefreshListener {
            initData()
        }
    }

    private fun initData() {
        viewModel.fetchWatchlist()
    }

    private fun showLoading(isShowLoading: Boolean) {
        binding.pbWatchlist.isVisible = isShowLoading
        binding.srlWatchlist.isRefreshing = isShowLoading
    }

}