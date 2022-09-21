package com.wiryadev.cloneflix.router

import android.content.Context
import android.content.Intent
import com.wiryadev.home.presentation.HomeActivity
import com.wiryadev.login.presentation.LoginActivity
import com.wiryadev.register.presentation.RegisterActivity
import com.wiryadev.shared.router.ActivityRouter

class ActivityRouterImpl : ActivityRouter {

    override fun loginActivity(context: Context): Intent {
        return Intent(context, LoginActivity::class.java)
    }

    override fun registerActivity(context: Context): Intent {
        return Intent(context, RegisterActivity::class.java)
    }

    override fun homeActivity(context: Context): Intent {
        return Intent(context, HomeActivity::class.java)
    }
}