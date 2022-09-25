package com.wiryadev.cloneflix.router

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.wiryadev.home.presentation.ui.homefeed.MovieInfoBottomSheet
import com.wiryadev.shared.data.model.viewparam.MovieViewParam
import com.wiryadev.shared.router.BottomSheetRouter

class BottomSheetRouterImpl: BottomSheetRouter{

    override fun createMovieInfoBottomSheet(movieViewParam: MovieViewParam): BottomSheetDialogFragment {
        return MovieInfoBottomSheet.newInstance(movieViewParam)
    }

}