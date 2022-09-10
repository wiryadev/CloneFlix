package com.wiryadev.login.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wiryadev.core.wrapper.ViewResource
import com.wiryadev.login.domain.LoginUserUseCase
import com.wiryadev.shared.data.model.viewparam.UserViewParam
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {

    private val _loginResult = MutableLiveData<ViewResource<UserViewParam>>()
    val loginResult: LiveData<ViewResource<UserViewParam>> get() = _loginResult

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            loginUserUseCase(LoginUserUseCase.Param(email, password)).collect {
                _loginResult.postValue(it)
            }
        }
    }
}