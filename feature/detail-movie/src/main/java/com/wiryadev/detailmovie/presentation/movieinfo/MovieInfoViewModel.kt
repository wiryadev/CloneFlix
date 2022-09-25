package com.wiryadev.detailmovie.presentation.movieinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wiryadev.shared.delegates.AddOrRemoveWatchlistDelegate
import com.wiryadev.shared.delegates.AddOrRemoveWatchlistDelegateImpl

class MovieInfoViewModel : ViewModel(),
    AddOrRemoveWatchlistDelegate by AddOrRemoveWatchlistDelegateImpl() {

    init {
        init(viewModelScope)
    }

}