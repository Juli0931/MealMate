package com.example.mealmate.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mealmate.R
import com.example.mealmate.databinding.ActivityAuthBinding
import com.example.mealmate.view.fragments.LoginFragment
import com.example.mealmate.view.fragments.MainFragment
import com.example.mealmate.view.fragments.SignupFragment
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

class AuthActivity : AppCompatActivity(), NavigateToMainListener, NavigateToLoginListener,
    NavigateToSignupListener {

    private val binding by lazy {
        ActivityAuthBinding.inflate(layoutInflater)
    }

    private lateinit var mainFragment: MainFragment
    private lateinit var loginFragment: LoginFragment
    private lateinit var signupFragment: SignupFragment

    override fun onCreate(savedInstanceState: Bundle?) {


        initRemoteConfig()

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mainFragment = MainFragment.newInstance()
        mainFragment.loginListener = this
        mainFragment.signupListener = this

        loginFragment = LoginFragment.newInstance()
        loginFragment.signupListener = this

        signupFragment = SignupFragment.newInstance()
        signupFragment.loginListener = this

        showFragment(mainFragment)


    }

    private fun initRemoteConfig(){

        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

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
