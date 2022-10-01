package com.wiryadev.player.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wiryadev.player.R
import com.wiryadev.player.databinding.FragmentPlayerBinding
import com.wiryadev.player.manager.ExoPlayerManager
import com.wiryadev.player.manager.PlayerManager

class PlayerFragment : Fragment() {

    private var _binding: FragmentPlayerBinding? = null
    private val binding: FragmentPlayerBinding get() = _binding!!

    private lateinit var playerManager: PlayerManager

    private val videoUrl: String? by lazy {
        arguments?.getString(ARG_VIDEO_URL)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playerManager = ExoPlayerManager(binding.playerView)
        this.lifecycle.addObserver(playerManager)
        videoUrl?.let { playerManager.play(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.lifecycle.removeObserver(playerManager)
    }

    companion object {
        private const val ARG_VIDEO_URL = "arg_video_url"

        fun newInstance(videoUrl: String) = PlayerFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_VIDEO_URL, videoUrl)
            }
        }
    }

}