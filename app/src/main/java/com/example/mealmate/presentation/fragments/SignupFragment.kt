package com.example.mealmate.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mealmate.databinding.SignupFragmentBinding

class SignupFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:SignupFragmentBinding = SignupFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Método estatico para generar instancias del propio fragment
    companion object{
        fun newInstance(): SignupFragment {
            return SignupFragment()
        }
    }

}
/*

    private val binding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }
    val viewModel: SignupViewModel by viewModels()

    //Registro
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
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

        viewModel.authStatus.observe(this) {
            when (it) {
                is AppAuthState.Loading -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }

                is AppAuthState.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }

                is AppAuthState.Success -> {
                    startActivity( Intent(this, AboutYouActivity::class.java) )
                }
            }
        }
    }
*/