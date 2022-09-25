package com.wiryadev.shared.router

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.wiryadev.shared.data.model.viewparam.MovieViewParam

interface BottomSheetRouter {

    fun createMovieInfoBottomSheet(movieViewParam: MovieViewParam): BottomSheetDialogFragment

}