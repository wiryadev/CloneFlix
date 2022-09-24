package com.wiryadev.home.presentation.ui.home

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.wiryadev.core.base.BaseActivity
import com.wiryadev.home.R
import com.wiryadev.home.databinding.ActivityHomeBinding
import com.wiryadev.home.presentation.ui.homefeed.HomeFeedFragment
import com.wiryadev.home.presentation.ui.watchlist.WatchlistFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity :
    BaseActivity<ActivityHomeBinding, HomeViewModel>(ActivityHomeBinding::inflate) {

    override val viewModel: HomeViewModel by viewModel()

    private val homeFeedFragment = HomeFeedFragment()
    private val watchListFragment = WatchlistFragment()
    private var activeFragment: Fragment = homeFeedFragment

    override fun initView() {
        setupFragment()
    }

    private fun setupFragment() {
        // delete all fragment in fragmentManager first
        for (fragment in supportFragmentManager.fragments) {
            supportFragmentManager.beginTransaction()
                .remove(fragment)
                .commit()
        }

        // add fragment to fragmentManager
        supportFragmentManager.beginTransaction()
            .add(binding.container.id, homeFeedFragment)
            .add(binding.container.id, watchListFragment)
            .hide(watchListFragment)
            .commit()

        // set click menu for changing fragment
        binding.bottomNavView.setOnItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.home -> {
                    showFragment(homeFeedFragment)
                    true
                }

                else -> {
                    showFragment(watchListFragment)
                    true
                }
            }
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .hide(activeFragment)
            .show(fragment)
            .commit()

        activeFragment = fragment
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }

}