package com.wiryadev.detailmovie.presentation.detailmovie

import androidx.core.view.isVisible
import coil.load
import com.wiryadev.core.base.BaseActivity
import com.wiryadev.detailmovie.databinding.ActivityDetailMovieBinding
import com.wiryadev.shared.data.model.viewparam.MovieViewParam
import com.wiryadev.shared.utils.CommonUtils
import com.wiryadev.shared.utils.ext.subscribe
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailMovieActivity :
    BaseActivity<ActivityDetailMovieBinding, DetailMovieViewModel>(ActivityDetailMovieBinding::inflate) {

    override val viewModel: DetailMovieViewModel by viewModel()

    override fun initView() {
        val id = intent.getIntExtra(MOVIE_ID_KEY, DEFAULT_ID)

        if (id == DEFAULT_ID) return

        viewModel.getMovieDetail(id)
    }

    override fun observeData() {
        viewModel.movieDetail.observe(this) { viewResource ->
            viewResource.subscribe(
                doOnSuccess = { result ->
                    showLoading(false)
                    result.payload?.let { showMovieDetail(it) }
                },
                doOnLoading = {
                    showLoading(true)
                },
                doOnError = { error ->
                    showLoading(false)
                    error.exception?.let { e -> showError(true, e) }
                },
            )
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbDetail.isVisible = isLoading
    }

    private fun showMovieDetail(movieViewParam: MovieViewParam) {
        with(binding.layoutDetail) {
            layoutHeaderDetail.apply {
                ivPosterDetail.load(movieViewParam.posterUrl)
            }
            clDetailMovie.apply {
                tvTitleMovie.text = movieViewParam.title
                tvMovieDesc.text = movieViewParam.overview
                tvAdditionalInfo.text =
                    "${movieViewParam.releaseDate} \u2022 ${movieViewParam.runtime} \u2022 ${movieViewParam.filmRate}"

                ivWatchlist.setOnClickListener { viewModel.addOrRemoveWatchlist(movieViewParam) }
                ivShare.setOnClickListener { CommonUtils.shareFilm(this@DetailMovieActivity, movieViewParam) }
            }
        }
    }

    companion object {
        const val MOVIE_ID_KEY = "movie_id_key"
        private const val DEFAULT_ID = -111
    }

}