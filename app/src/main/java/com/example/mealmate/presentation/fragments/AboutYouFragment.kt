package com.example.mealmate.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mealmate.databinding.AboutYouFragmentBinding

class AboutYouFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: AboutYouFragmentBinding = AboutYouFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Método estatico para generar instancias del propio fragment
    companion object{
        fun newInstance(): AboutYouFragment {
            return AboutYouFragment()
        }
    }


}
/*
    private val binding by lazy {
        ActivityAboutYouBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnContinuar.setOnClickListener {
            val intent = Intent(this, OnboardingActivity::class.java)
            startActivity(intent)
        }

        binding.button2.setOnClickListener {
            startActivity(
                Intent(this, ProfileActivity::class.java)
            )
        }
    }
}

 */

