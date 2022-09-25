package com.wiryadev.home.presentation.ui.homefeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.wiryadev.home.presentation.ui.home.HomeViewModel
import com.wiryadev.shared.data.model.viewparam.MovieViewParam
import com.wiryadev.shared.utils.CommonUtils
import com.wiryadev.styling.databinding.BottomSheetMovieInfoBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MovieInfoBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var movie: MovieViewParam? = null

    private var _binding: BottomSheetMovieInfoBinding? = null
    private val binding: BottomSheetMovieInfoBinding get() = _binding!!

    private val viewModel: HomeViewModel by sharedViewModel()

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

    private fun showData(movieViewParam: MovieViewParam) {
        with(binding) {
            ivPoster.load(movieViewParam.posterUrl)
            tvMovieTitle.text = movieViewParam.title
            tvShortDesc.text = movieViewParam.overview
            tvAdditionalInfo.text = "${movieViewParam.releaseDate} \u2022 ${movieViewParam.runtime} \u2022 ${movieViewParam.filmRate}"
            tvDetailMovie.setOnClickListener {
                // todo: go to detail screen
            }
            ivClose.setOnClickListener { dismiss() }
            ivWatchlist.setOnClickListener { viewModel.addOrRemoveWatchlist(movieViewParam) }
            ivShare.setOnClickListener { CommonUtils.shareFilm(requireContext(), movieViewParam) }
        }
    }

    companion object {
        const val MOVIE_EXTRA_KEY = "movie_extra_key"

        fun newInstance(movie: MovieViewParam): MovieInfoBottomSheetDialogFragment {
            val args = Bundle()
            args.putParcelable(MOVIE_EXTRA_KEY, movie)

            return MovieInfoBottomSheetDialogFragment().apply {
                arguments = args
            }
        }
    }
}