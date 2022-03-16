package com.example.appevents.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.appevents.R
import com.example.appevents.databinding.FragmentSplashBinding

class SplashFragment : Fragment(R.layout.fragment_splash) {
    private lateinit var binding: FragmentSplashBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSplashBinding.bind(view)

        (activity as AppCompatActivity).supportActionBar?.hide()

        val textAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.splash_animation)
        binding.txtSplash.startAnimation(textAnimation)

        Handler(Looper.getMainLooper()).postDelayed({
            openHome()
        }, 3000)

    }

    private fun openHome() {
        Navigation.findNavController(requireView()).navigate(R.id.homeFragment)
    }
}