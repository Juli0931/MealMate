package com.example.mealmate.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mealmate.databinding.SignupFragmentBinding
import com.example.mealmate.domain.model.AppAuthState
import com.example.mealmate.domain.model.User
import com.example.mealmate.view.activities.NavigateToLoginListener
import com.example.mealmate.view.activities.SurveyActivity
import com.example.mealmate.viewmodel.SignupViewModel

class SignupFragment : Fragment() {

    lateinit var loginListener: NavigateToLoginListener

    private lateinit var binding: SignupFragmentBinding
    private val viewModel: SignupViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SignupFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnLogin.setOnClickListener {
            loginListener.navigateToLogin()
        }

        binding.btnSignup.setOnClickListener {
            viewModel.signup(
                User(
                    "",
                    binding.etUsername.text.toString(),
                    binding.etEmail.text.toString()
                    //binding.nameET.text.toString("")
                ),
                binding.etPassword.text.toString()
            )
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
                        Intent(requireContext(), SurveyActivity::class.java)
                    )
                    activity?.finish()
                }
            }
        }
    }

    // Método estatico para generar instancias del propio fragment
    companion object{
        fun newInstance(): SignupFragment {
            return SignupFragment()
        }
    }

}