package com.example.mealmate.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mealmate.R
import com.example.mealmate.databinding.ActivityAuthBinding
import com.example.mealmate.presentation.fragments.LoginFragment
import com.example.mealmate.presentation.fragments.MainFragment
import com.example.mealmate.presentation.fragments.SignupFragment

class AuthActivity : AppCompatActivity(), NavigateToMainListener, NavigateToLoginListener,
    NavigateToSignupListener {

    private val binding by lazy {
        ActivityAuthBinding.inflate(layoutInflater)
    }

    private lateinit var mainFragment: MainFragment
    private lateinit var loginFragment: LoginFragment
    private lateinit var signupFragment: SignupFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mainFragment = MainFragment.newInstance()
        mainFragment.loginListener = this
        mainFragment.signupListener = this

        loginFragment = LoginFragment.newInstance()
        signupFragment = SignupFragment.newInstance()

        showFragment(mainFragment)


    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerViewAuth, fragment)
            .commit()
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

        }else{
            showFragment(mainFragment)
        }
    }

    override fun navigateToSignup() {
        showFragment(signupFragment)
    }

    override fun navigateToMain() {
        showFragment(mainFragment)
    }


}

interface NavigateToLoginListener {
    fun navigateToLogin()
}

interface NavigateToSignupListener {
    fun navigateToSignup()
}

interface NavigateToMainListener {
    fun navigateToMain()
}