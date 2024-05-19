package com.example.mealmate.view.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mealmate.R
import com.example.mealmate.databinding.ActivityAuthBinding
import com.example.mealmate.domain.model.AppAuthState
import com.example.mealmate.view.fragments.LoginFragment
import com.example.mealmate.view.fragments.MainFragment
import com.example.mealmate.view.fragments.SignupFragment
import com.example.mealmate.viewmodel.AuthViewModel

class AuthActivity : AppCompatActivity(), NavigateToMainListener, NavigateToLoginListener,
    NavigateToSignupListener {

    private val binding by lazy {
        ActivityAuthBinding.inflate(layoutInflater)
    }

    private lateinit var mainFragment: MainFragment
    private lateinit var loginFragment: LoginFragment
    private lateinit var signupFragment: SignupFragment
    private val viewModel:AuthViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                        Intent(this, HomeActivity::class.java)
                    )
                    finish()
                }
            }
        }
        viewModel.verifyCurrentFirebaseToken()

        mainFragment = MainFragment.newInstance()
        mainFragment.loginListener = this
        mainFragment.signupListener = this

        loginFragment = LoginFragment.newInstance()
        loginFragment.signupListener = this

        signupFragment = SignupFragment.newInstance()
        signupFragment.loginListener = this

        showFragment(mainFragment)
        setContentView(binding.root)

    }


    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerViewAuth, fragment)
            .commit()
    }

    override fun navigateToMain() {
        showFragment(mainFragment)
    }

    override fun navigateToSignup() {
        showFragment(signupFragment)
    }

    override fun navigateToLogin() {
        showFragment(loginFragment)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerViewAuth)

        if (currentFragment is MainFragment) {
            super.onBackPressed()

        } else {
            showFragment(mainFragment)
        }
    }
}

interface NavigateToMainListener {
    fun navigateToMain()
}

interface NavigateToLoginListener {
    fun navigateToLogin()
}

interface NavigateToSignupListener {
    fun navigateToSignup()
}

