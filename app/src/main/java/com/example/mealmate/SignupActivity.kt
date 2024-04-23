package com.example.mealmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.mealmate.databinding.ActivitySignupBinding
import com.example.mealmate.domain.model.AppAuthState
import com.example.mealmate.domain.model.User
import com.example.mealmate.viewmodel.SignupViewModel

class SignupActivity : AppCompatActivity() {

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
}