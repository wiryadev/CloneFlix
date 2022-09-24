package com.wiryadev.home.presentation.ui.homefeed

import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
//import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wiryadev.core.base.BaseFragment
import com.wiryadev.home.R
import com.wiryadev.home.databinding.FragmentHomeFeedBinding
import com.wiryadev.home.presentation.adapter.home.HomeAdapter
import com.wiryadev.home.presentation.adapter.home.createHomeAdapterClickListener
import com.wiryadev.home.presentation.ui.home.HomeViewModel
import com.wiryadev.shared.utils.ColorUtils
import com.wiryadev.shared.utils.ext.subscribe
import com.wiryadev.shared.utils.text_drawable.ColorGenerator
import com.wiryadev.shared.utils.text_drawable.TextDrawable
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.math.min

class HomeFeedFragment :
    BaseFragment<FragmentHomeFeedBinding, HomeViewModel>(FragmentHomeFeedBinding::inflate) {

    override val viewModel: HomeViewModel by sharedViewModel()

    private val recyclerViewPool: RecyclerView.RecycledViewPool by lazy {
        RecyclerView.RecycledViewPool()
    }

    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(
            listener = createHomeAdapterClickListener(
                onMyListClicked = {},
                onPlayMovieClicked = {},
                onMovieClicked = {}
            ),
            recyclerViewPool = recyclerViewPool
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

                })
        }
        viewModel.currentUserResult.observe(this) {
            it.subscribe(doOnSuccess = { result ->
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
            })
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

}