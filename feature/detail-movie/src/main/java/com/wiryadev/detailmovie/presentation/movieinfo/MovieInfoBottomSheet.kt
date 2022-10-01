package com.wiryadev.detailmovie.presentation.movieinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.wiryadev.detailmovie.presentation.detailmovie.DetailMovieActivity
import com.wiryadev.shared.data.model.viewparam.MovieViewParam
import com.wiryadev.shared.router.ActivityRouter
import com.wiryadev.shared.utils.CommonUtils
import com.wiryadev.styling.databinding.BottomSheetMovieInfoBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieInfoBottomSheet : BottomSheetDialogFragment() {

    private var movie: MovieViewParam? = null

    private var _binding: BottomSheetMovieInfoBinding? = null
    private val binding: BottomSheetMovieInfoBinding get() = _binding!!

    private val viewModel: MovieInfoViewModel by viewModel()

    private val router: ActivityRouter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetMovieInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        movie = arguments?.getParcelable(MOVIE_EXTRA_KEY)

        movie?.let { showData(it) }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun showData(movieViewParam: MovieViewParam) {
        with(binding) {
            ivPoster.load(movieViewParam.posterUrl)
            tvMovieTitle.text = movieViewParam.title
            tvShortDesc.text = movieViewParam.overview
            tvAdditionalInfo.text =
                "${movieViewParam.releaseDate} \u2022 ${movieViewParam.runtime} \u2022 ${movieViewParam.filmRate}"

            tvDetailMovie.setOnClickListener {navigateToDetail(movieViewParam) }
            ivClose.setOnClickListener { dismiss() }
            ivWatchlist.setOnClickListener { viewModel.addOrRemoveWatchlist(movieViewParam) }
            ivShare.setOnClickListener { CommonUtils.shareFilm(requireContext(), movieViewParam) }
        }
    }

    private fun navigateToDetail(movieViewParam: MovieViewParam) {
        startActivity(
            router.detailMovieActivity(requireContext()).apply {
                putExtra(DetailMovieActivity.MOVIE_ID_KEY, movieViewParam.id)
            }
        )
    }

    companion object {
        const val MOVIE_EXTRA_KEY = "movie_extra_key"

        fun newInstance(movie: MovieViewParam): MovieInfoBottomSheet {
            val args = Bundle()
            args.putParcelable(MOVIE_EXTRA_KEY, movie)

            return MovieInfoBottomSheet().apply {
                arguments = args
            }
        }
    }
}