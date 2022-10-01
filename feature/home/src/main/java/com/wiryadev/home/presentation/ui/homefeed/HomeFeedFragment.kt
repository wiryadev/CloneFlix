package com.wiryadev.home.presentation.ui.homefeed

import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wiryadev.core.base.BaseFragment
import com.wiryadev.home.R
import com.wiryadev.home.databinding.FragmentHomeFeedBinding
import com.wiryadev.home.presentation.adapter.home.HomeAdapter
import com.wiryadev.home.presentation.adapter.home.createHomeAdapterClickListener
import com.wiryadev.home.presentation.ui.home.HomeViewModel
import com.wiryadev.shared.data.model.viewparam.MovieViewParam
import com.wiryadev.shared.router.ActivityRouter
import com.wiryadev.shared.router.BottomSheetRouter
import com.wiryadev.shared.utils.ColorUtils
import com.wiryadev.shared.utils.ext.subscribe
import com.wiryadev.shared.utils.text_drawable.ColorGenerator
import com.wiryadev.shared.utils.text_drawable.TextDrawable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.math.min

class HomeFeedFragment :
    BaseFragment<FragmentHomeFeedBinding, HomeViewModel>(FragmentHomeFeedBinding::inflate) {

    override val viewModel: HomeViewModel by sharedViewModel()

    private val bottomSheetRouter: BottomSheetRouter by inject()
    private val activityRouter: ActivityRouter by inject()

    private val recyclerViewPool: RecyclerView.RecycledViewPool by lazy {
        RecyclerView.RecycledViewPool()
    }

    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(
            listener = createHomeAdapterClickListener(
                onMyListClicked = { viewModel.addOrRemoveWatchlist(it) },
                onPlayMovieClicked = {
                    startActivity(activityRouter.playerActivity(requireContext(), it.videoUrl))
                },
                onMovieClicked = { openBottomSheet(it) },
            ),
            recyclerViewPool = recyclerViewPool,
        )
    }

    override fun initView() {
        setupRecyclerView()
        initData()
    }

    override fun observeData() {
        super.observeData()
        viewModel.homeFeedsResult.observe(this) {
            it.subscribe(
                doOnSuccess = { result ->
                    showLoading(false)
                    result.payload?.let { data ->
                        homeAdapter.setItems(data)
                    }
                },
                doOnLoading = {
                    showLoading(true)
                },
                doOnError = { error ->
                    showLoading(false)
                    error.exception?.let { e -> showError(true, e) }

                }
            )
        }

        viewModel.currentUserResult.observe(this) {
            it.subscribe(
                doOnSuccess = { result ->
                    binding.ivAvatarUser.setImageDrawable(
                        TextDrawable.builder()
                            .beginConfig()
                            .bold()
                            .toUpperCase()
                            .endConfig()
                            .buildRect(
                                text = result.payload?.username?.get(0).toString(),
                                color = ColorGenerator.MATERIAL.randomColor
                            )
                    )
                }
            )
        }

        viewModel.observeAddOrRemoveWatchlist().observe(this) {
            it.subscribe(
                doOnSuccess = { result ->
                    Toast.makeText(
                        requireContext(),
                        if (result.payload?.isUserWatchlist == true) {
                            getString(R.string.text_add_watchlist_success)
                        } else {
                            getString(R.string.text_remove_watchlist_success)
                        },
                        Toast.LENGTH_SHORT
                    ).show()
                },
                doOnError = {

                }
            )
        }
    }

    private fun setupRecyclerView() {
        binding.rvHome.apply {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setRecycledViewPool(recycledViewPool)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val scrollY: Int = binding.rvHome.computeVerticalScrollOffset()
                    val color = ColorUtils.changeAlpha(
                        currentColor = ContextCompat.getColor(
                            requireActivity(),
                            R.color.black_transparent
                        ),
                        fraction = (min(255, scrollY).toFloat() / 255.0f).toDouble()
                    )
                    binding.clToolbarHomeFeed.setBackgroundColor(color)
                }
            })
        }
    }

    private fun initData() {
        viewModel.getCurrentUser()
        viewModel.fetchHome()
    }

    private fun showLoading(isShowLoading: Boolean) {
        binding.pbHome.isVisible = isShowLoading
    }

    private fun openBottomSheet(movie: MovieViewParam) {
        bottomSheetRouter.createMovieInfoBottomSheet(movie)
            .show(childFragmentManager, "movie_info")
    }

}