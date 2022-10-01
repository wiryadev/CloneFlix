package com.wiryadev.player.manager

import androidx.lifecycle.DefaultLifecycleObserver

interface PlayerManager : DefaultLifecycleObserver {
    fun play(videoUrl: String)
}