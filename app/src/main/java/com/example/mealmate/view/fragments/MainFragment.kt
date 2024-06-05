package com.example.mealmate.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mealmate.databinding.MainFragmentBinding
import com.example.mealmate.view.activities.NavigateToLoginListener
import com.example.mealmate.view.activities.NavigateToSignupListener
import com.example.mealmate.view.activities.SurveyActivity

class MainFragment : Fragment() {

    lateinit var loginListener: NavigateToLoginListener
    lateinit var signupListener: NavigateToSignupListener

    private lateinit var binding: MainFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            loginListener.navigateToLogin()
        }
        binding.btnSignup.setOnClickListener {
            signupListener.navigateToSignup()
        }

    }

    // Método estatico para generar instancias del propio fragment
    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }


}