package com.wiryadev.cloneflix.router

import androidx.fragment.app.Fragment
import com.wiryadev.player.presentation.PlayerFragment
import com.wiryadev.shared.router.FragmentRouter

class FragmentRouterImpl : FragmentRouter {

    override fun createPlayerFragment(videoUrl: String): Fragment {
        return PlayerFragment.newInstance(videoUrl)
    }

}