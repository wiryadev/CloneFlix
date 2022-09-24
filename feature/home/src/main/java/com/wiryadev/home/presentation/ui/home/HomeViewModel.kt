package com.wiryadev.home.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wiryadev.core.wrapper.ViewResource
import com.wiryadev.home.domain.GetHomeFeedUseCase
import com.wiryadev.home.domain.GetUserWatchlistUseCase
import com.wiryadev.home.presentation.viewparam.homeitem.HomeUiItem
import com.wiryadev.shared.data.model.viewparam.MovieViewParam
import com.wiryadev.shared.data.model.viewparam.UserViewParam
import com.wiryadev.shared.domain.GetCurrentUserUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getHomeFeedsUseCase: GetHomeFeedUseCase,
    private val getUserWatchlistUseCase: GetUserWatchlistUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
) : ViewModel() {

    private val _homeFeedsResult = MutableLiveData<ViewResource<List<HomeUiItem>>>()
    val homeFeedsResult: LiveData<ViewResource<List<HomeUiItem>>> get() = _homeFeedsResult

    private val _watchlistResult = MutableLiveData<ViewResource<List<MovieViewParam>>>()
    val watchlistResult: LiveData<ViewResource<List<MovieViewParam>>> get() = _watchlistResult

    private val _currentUserResult = MutableLiveData<ViewResource<UserViewParam>>()
    val currentUserResult: LiveData<ViewResource<UserViewParam>> get() = _currentUserResult

    fun fetchHome() {
        viewModelScope.launch {
            getHomeFeedsUseCase().collect {
                _homeFeedsResult.postValue(it)
            }
        }
    }
    fun getCurrentUser() {
        viewModelScope.launch {
            getCurrentUserUseCase().collect {
                _currentUserResult.postValue(it)
            }
        }
    }
    fun fetchWatchlist() {
        viewModelScope.launch {
            getUserWatchlistUseCase().collect {
                _watchlistResult.postValue(it)
            }
        }
    }

}