package com.wiryadev.splashscreen.presentation

import android.widget.Toast
import com.wiryadev.core.base.BaseActivity
import com.wiryadev.shared.utils.ext.subscribe
import com.wiryadev.splashscreen.databinding.ActivitySplashscreenBinding
import org.koin.android.ext.android.inject

class SplashscreenActivity : BaseActivity<ActivitySplashscreenBinding, SplashscreenViewModel>(
    ActivitySplashscreenBinding::inflate
) {

    override val viewModel: SplashscreenViewModel by inject()

    override fun initView() {
        viewModel.syncUser()
    }

    override fun observeData() {
        viewModel.syncResult.observe(this) {
            it.subscribe(
                doOnSuccess = { response ->
                    if (response.payload?.first == true) {
                        navigateToHome()
                    } else {
                        navigateToLogin()
                    }
                },
                doOnError = { error ->
                    Toast.makeText(this, error.exception?.message, Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun navigateToLogin() {
        Toast.makeText(this, "Navigate to login", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToHome() {
        Toast.makeText(this, "Navigate to home", Toast.LENGTH_SHORT).show()
    }

}