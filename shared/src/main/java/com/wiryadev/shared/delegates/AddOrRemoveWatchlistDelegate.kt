package com.wiryadev.shared.delegates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wiryadev.core.wrapper.ViewResource
import com.wiryadev.shared.data.model.viewparam.MovieViewParam
import com.wiryadev.shared.domain.AddOrRemoveWatchlistUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

interface AddOrRemoveWatchlistDelegate {
    fun init(scope: CoroutineScope)
    fun observeAddOrRemoveWatchlist(): LiveData<ViewResource<MovieViewParam>>
    fun addOrRemoveWatchlist(movie: MovieViewParam)
}

class AddOrRemoveWatchlistDelegateImpl : AddOrRemoveWatchlistDelegate {
    private lateinit var coroutineScope: CoroutineScope
    private val useCase: AddOrRemoveWatchlistUseCase by inject(AddOrRemoveWatchlistUseCase::class.java)
    private val result = MutableLiveData<ViewResource<MovieViewParam>>()

    override fun init(scope: CoroutineScope) {
        coroutineScope = scope
    }

    override fun observeAddOrRemoveWatchlist(): LiveData<ViewResource<MovieViewParam>> = result

    override fun addOrRemoveWatchlist(movie: MovieViewParam) {
        coroutineScope.launch {
            useCase(AddOrRemoveWatchlistUseCase.Param(movie)).collect {
                result.postValue(it)
            }
        }
    }

}