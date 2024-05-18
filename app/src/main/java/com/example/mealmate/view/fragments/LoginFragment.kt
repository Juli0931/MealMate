package com.example.mealmate.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mealmate.databinding.LoginFragmentBinding
import com.example.mealmate.domain.model.AppAuthState
import com.example.mealmate.view.activities.HomeActivity
import com.example.mealmate.view.activities.NavigateToSignupListener
import com.example.mealmate.viewmodel.LoginViewModel

class LoginFragment : Fragment() {

    lateinit var signupListener: NavigateToSignupListener

    private lateinit var binding: LoginFragmentBinding
    private val viewModel: LoginViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            viewModel.login(username, password)
        }

        binding.btnSignup.setOnClickListener {
            signupListener.navigateToSignup()
        }

        viewModel.authStatus.observe(viewLifecycleOwner) {
            when (it) {
                is AppAuthState.Loading -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }

                is AppAuthState.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }

                is AppAuthState.Success -> {
                    startActivity(
                        Intent(requireContext(), HomeActivity::class.java)
                    )
                    activity?.finish()
                }
            }
        }

    }

    // Método estatico para generar instancias del propio fragment
    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }


}