package com.wiryadev.splashscreen.presentation

import android.content.Intent
import android.widget.Toast
import com.wiryadev.core.base.BaseActivity
import com.wiryadev.shared.router.ActivityRouter
import com.wiryadev.shared.utils.ext.subscribe
import com.wiryadev.splashscreen.databinding.ActivitySplashscreenBinding
import org.koin.android.ext.android.inject

class SplashscreenActivity : BaseActivity<ActivitySplashscreenBinding, SplashscreenViewModel>(
    ActivitySplashscreenBinding::inflate
) {

    override val viewModel: SplashscreenViewModel by inject()

    val router: ActivityRouter by inject()

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
        startActivity(router.loginActivity(this))
    }

    private fun navigateToHome() {
        startActivity(router.homeActivity(this))
    }

    private fun Intent.splashFlag() {
        this.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
    }

}