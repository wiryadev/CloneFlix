package com.wiryadev.login.presentation

import android.content.Intent
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputLayout
import com.wiryadev.core.base.BaseActivity
import com.wiryadev.core.exception.FieldErrorException
import com.wiryadev.login.constans.LoginFieldConstants
import com.wiryadev.login.databinding.ActivityLoginBinding
import com.wiryadev.shared.router.ActivityRouter
import com.wiryadev.shared.utils.ext.subscribe
import com.wiryadev.shared.utils.listen
import org.koin.android.ext.android.inject

class LoginActivity :
    BaseActivity<ActivityLoginBinding, LoginViewModel>(ActivityLoginBinding::inflate) {

    override val viewModel: LoginViewModel by inject()

    private val router: ActivityRouter by inject()

    override fun initView() {
        with(binding) {
            btnRegisterNewAccount.setOnClickListener {
                navigateToRegister()
            }

            btnLogin.setOnClickListener {
                viewModel.loginUser(
                    email = etEmail.text?.trim().toString(),
                    password = etPassword.text?.trim().toString(),
                )
            }

            etPassword.listen(
                beforeTextChanged = {
                    tilPassword.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
                }
            )
        }
    }

    override fun observeData() {
        viewModel.loginResult.observe(this) { loginResult ->
            resetFields()
            loginResult.subscribe(
                doOnSuccess = {
                    showLoading(false)
                    navigateToHome()
                },
                doOnError = {
                    showLoading(false)
                    if (loginResult.exception is FieldErrorException) {
                        handleFieldError(loginResult.exception as FieldErrorException)
                    } else {
                        loginResult.exception?.let { e -> showError(true, e) }
                    }
                },
                doOnLoading = {
                    showLoading(true)
                },
            )
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbLoading.isVisible = isLoading
    }

    private fun handleFieldError(exception: FieldErrorException) {
        exception.errorFields.forEach { errorField ->
            if (errorField.first == LoginFieldConstants.FIELD_EMAIL) {
                binding.etEmail.error = getString(errorField.second)
            }
            if (errorField.first == LoginFieldConstants.FIELD_PASSWORD) {
                binding.tilPassword.endIconMode = TextInputLayout.END_ICON_NONE
                binding.etPassword.error = getString(errorField.second)
            }
        }
    }

    private fun resetFields() {
        with(binding) {
            tilEmail.isErrorEnabled = false
            tilPassword.isErrorEnabled = false
        }
    }

    private fun navigateToHome() {
        startActivity(
            router.homeActivity(this).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
        )
    }

    private fun navigateToRegister() {
        startActivity(router.registerActivity(this))
    }

}