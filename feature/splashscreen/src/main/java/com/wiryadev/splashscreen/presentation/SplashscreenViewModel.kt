package com.wiryadev.splashscreen.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wiryadev.core.wrapper.ViewResource
import com.wiryadev.splashscreen.domain.SyncResult
import com.wiryadev.splashscreen.domain.SyncUserUseCase
import kotlinx.coroutines.launch

class SplashscreenViewModel(
    private val syncUserUseCase: SyncUserUseCase,
) : ViewModel() {

    private val _syncResult: MutableLiveData<ViewResource<SyncResult>> = MutableLiveData()
    val syncResult: LiveData<ViewResource<SyncResult>> get() = _syncResult

    fun syncUser() {
        viewModelScope.launch {
            syncUserUseCase().collect {
                _syncResult.value = it
            }
        }
    }

}