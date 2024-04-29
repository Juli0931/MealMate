package com.example.mealmate.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mealmate.databinding.LoginFragmentBinding
import com.example.mealmate.presentation.activities.HomeActivity
import com.example.mealmate.presentation.activities.NavigateToSignupListener

class LoginFragment : Fragment() {

    lateinit var signupListener: NavigateToSignupListener

    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(requireContext(), HomeActivity::class.java))
            activity?.finish()
        }
        binding.btnSignup.setOnClickListener {
            signupListener.navigateToSignup()
        }

    }


    // Método estatico para generar instancias del propio fragment
    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }


}
/*


    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            viewModel.login(username, password)
        }

        viewModel.authStatus.observe(this) {
            when (it) {
                is AppAuthState.Loading -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }

                is AppAuthState.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }

                is AppAuthState.Success -> {
                    startActivity(
                        Intent(this@LoginFragment, ProfileActivity::class.java)
                    )
                }
            }
        }
    }
**/