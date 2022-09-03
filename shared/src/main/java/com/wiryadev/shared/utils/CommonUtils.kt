package com.wiryadev.shared.utils

import android.content.Context
import android.content.Intent
import com.wiryadev.shared.data.model.viewparam.MovieViewParam
import com.wiryadev.styling.ProjectDrawable

object CommonUtils {

    fun getWatchlistIcon(isUserWatchlist: Boolean): Int {
        return if (isUserWatchlist) ProjectDrawable.ic_check else ProjectDrawable.ic_add
    }

    fun shareFilm(context: Context, movieViewParam: MovieViewParam) {
        val shareIntent = Intent.createChooser(
            Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Watch this ! ${movieViewParam.title} ${movieViewParam.posterUrl}"
                )
                type = "text/plain"
            },
            null,
        )
        context.startActivity(shareIntent)
    }

}