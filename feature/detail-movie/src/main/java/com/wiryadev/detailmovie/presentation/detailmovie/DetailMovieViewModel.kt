package com.wiryadev.detailmovie.presentation.detailmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wiryadev.core.wrapper.ViewResource
import com.wiryadev.detailmovie.domain.GetDetailMovieUseCase
import com.wiryadev.shared.data.model.viewparam.MovieViewParam
import com.wiryadev.shared.delegates.AddOrRemoveWatchlistDelegate
import com.wiryadev.shared.delegates.AddOrRemoveWatchlistDelegateImpl
import kotlinx.coroutines.launch

class DetailMovieViewModel(
    private val getDetailMovieUseCase: GetDetailMovieUseCase
) : ViewModel(), AddOrRemoveWatchlistDelegate by AddOrRemoveWatchlistDelegateImpl() {

    init {
        init(viewModelScope)
    }

    private val _movieDetail = MutableLiveData<ViewResource<MovieViewParam>>()
    val movieDetail: LiveData<ViewResource<MovieViewParam>> get() = _movieDetail

    fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            getDetailMovieUseCase(movieId).collect {
                _movieDetail.postValue(it)
            }
        }
    }

}