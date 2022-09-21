package com.wiryadev.register.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wiryadev.core.wrapper.ViewResource
import com.wiryadev.register.domain.RegisterUserUseCase
import com.wiryadev.shared.data.model.viewparam.UserViewParam
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _registerResult = MutableLiveData<ViewResource<UserViewParam>>()
    val registerResult: LiveData<ViewResource<UserViewParam>> get() = _registerResult

    fun registerUser(
        email: String,
        password: String,
        username: String,
        gender: String,
        birthdate: String,
    ) {
        viewModelScope.launch {
            registerUserUseCase(
                RegisterUserUseCase.Param(
                    birthdate = birthdate,
                    email = email,
                    gender = gender,
                    password = password,
                    username =username,
                )
            ).collect {
                _registerResult.postValue(it)
            }
        }
    }
}