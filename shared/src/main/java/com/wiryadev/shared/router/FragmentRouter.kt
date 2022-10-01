package com.wiryadev.shared.router

import androidx.fragment.app.Fragment

interface FragmentRouter {

    fun createPlayerFragment(videoUrl: String): Fragment

}