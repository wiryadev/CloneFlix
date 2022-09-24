package com.wiryadev.register.presentation

import android.content.Intent
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputLayout
import com.wiryadev.core.base.BaseActivity
import com.wiryadev.core.exception.FieldErrorException
import com.wiryadev.register.constants.RegisterFieldConstants
import com.wiryadev.register.databinding.ActivityRegisterBinding
import com.wiryadev.shared.router.ActivityRouter
import com.wiryadev.shared.utils.DateUtils.showDatePickerDialog
import com.wiryadev.shared.utils.GenderUtils
import com.wiryadev.shared.utils.ext.subscribe
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity :
    BaseActivity<ActivityRegisterBinding, RegisterViewModel>(ActivityRegisterBinding::inflate) {

    override val viewModel: RegisterViewModel by viewModel()

    private val router: ActivityRouter by inject()

    override fun initView() {
        with(binding) {
            etBirthdate.setOnClickListener {
                showDatePickerDialog {
                    binding.etBirthdate.setText(it)
                }
            }
            tvGender.apply {
                setAdapter(GenderUtils.createGenderListAdapter(this@RegisterActivity))
            }
            btnRegister.setOnClickListener {
                viewModel.registerUser(
                    email = etEmail.text?.trim().toString(),
                    password = etPassword.text?.trim().toString(),
                    username = etUsername.text?.trim().toString(),
                    birthdate = etBirthdate.text?.trim().toString(),
                    gender = tvGender.text?.trim().toString(),
                )
            }
        }
    }

    override fun observeData() {
        viewModel.registerResult.observe(this) { registerResult ->
            resetField()
            registerResult.subscribe(doOnLoading = {
                showLoading(true)
            }, doOnSuccess = {
                showLoading(false)
                navigateToHome()
            }, doOnError = {
                showLoading(false)
                if (registerResult.exception is FieldErrorException) {
                    handleFieldError(registerResult.exception as FieldErrorException)
                } else {
                    showError(true, registerResult.exception as Exception)
                }
            })
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbLoading.isVisible = isLoading
    }

    private fun handleFieldError(exception: FieldErrorException) {
        exception.let {
            it.errorFields.forEach { errorField ->
                if (errorField.first == RegisterFieldConstants.FIELD_EMAIL) {
                    binding.etEmail.error = getString(errorField.second)
                }
                if (errorField.first == RegisterFieldConstants.FIELD_BIRTH_DATE) {
                    binding.etBirthdate.error = getString(errorField.second)
                }
                if (errorField.first == RegisterFieldConstants.FIELD_USERNAME) {
                    binding.etUsername.error = getString(errorField.second)
                }
                if (errorField.first == RegisterFieldConstants.FIELD_GENDER) {
                    binding.tvGender.error = getString(errorField.second)
                }
                if (errorField.first == RegisterFieldConstants.FIELD_PASSWORD) {
                    binding.tilPassword.endIconMode = TextInputLayout.END_ICON_NONE
                    binding.etPassword.error = getString(errorField.second)
                }
            }
        }
    }

    private fun resetField() {
        with(binding) {
            tilEmail.isErrorEnabled = false
            tilPassword.isErrorEnabled = false
            tilGender.isErrorEnabled = false
            tilBirthdate.isErrorEnabled = false
            tilUsername.isErrorEnabled = false
            tilPassword.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
        }
    }

    private fun navigateToHome() {
        startActivity(
            router.homeActivity(this).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
        )
    }

}